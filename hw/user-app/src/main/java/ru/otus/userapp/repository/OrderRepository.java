package ru.otus.userapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.common.entity.orders.OrderStatus;
import ru.otus.userapp.entity.Order;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findOrdersByUserId(String userID);

    @Modifying
    @Query("update orders o set o.status = ?1 where o.id = ?2")
    int updateOrderStatus(OrderStatus status, UUID orderId);

    boolean existsById(UUID id);
}
