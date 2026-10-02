package org.luisbaquiax.inventoryservice.repository;

import org.luisbaquiax.inventoryservice.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}