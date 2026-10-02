package org.luisbaquiax.paymentservice.exception;

public class PaymentNotFoundException extends PaymentException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
