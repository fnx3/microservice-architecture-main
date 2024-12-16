package ru.otus.billingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.billingservice.entity.UserWallet;
import ru.otus.billingservice.repository.WalletRepository;
import ru.otus.billingservice.service.AuthService;
import ru.otus.billingservice.service.WalletService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final AuthService authService;

    @Override
    public UUID createUserWallet(String userId) {
        return walletRepository.save(
                        UserWallet.builder()
                                .userId(userId)
                                .amount(BigDecimal.ZERO)
                                .build())
                .getId();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public UserWallet updateWallet(BigDecimal amount, HttpServletRequest httpServletRequest) {

        String userId = authService.getAuthUserId(httpServletRequest);
        Optional<UserWallet> walletOptional = walletRepository.getByUserId(userId);
        UserWallet wallet;
        if (walletOptional.isPresent()) {
            wallet = walletOptional.get();
        } else {
            throw new IllegalStateException("user with id " + userId + " does not have wallet");
        }
        BigDecimal sum = wallet.getAmount().add(amount);

        if (sum.doubleValue()>=0){
            return walletRepository.save(UserWallet.builder()
                            .id(wallet.getId())
                            .amount(sum)
                            .userId(userId)
                    .build());
        }
        throw new IllegalStateException("not enough money in the wallet");
    }

    @Override
    public UserWallet getUserWallet(HttpServletRequest httpServletRequest) {
        String userId = authService.getAuthUserId(httpServletRequest);
        Optional<UserWallet> walletOptional = walletRepository.getByUserId(userId);
        if (walletOptional.isPresent()) {
            return walletOptional.get();
        } else {
            throw new IllegalStateException("user with id " + userId + " does not have wallet");
        }
    }


}
