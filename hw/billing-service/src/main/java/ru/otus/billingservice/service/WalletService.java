package ru.otus.billingservice.service;

import ru.otus.billingservice.entity.UserWallet;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {
    UUID createUserWallet(String userId);

    UserWallet updateWallet(BigDecimal amount, HttpServletRequest httpServletRequest);

    UserWallet getUserWallet(HttpServletRequest httpServletRequest);
}
