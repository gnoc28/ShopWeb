package com.example.ShopDt.controller;

import com.example.ShopDt.dto.request.OrderRequest;
import com.example.ShopDt.dto.response.ApiResponse;
import com.example.ShopDt.dto.response.OrderResponse;
import com.example.ShopDt.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        try{
            OrderResponse order = orderService.createOrder(request);
            return ApiResponse.<OrderResponse>builder()
                    .success(true)
                    .message("xuất đơn thành công")
                    .data(order)
                    .build();
        } catch(Exception ex){
            return ApiResponse.<OrderResponse>builder()
                    .success(false)
                    .message("xuất đơn thất bại")
                    .error(ex.getMessage())
                    .build();
        }
    }
}
