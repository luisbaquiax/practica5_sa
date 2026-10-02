package org.luisbaquiax.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {

    @NotNull(message = "Id de la orden obligatorio")
    private Long orderId;

    @NotBlank(message = "Id del producto obligatorio")
    private String productId;

    @Min(value = 1, message = "la cantidad debe ser al menos 1")
    private Integer quantity;
}