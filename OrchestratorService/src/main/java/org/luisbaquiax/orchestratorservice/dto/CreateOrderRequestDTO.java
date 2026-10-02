package org.luisbaquiax.orchestratorservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateOrderRequestDTO {

    @NotNull
    private Long customerId;

    @NotBlank
    private String productId;

    @Min(1)
    private Integer quantity;

    @NotNull
    private BigDecimal totalAmount;

    @NotBlank
    private String address;

    // Solo para el POC
    private boolean simulatePaymentFailure = false;
}