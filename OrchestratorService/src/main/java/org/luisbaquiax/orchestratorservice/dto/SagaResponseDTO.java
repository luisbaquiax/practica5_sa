package org.luisbaquiax.orchestratorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luisbaquiax.orchestratorservice.enums.SagaStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaResponseDTO {
    private Long orderId;
    private SagaStatus status;
    private String failureReason;
}