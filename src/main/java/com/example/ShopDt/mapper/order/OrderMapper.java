package com.example.ShopDt.mapper.order;

import com.example.ShopDt.dto.response.*;
import com.example.ShopDt.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "createAt", target = "createdAt")
    @Mapping(source = "orderDetails", target = "orderDetails")
    OrderResponse toOrderResponse(Order order);

}
