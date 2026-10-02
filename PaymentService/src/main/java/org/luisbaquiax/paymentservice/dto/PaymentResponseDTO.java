package org.luisbaquiax.paymentservice.dto;

import lombok.*;
import org.luisbaquiax.paymentservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long id;
    private Long orderId;
    private Long customerId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}