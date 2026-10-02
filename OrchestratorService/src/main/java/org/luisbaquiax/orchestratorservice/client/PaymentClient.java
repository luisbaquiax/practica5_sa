package org.luisbaquiax.orchestratorservice.client;

import org.luisbaquiax.orchestratorservice.dto.PaymentRequestDTO;
import org.luisbaquiax.orchestratorservice.dto.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", url = "${services.payment.url}")
public interface PaymentClient {

    @PostMapping("/payments")
    PaymentResponseDTO charge(@RequestBody PaymentRequestDTO request);

    @PostMapping("/payments/{orderId}/refund")
    PaymentResponseDTO refund(@PathVariable("orderId") Long orderId);
}