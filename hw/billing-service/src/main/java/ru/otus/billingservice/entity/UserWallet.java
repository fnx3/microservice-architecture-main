package ru.otus.billingservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.otus.billingservice.repository.UuidConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table
@Entity(name = "wallet")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserWallet {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "user_id")
    private String userId;

    private BigDecimal amount;

}
