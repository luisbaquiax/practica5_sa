package org.luisbaquiax.orchestratorservice.dto;

import lombok.Data;

@Data
public class ShippingRequestDTO {
    private Long orderId;
    private String address;
}