package org.luisbaquiax.paymentservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "Id de la orden es obligatorio")
    private Long orderId;

    @NotNull(message = "Id del cliente es obligatorio")
    private Long customerId;

    @NotNull(message = "Monto obligatorio")
    private BigDecimal amount;

    private boolean simulateFailure = false;
}