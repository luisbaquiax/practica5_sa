package org.luisbaquiax.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.inventoryservice.dto.InventoryRequestDTO;
import org.luisbaquiax.inventoryservice.dto.InventoryResponseDTO;
import org.luisbaquiax.inventoryservice.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/reserve")
    public ResponseEntity<InventoryResponseDTO> reserve(@Valid @RequestBody InventoryRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.reserve(request));
    }

    @PostMapping("/release")
    public ResponseEntity<InventoryResponseDTO> release(@RequestParam Long orderId) {
        return ResponseEntity.ok(inventoryService.release(orderId));
    }
}