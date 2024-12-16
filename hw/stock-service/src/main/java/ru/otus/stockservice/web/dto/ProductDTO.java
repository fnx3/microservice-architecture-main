package ru.otus.stockservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductDTO {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    private int quantity;
}
