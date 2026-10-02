package org.luisbaquiax.shippingservice.service;

import lombok.RequiredArgsConstructor;
import org.luisbaquiax.shippingservice.dto.ShippingRequestDTO;
import org.luisbaquiax.shippingservice.dto.ShippingResponseDTO;
import org.luisbaquiax.shippingservice.enums.ShippingStatus;
import org.luisbaquiax.shippingservice.exception.ShippingNotFoundException;
import org.luisbaquiax.shippingservice.model.Shipping;
import org.luisbaquiax.shippingservice.repository.ShippingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    @Override
    public ShippingResponseDTO schedule(ShippingRequestDTO request) {
        Shipping shipping = Shipping.builder()
                .orderId(request.getOrderId())
                .address(request.getAddress())
                .status(ShippingStatus.SCHEDULED)
                .build();

        return toDto(shippingRepository.save(shipping));
    }

    @Override
    public void cancel(Long orderId) {
        Shipping shipping = shippingRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ShippingNotFoundException(orderId));
        shipping.setStatus(ShippingStatus.CANCELLED);
        shippingRepository.save(shipping);
    }

    private ShippingResponseDTO toDto(Shipping s) {
        return ShippingResponseDTO.builder()
                .id(s.getId())
                .orderId(s.getOrderId())
                .address(s.getAddress())
                .status(s.getStatus())
                .build();
    }
}