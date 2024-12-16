package ru.otus.stockservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity(name = "stock_product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockProduct {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private int quantity;
}
