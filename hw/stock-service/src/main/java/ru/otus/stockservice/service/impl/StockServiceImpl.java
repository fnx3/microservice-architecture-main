package ru.otus.stockservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.common.entity.orders.Product;
import ru.otus.common.entity.orders.message.OrderMessage;
import ru.otus.stockservice.entity.StockProduct;
import ru.otus.stockservice.repository.ProductRepository;
import ru.otus.stockservice.service.OrderProducer;
import ru.otus.stockservice.service.StockService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final ProductRepository productRepository;
    private final OrderProducer orderProducer;

    @Override
    public void takeProductsFromStock(OrderMessage orderMessage) {
        Map<UUID, Product> uuidProductMap = orderMessage.getProducts()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        try {
            uuidProductMap.forEach((k, v) -> {
                StockProduct stockProduct = productRepository.findById(k)
                        .orElseThrow(() -> new IllegalStateException(String.format("product with id %s is out of stock", k)));
                int updatedProductQuantity = stockProduct.getQuantity() - v.getQuantity();
                if (updatedProductQuantity < 0) {
                    throw new IllegalStateException(String.format("product with id %s is out of stock", k));
                }
                stockProduct.setQuantity(updatedProductQuantity);
                productRepository.save(stockProduct);
            });
            orderMessage.setStatus(OrderStatus.ORDER_COLLECTED);
            orderProducer.send(orderMessage);

        } catch (Exception exception) {
            log.error(exception.getMessage());
            orderMessage.setStatus(OrderStatus.STOCK_ERROR);
            orderProducer.send(orderMessage);

        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockProduct> getAllProducts() {
        return productRepository.findAll();
    }
}
