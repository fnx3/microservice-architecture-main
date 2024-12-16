package ru.otus.mailservice.entity;

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
public class Mail {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "payment_status")
    private String paymentStatus;

    private String content;
}
