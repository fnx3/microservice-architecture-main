package ru.otus.userapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.common.entity.orders.UserOrder;

import java.util.List;

@Data
@AllArgsConstructor
public class UserOrdersResponse {
    private List<UserOrder> orders;
}
