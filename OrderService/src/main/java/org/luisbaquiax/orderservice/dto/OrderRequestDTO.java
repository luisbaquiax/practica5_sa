package org.luisbaquiax.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDTO {

    @NotNull(message = "Id del cliente  obligatorio")
    private Long customerId;

    @NotBlank(message = "Id del producto es obligatorio")
    private String productId;

    @Min(value = 1, message = "la cantidad debe ser al menos 1")
    private Integer quantity;

    @NotNull(message = "Monto total debe ser obligatorio")
    private BigDecimal totalAmount;
}