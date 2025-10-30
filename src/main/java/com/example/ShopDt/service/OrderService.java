package com.example.ShopDt.service;

import com.example.ShopDt.dto.request.OrderRequest;
import com.example.ShopDt.dto.response.OrderResponse;
import com.example.ShopDt.entity.*;
import com.example.ShopDt.mapper.order.OrderMapper;
import com.example.ShopDt.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest request) {
        Long userId = request.getUserId();

        // Lấy toàn bộ giỏ hàng
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // Lấy thông tin user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        // Tạo đơn hàng mới
        Order order = new Order();
        order.setUser(user);
        order.setCreateAt(LocalDateTime.now());
        order.setStatus(1);
        order.setNote(request.getNote());
        order.setTotalPrice(0f);
        order = orderRepository.save(order);

        float totalPrice = 0f;

        // Xử lý từng item trong cart
        for (Cart cart : cartItems) {
            Product product = cart.getProduct();

            if (product.getQuantity() < cart.getQuantity()) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ số lượng tồn kho!");
            }

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(cart.getQuantity());
            detail.setPrice(product.getPrice() * cart.getQuantity());
            detail.setStatus(1);
            orderDetailRepository.save(detail);

            // Giảm số lượng tồn kho
            product.setQuantity(product.getQuantity() - cart.getQuantity());
            productRepository.save(product);

            totalPrice += detail.getPrice();
        }

        //Cập nhật tổng giá trị đơn hàng
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Xóa giỏ hàng
        cartRepository.deleteAll(cartItems);

        //  Trả về DTO phản hồi
        return orderMapper.toOrderResponse(order);
    }
}
