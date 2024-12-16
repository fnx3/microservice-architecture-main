package ru.otus.userapp.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity(name = "order_products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProduct {
    @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
}
