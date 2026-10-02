package org.luisbaquiax.paymentservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.paymentservice.dto.PaymentRequestDTO;
import org.luisbaquiax.paymentservice.dto.PaymentResponseDTO;
import org.luisbaquiax.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> charge(@Valid @RequestBody PaymentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.charge(request));
    }

    @PostMapping("/{orderId}/refund")
    public ResponseEntity<PaymentResponseDTO> refund(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.refund(orderId));
    }
}