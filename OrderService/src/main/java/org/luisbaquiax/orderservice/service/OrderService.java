package org.luisbaquiax.orderservice.service;

import org.luisbaquiax.orderservice.dto.OrderRequestDTO;
import org.luisbaquiax.orderservice.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO request);
    OrderResponseDTO getOrder(Long id);
    void confirmOrder(Long id);
    void cancelOrder(Long id);
}