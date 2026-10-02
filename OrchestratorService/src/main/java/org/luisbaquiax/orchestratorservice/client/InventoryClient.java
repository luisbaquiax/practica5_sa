package org.luisbaquiax.orchestratorservice.client;

import org.luisbaquiax.orchestratorservice.dto.InventoryRequestDTO;
import org.luisbaquiax.orchestratorservice.dto.InventoryResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service", url = "${services.inventory.url}")
public interface InventoryClient {

    @PostMapping("/inventory/reserve")
    InventoryResponseDTO reserve(@RequestBody InventoryRequestDTO request);

    @PostMapping("/inventory/release")
    InventoryResponseDTO release(@RequestParam("orderId") Long orderId);
}