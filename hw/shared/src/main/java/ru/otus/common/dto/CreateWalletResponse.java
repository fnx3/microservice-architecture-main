package ru.otus.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateWalletResponse {
    @NotNull
    private String userId;
    @NotNull
    private String walletId;
}
