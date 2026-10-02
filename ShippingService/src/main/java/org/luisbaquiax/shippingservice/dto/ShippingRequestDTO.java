package org.luisbaquiax.shippingservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShippingRequestDTO {

    @NotNull(message = "ID de la oreden obligatorio")
    private Long orderId;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;
}