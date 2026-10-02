package org.luisbaquiax.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    private String productId;

    @Column(nullable = false)
    private Integer availableQuantity;
}