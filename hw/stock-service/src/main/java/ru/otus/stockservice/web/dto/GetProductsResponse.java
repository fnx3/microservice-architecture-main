package ru.otus.stockservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.stockservice.entity.StockProduct;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetProductsResponse {
    private List<ProductDTO> products;

    public static GetProductsResponse fromDomain(List<StockProduct> stockProducts) {
        return GetProductsResponse.builder()
                .products(stockProducts.stream()
                        .map(stockProduct -> new ProductDTO(stockProduct.getId(), stockProduct.getName(), stockProduct.getQuantity()))
                        .toList())
                .build();
    }
}
