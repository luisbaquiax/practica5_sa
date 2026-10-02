package org.luisbaquiax.shippingservice.dto;

import lombok.*;
import org.luisbaquiax.shippingservice.enums.ShippingStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingResponseDTO {
    private Long id;
    private Long orderId;
    private String address;
    private ShippingStatus status;
}