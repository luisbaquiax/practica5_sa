package org.luisbaquiax.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.orderservice.dto.OrderRequestDTO;
import org.luisbaquiax.orderservice.dto.OrderResponseDTO;
import org.luisbaquiax.orderservice.enums.OrderStatus;
import org.luisbaquiax.orderservice.exception.OrderNotFoundException;
import org.luisbaquiax.orderservice.model.Order;
import org.luisbaquiax.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .totalAmount(request.getTotalAmount())
                .status(OrderStatus.PENDING)
                .build();

        Order saved = orderRepository.save(order);
        return toResponseDTO(saved);
    }

    @Override
    public OrderResponseDTO getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Orden con id %d no encontrada", id)));
        return toResponseDTO(order);
    }

    @Override
    public void confirmOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Orden con id %d no encontrada", id)));
        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Orden con id %d no encontrada", id)));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}