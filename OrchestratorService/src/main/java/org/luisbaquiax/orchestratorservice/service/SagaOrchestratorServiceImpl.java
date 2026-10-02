package org.luisbaquiax.orchestratorservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luisbaquiax.orchestratorservice.client.*;
import org.luisbaquiax.orchestratorservice.dto.*;
import org.luisbaquiax.orchestratorservice.enums.SagaStatus;
import org.luisbaquiax.orchestratorservice.exception.SagaExecutionException;
import org.luisbaquiax.orchestratorservice.model.SagaState;
import org.luisbaquiax.orchestratorservice.repository.SagaStateRepository;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaOrchestratorServiceImpl implements SagaOrchestratorService {

    private final OrderClient orderClient;
    private final PaymentClient paymentClient;
    private final InventoryClient inventoryClient;
    private final ShippingClient shippingClient;
    private final SagaStateRepository sagaStateRepository;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Override
    public SagaResponseDTO createOrderSaga(CreateOrderRequestDTO request) {

        // Paso 1: Order Service
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setCustomerId(request.getCustomerId());
        orderRequest.setProductId(request.getProductId());
        orderRequest.setQuantity(request.getQuantity());
        orderRequest.setTotalAmount(request.getTotalAmount());

        OrderResponseDTO order = circuitBreakerFactory.create("orderService")
                .run(() -> orderClient.createOrder(orderRequest),
                        t -> {
                            throw new SagaExecutionException("Order Service no disponible: " + t.getMessage(), t);
                        });

        Long orderId = order.getId();
        SagaState saga = sagaStateRepository.save(SagaState.builder()
                .orderId(orderId)
                .status(SagaStatus.ORDER_CREATED)
                .build());

        // Paso 2: Payment Service
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setCustomerId(request.getCustomerId());
        paymentRequest.setAmount(request.getTotalAmount());
        paymentRequest.setSimulateFailure(request.isSimulatePaymentFailure());

        try {
            circuitBreakerFactory.create("paymentService")
                    .run(() -> paymentClient.charge(paymentRequest),
                            t -> {
                                throw new SagaExecutionException("Payment Service falló: " + t.getMessage(), t);
                            });
            updateSaga(saga, SagaStatus.PAYMENT_COMPLETED, null);
        } catch (Exception e) {
            compensateFromOrder(orderId, saga, e.getMessage());
            throw new SagaExecutionException("Saga fallida en Payment: " + e.getMessage());
        }

        // Paso 3: Inventory Service
        InventoryRequestDTO inventoryRequest = new InventoryRequestDTO();
        inventoryRequest.setOrderId(orderId);
        inventoryRequest.setProductId(request.getProductId());
        inventoryRequest.setQuantity(request.getQuantity());

        try {
            circuitBreakerFactory.create("inventoryService")
                    .run(() -> inventoryClient.reserve(inventoryRequest),
                            t -> {
                                throw new SagaExecutionException("Inventory Service falló: " + t.getMessage(), t);
                            });
            updateSaga(saga, SagaStatus.INVENTORY_RESERVED, null);
        } catch (Exception e) {
            compensateFromPayment(orderId, saga, e.getMessage());
            throw new SagaExecutionException("Saga fallida en Inventory: " + e.getMessage());
        }

        // Paso 4: Shipping Service
        ShippingRequestDTO shippingRequest = new ShippingRequestDTO();
        shippingRequest.setOrderId(orderId);
        shippingRequest.setAddress(request.getAddress());

        try {
            circuitBreakerFactory.create("shippingService")
                    .run(() -> shippingClient.schedule(shippingRequest),
                            t -> {
                                throw new SagaExecutionException("Shipping Service falló: " + t.getMessage(), t);
                            });
            updateSaga(saga, SagaStatus.SHIPPING_SCHEDULED, null);
        } catch (Exception e) {
            compensateFromInventory(orderId, saga, e.getMessage());
            throw new SagaExecutionException("Saga fallida en Shipping: " + e.getMessage());
        }

        // Éxito total: confirmar la orden
        orderClient.confirmOrder(orderId);
        updateSaga(saga, SagaStatus.COMPLETED, null);

        return SagaResponseDTO.builder().orderId(orderId).status(SagaStatus.COMPLETED).build();
    }

    @Override
    public SagaResponseDTO getSagaStatus(Long orderId) {
        SagaState saga = sagaStateRepository.findByOrderId(orderId)
                .orElseThrow(() -> new SagaExecutionException("Saga no encontrada para orderId: " + orderId));
        return SagaResponseDTO.builder()
                .orderId(saga.getOrderId())
                .status(saga.getStatus())
                .failureReason(saga.getFailureReason())
                .build();
    }

    /**
     * Compensasiones
     */
    private void compensateFromOrder(Long orderId, SagaState saga, String reason) {
        updateSaga(saga, SagaStatus.COMPENSATING, reason);
        safeCancelOrder(orderId);
        updateSaga(saga, SagaStatus.FAILED, reason);
    }

    private void compensateFromPayment(Long orderId, SagaState saga, String reason) {
        updateSaga(saga, SagaStatus.COMPENSATING, reason);
        safeRefundPayment(orderId);
        safeCancelOrder(orderId);
        updateSaga(saga, SagaStatus.FAILED, reason);
    }

    private void compensateFromInventory(Long orderId, SagaState saga, String reason) {
        updateSaga(saga, SagaStatus.COMPENSATING, reason);
        safeReleaseInventory(orderId);
        safeRefundPayment(orderId);
        safeCancelOrder(orderId);
        updateSaga(saga, SagaStatus.FAILED, reason);
    }

    private void safeCancelOrder(Long orderId) {
        try {
            orderClient.cancelOrder(orderId);
        } catch (Exception e) {
            log.error("Compensación fallida: no se pudo cancelar la orden {}: {}",
                    orderId, e.getMessage());
        }
    }

    private void safeRefundPayment(Long orderId) {
        try {
            paymentClient.refund(orderId);
        } catch (Exception e) {
            log.error("Compensación fallida: no se pudo reembolsar el pago de la orden {}: {}"
                    , orderId, e.getMessage());
        }
    }

    private void safeReleaseInventory(Long orderId) {
        try {
            inventoryClient.release(orderId);
        } catch (Exception e) {
            log.error("Compensación fallida: no se pudo liberar inventario de la orden {}: {}"
                    , orderId, e.getMessage());
        }
    }

    private void updateSaga(SagaState saga, SagaStatus status, String failureReason) {
        saga.setStatus(status);
        saga.setFailureReason(failureReason);
        sagaStateRepository.save(saga);
    }
}