package org.luisbaquiax.orchestratorservice.client;

import org.luisbaquiax.orchestratorservice.dto.OrderRequestDTO;
import org.luisbaquiax.orchestratorservice.dto.OrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-service", url = "${services.order.url}")
public interface OrderClient {

    @PostMapping("/orders")
    OrderResponseDTO createOrder(@RequestBody OrderRequestDTO request);

    @PutMapping("/orders/{id}/confirm")
    void confirmOrder(@PathVariable("id") Long id);

    @DeleteMapping("/orders/{id}")
    void cancelOrder(@PathVariable("id") Long id);
}