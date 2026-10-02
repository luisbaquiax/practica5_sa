package org.luisbaquiax.orchestratorservice.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {
    private Long orderId;
    private String productId;
    private Integer quantity;
}