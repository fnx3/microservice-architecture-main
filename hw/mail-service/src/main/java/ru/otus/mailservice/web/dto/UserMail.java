package ru.otus.mailservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserMail {
    private String userId;
    private UUID orderId;
    private String content;
    private String paymentStatus;
}
