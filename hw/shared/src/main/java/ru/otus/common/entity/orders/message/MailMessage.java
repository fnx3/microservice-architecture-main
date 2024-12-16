package ru.otus.common.entity.orders.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailMessage {
    private UUID orderId;
    private String userId;
    private String paymentStatus;
}
