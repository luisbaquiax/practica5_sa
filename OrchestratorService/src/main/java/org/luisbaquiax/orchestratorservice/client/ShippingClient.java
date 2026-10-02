package org.luisbaquiax.orchestratorservice.client;

import org.luisbaquiax.orchestratorservice.dto.ShippingRequestDTO;
import org.luisbaquiax.orchestratorservice.dto.ShippingResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "shipping-service", url = "${services.shipping.url}")
public interface ShippingClient {

    @PostMapping("/shipping/schedule")
    ShippingResponseDTO schedule(@RequestBody ShippingRequestDTO request);

    @DeleteMapping("/shipping")
    void cancel(@RequestParam("orderId") Long orderId);
}