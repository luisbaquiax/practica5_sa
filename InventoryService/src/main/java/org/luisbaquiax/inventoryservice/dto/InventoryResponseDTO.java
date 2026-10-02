package org.luisbaquiax.inventoryservice.dto;

import lombok.*;
import org.luisbaquiax.inventoryservice.enums.ReservationStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponseDTO {
    private Long reservationId;
    private Long orderId;
    private String productId;
    private Integer quantity;
    private ReservationStatus status;
}