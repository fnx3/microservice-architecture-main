package ru.otus.userapp.web.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.userapp.entity.Order;
import ru.otus.userapp.service.OrderService;
import ru.otus.userapp.web.dto.CreateOrderRequest;
import ru.otus.userapp.web.dto.UserOrdersResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<UUID> createOrder(@Validated @RequestBody CreateOrderRequest request, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(orderService.createOrder(request.getId(), request.getPaymentMethod(), request.getDeliveryLocation(), request.getProducts(), request.getPrice(), httpServletRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<UserOrdersResponse> getUserOrderS(HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(new UserOrdersResponse(orderService.getUserOrders(httpServletRequest).stream()
                .map(Order::fromDomain)
                .toList()),
                HttpStatus.OK);
    }
}
