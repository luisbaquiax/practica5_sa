package org.luisbaquiax.shippingservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.luisbaquiax.shippingservice.enums.ShippingStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "shippings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}