package org.luisbaquiax.orchestratorservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.orchestratorservice.dto.CreateOrderRequestDTO;
import org.luisbaquiax.orchestratorservice.dto.SagaResponseDTO;
import org.luisbaquiax.orchestratorservice.service.SagaOrchestratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class SagaController {

    private final SagaOrchestratorService sagaOrchestratorService;

    @PostMapping
    public ResponseEntity<SagaResponseDTO> createOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
        SagaResponseDTO response = sagaOrchestratorService.createOrderSaga(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<SagaResponseDTO> getStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(sagaOrchestratorService.getSagaStatus(orderId));
    }
}