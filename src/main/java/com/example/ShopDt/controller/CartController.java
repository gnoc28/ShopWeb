package com.example.ShopDt.controller;

import com.example.ShopDt.dto.request.CartRequest;
import com.example.ShopDt.dto.response.CartResponse;
import com.example.ShopDt.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carts")
@RequiredArgsConstructor
@Tag(name = "Cart")

public class CartController {
    final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponse>> getCart(@PathVariable long userId){
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.addToCart(cartRequest));
    }

    @PostMapping("/decrease")
    public ResponseEntity<CartResponse> decreaseCart(@RequestBody CartRequest cartRequest){
        return ResponseEntity.ok(cartService.decreaseQuantity(cartRequest));
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long userId, @PathVariable Long productId){
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

}
