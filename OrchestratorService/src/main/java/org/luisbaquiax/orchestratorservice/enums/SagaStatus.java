package org.luisbaquiax.orchestratorservice.enums;

public enum SagaStatus {
    STARTED,
    ORDER_CREATED,
    PAYMENT_COMPLETED,
    INVENTORY_RESERVED,
    SHIPPING_SCHEDULED,
    COMPLETED,
    COMPENSATING,
    FAILED
}