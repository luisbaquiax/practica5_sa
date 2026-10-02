package org.luisbaquiax.shippingservice.service;

import org.luisbaquiax.shippingservice.dto.ShippingRequestDTO;
import org.luisbaquiax.shippingservice.dto.ShippingResponseDTO;

public interface ShippingService {
    ShippingResponseDTO schedule(ShippingRequestDTO request);
    void cancel(Long orderId);
}