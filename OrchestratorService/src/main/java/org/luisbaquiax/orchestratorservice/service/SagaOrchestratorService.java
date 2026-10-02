package org.luisbaquiax.orchestratorservice.service;

import org.luisbaquiax.orchestratorservice.dto.CreateOrderRequestDTO;
import org.luisbaquiax.orchestratorservice.dto.SagaResponseDTO;

public interface SagaOrchestratorService {
    SagaResponseDTO createOrderSaga(CreateOrderRequestDTO request);
    SagaResponseDTO getSagaStatus(Long orderId);
}