package org.luisbaquiax.orchestratorservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDTO {

    private Long customerId;
    private String productId;
    private Integer quantity;
    private BigDecimal totalAmount;
}