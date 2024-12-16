package ru.otus.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.stockservice.entity.StockProduct;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<StockProduct, UUID> {
}
