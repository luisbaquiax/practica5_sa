package org.luisbaquiax.shippingservice.exception;

public class ShippingNotFoundException extends ShippingException {
    public ShippingNotFoundException(Long orderId) {
        super("Envío no encontrado para orden con id: " + orderId);
    }
}