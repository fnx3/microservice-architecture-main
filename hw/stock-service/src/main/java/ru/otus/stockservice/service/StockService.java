package ru.otus.stockservice.service;

import ru.otus.common.entity.orders.message.OrderMessage;
import ru.otus.stockservice.entity.StockProduct;

import java.util.List;

public interface StockService {
    void takeProductsFromStock(OrderMessage orderMessage);

    List<StockProduct> getAllProducts();
}
