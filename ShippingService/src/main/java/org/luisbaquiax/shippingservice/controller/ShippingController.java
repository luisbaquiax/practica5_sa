package org.luisbaquiax.shippingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.shippingservice.dto.ShippingRequestDTO;
import org.luisbaquiax.shippingservice.dto.ShippingResponseDTO;
import org.luisbaquiax.shippingservice.service.ShippingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/schedule")
    public ResponseEntity<ShippingResponseDTO> schedule(@Valid @RequestBody ShippingRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shippingService.schedule(request));
    }

    @DeleteMapping
    public ResponseEntity<Void> cancel(@RequestParam Long orderId) {
        shippingService.cancel(orderId);
        return ResponseEntity.noContent().build();
    }
}
