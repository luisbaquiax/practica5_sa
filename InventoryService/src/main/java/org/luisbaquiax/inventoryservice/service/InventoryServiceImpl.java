package org.luisbaquiax.inventoryservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.luisbaquiax.inventoryservice.dto.InventoryRequestDTO;
import org.luisbaquiax.inventoryservice.dto.InventoryResponseDTO;
import org.luisbaquiax.inventoryservice.enums.ReservationStatus;
import org.luisbaquiax.inventoryservice.exception.InsufficientStockException;
import org.luisbaquiax.inventoryservice.exception.ReservationNotFoundException;
import org.luisbaquiax.inventoryservice.model.Reservation;
import org.luisbaquiax.inventoryservice.model.Stock;
import org.luisbaquiax.inventoryservice.repository.ReservationRepository;
import org.luisbaquiax.inventoryservice.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final StockRepository stockRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public InventoryResponseDTO reserve(InventoryRequestDTO request) {
        Stock stock = stockRepository.findById(request.getProductId())
                .orElseThrow(() -> new InsufficientStockException(request.getProductId()));

        if (stock.getAvailableQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(request.getProductId());
        }

        stock.setAvailableQuantity(stock.getAvailableQuantity() - request.getQuantity());
        stockRepository.save(stock);

        Reservation reservation = Reservation.builder()
                .orderId(request.getOrderId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(ReservationStatus.RESERVED)
                .build();

        return toDto(reservationRepository.save(reservation));
    }

    @Override
    @Transactional
    public InventoryResponseDTO release(Long orderId) {
        Reservation reservation = reservationRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ReservationNotFoundException(String.format("Reservacion no encontrada para el pedido %s", orderId)));

        Stock stock = stockRepository.findById(reservation.getProductId())
                .orElseThrow(() -> new InsufficientStockException(reservation.getProductId()));

        stock.setAvailableQuantity(stock.getAvailableQuantity() + reservation.getQuantity());
        stockRepository.save(stock);

        reservation.setStatus(ReservationStatus.RELEASED);
        return toDto(reservationRepository.save(reservation));
    }

    private InventoryResponseDTO toDto(Reservation r) {
        return InventoryResponseDTO.builder()
                .reservationId(r.getId())
                .orderId(r.getOrderId())
                .productId(r.getProductId())
                .quantity(r.getQuantity())
                .status(r.getStatus())
                .build();
    }
}