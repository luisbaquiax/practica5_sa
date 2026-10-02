package org.luisbaquiax.inventoryservice.service;

import org.luisbaquiax.inventoryservice.dto.InventoryRequestDTO;
import org.luisbaquiax.inventoryservice.dto.InventoryResponseDTO;

public interface InventoryService {
    InventoryResponseDTO reserve(InventoryRequestDTO request);
    InventoryResponseDTO release(Long orderId);
}