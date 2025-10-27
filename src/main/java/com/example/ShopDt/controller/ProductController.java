package com.example.ShopDt.controller;

import com.example.ShopDt.dto.request.ProductRequest;
import com.example.ShopDt.dto.response.ProductResponse;
import com.example.ShopDt.entity.Product;
import com.example.ShopDt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public  ProductResponse getProductById(@PathVariable long id){
        return  productService.findById(id);
    }

    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest request){
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable long id, @RequestBody ProductRequest request){
        return productService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id){
        productService.delete(id);
    }
}
