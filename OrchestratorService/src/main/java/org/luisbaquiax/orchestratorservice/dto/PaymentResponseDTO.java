package org.luisbaquiax.orchestratorservice.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long id;
    private Long orderId;
    private String status;
}