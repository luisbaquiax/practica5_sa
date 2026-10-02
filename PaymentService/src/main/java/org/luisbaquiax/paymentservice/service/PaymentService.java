package org.luisbaquiax.paymentservice.service;

import org.luisbaquiax.paymentservice.dto.PaymentRequestDTO;
import org.luisbaquiax.paymentservice.dto.PaymentResponseDTO;

public interface PaymentService {
    PaymentResponseDTO charge(PaymentRequestDTO request);
    PaymentResponseDTO refund(Long orderId);
}