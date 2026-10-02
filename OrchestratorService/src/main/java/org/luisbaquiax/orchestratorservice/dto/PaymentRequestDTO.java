package org.luisbaquiax.orchestratorservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {
    private Long orderId;
    private Long customerId;
    private BigDecimal amount;
    private boolean simulateFailure;
}