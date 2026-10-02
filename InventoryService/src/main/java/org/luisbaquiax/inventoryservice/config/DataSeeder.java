package org.luisbaquiax.inventoryservice.config;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.inventoryservice.model.Stock;
import org.luisbaquiax.inventoryservice.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final StockRepository stockRepository;

    @Override
    public void run(String... args) {
        if (stockRepository.count() == 0) {
            stockRepository.save(Stock.builder().productId("PROD-001").availableQuantity(10).build());
            stockRepository.save(Stock.builder().productId("PROD-002").availableQuantity(0).build()); // sin stock a propósito
        }
    }
}