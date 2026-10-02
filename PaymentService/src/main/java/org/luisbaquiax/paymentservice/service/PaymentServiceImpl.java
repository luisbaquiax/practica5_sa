package org.luisbaquiax.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.paymentservice.dto.PaymentRequestDTO;
import org.luisbaquiax.paymentservice.dto.PaymentResponseDTO;
import org.luisbaquiax.paymentservice.enums.PaymentStatus;
import org.luisbaquiax.paymentservice.exception.PaymentNotFoundException;
import org.luisbaquiax.paymentservice.exception.PaymentProcessingException;
import org.luisbaquiax.paymentservice.model.Payment;
import org.luisbaquiax.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDTO charge(PaymentRequestDTO request) {
        if (request.isSimulateFailure()) {
            throw new PaymentProcessingException(
                    String.format("Error al procesar el pago de la orden %s", request.getOrderId()));
        }

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .status(PaymentStatus.COMPLETED)
                .build();

        return toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentResponseDTO refund(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Pago de orden %s no encontrado", orderId)));
        payment.setStatus(PaymentStatus.REFUNDED);
        return toDto(paymentRepository.save(payment));
    }

    private PaymentResponseDTO toDto(Payment p) {
        return PaymentResponseDTO.builder()
                .id(p.getId())
                .orderId(p.getOrderId())
                .customerId(p.getCustomerId())
                .amount(p.getAmount())
                .status(p.getStatus())
                .createdAt(p.getCreatedAt())
                .build();
    }
}