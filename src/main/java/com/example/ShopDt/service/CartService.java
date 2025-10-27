package com.example.ShopDt.service;

import com.example.ShopDt.dto.request.CartRequest;
import com.example.ShopDt.dto.response.CartResponse;
import com.example.ShopDt.entity.Cart;
import com.example.ShopDt.entity.Product;
import com.example.ShopDt.entity.User;
import com.example.ShopDt.mapper.cart.CartMapper;
import com.example.ShopDt.repository.CartRepository;
import com.example.ShopDt.repository.ProductRepository;
import com.example.ShopDt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {


    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    //Lấy toàn bộ giỏ hàng của 1 user
    public List<CartResponse> getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUserId(userId)
                .stream()
                .map(cartMapper::toResponse)
                .collect(Collectors.toList());
    }

    // thêm sản phẩm vào giỏ
    public CartResponse addToCart(CartRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUserAndProduct(user, product)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + request.getQuantity());
                    return existing;
                })
                .orElseGet(() -> new Cart(null, user, product, request.getQuantity()));

        return cartMapper.toResponse(cartRepository.save(cart));
    }

    public CartResponse decreaseQuantity(CartRequest request) {
        User  user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        if(cart.getQuantity() > 1) {
            cart.setQuantity(cart.getQuantity() - 1);
            return cartMapper.toResponse(cartRepository.save(cart));
        } else {
            cartRepository.delete(cart);
            return null;
        }
    }

    public void removeFromCart(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartRepository.delete(cart);
    }
}

