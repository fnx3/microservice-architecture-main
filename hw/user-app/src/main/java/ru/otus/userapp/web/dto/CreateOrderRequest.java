package ru.otus.userapp.web.dto;

import lombok.Data;
import ru.otus.common.entity.orders.PaymentMethod;
import ru.otus.common.entity.orders.Product;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CreateOrderRequest {
    @NotNull
    private UUID id;
    @NotNull
    private PaymentMethod paymentMethod;
    @Valid
    private List<Product> products;
    @NotEmpty
    private String deliveryLocation;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=6, fraction=2)
    private BigDecimal price;
}
