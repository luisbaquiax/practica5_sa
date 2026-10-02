package org.luisbaquiax.orchestratorservice.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long reservationId;
    private Long orderId;
    private String status;
}