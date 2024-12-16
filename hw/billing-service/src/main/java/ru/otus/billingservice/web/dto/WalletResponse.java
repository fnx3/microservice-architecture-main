package ru.otus.billingservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.billingservice.entity.UserWallet;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {

    private UUID walletId;
    private BigDecimal amount;

    public static WalletResponse fromDomain(UserWallet wallet){
        return new WalletResponse(wallet.getId(), wallet.getAmount());
    }
}
