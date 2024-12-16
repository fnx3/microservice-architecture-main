package ru.otus.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.billingservice.entity.UserWallet;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<UserWallet, UUID> {
    Optional<UserWallet> getByUserId(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE wallet SET amount = ?1 WHERE userId = ?2")
    int updateWalletAmout(BigDecimal amount, String userId);

    @Modifying
    @Transactional
    @Query("UPDATE wallet SET amount = amount + ?1 WHERE userId = ?2")
    int addToWallet(BigDecimal amount, String userId);
}
