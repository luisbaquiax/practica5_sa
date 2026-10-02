package org.luisbaquiax.inventoryservice.exception;

public class InsufficientStockException extends InventoryExcpetion {
    public InsufficientStockException(String productId) {
        super(String.format("No hay suficiente stock para la orden %s", productId));
    }
}
