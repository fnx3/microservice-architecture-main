package ru.otus.billingservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.billingservice.service.WalletService;
import ru.otus.billingservice.web.dto.UpdateWalletRequest;
import ru.otus.billingservice.web.dto.WalletResponse;
import ru.otus.common.dto.CreateWalletRequest;
import ru.otus.common.dto.CreateWalletResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class BillingController {

    private final WalletService walletService;

    @PostMapping
    public CreateWalletResponse createWallet(@Validated @RequestBody CreateWalletRequest request) {
        return new CreateWalletResponse(request.getUserId(), walletService.createUserWallet(request.getUserId()).toString());
    }

    @PostMapping("/update")
    public WalletResponse updateWallet(@Validated @RequestBody UpdateWalletRequest request, HttpServletRequest httpServletRequest){
        return WalletResponse.fromDomain(walletService.updateWallet(request.getAmount(), httpServletRequest));
    }

    @GetMapping
    public WalletResponse getUserWallet(HttpServletRequest httpServletRequest){
        return WalletResponse.fromDomain(walletService.getUserWallet(httpServletRequest));
    }
}
