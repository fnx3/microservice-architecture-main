package ru.otus.stockservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.stockservice.service.StockService;
import ru.otus.stockservice.web.dto.GetProductsResponse;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final StockService stockService;

    @GetMapping
    public GetProductsResponse getProducts(){
        return GetProductsResponse.fromDomain(stockService.getAllProducts());
    }
}
