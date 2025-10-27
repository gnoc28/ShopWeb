package com.example.ShopDt.service;

import com.example.ShopDt.dto.request.ProductRequest;
import com.example.ShopDt.dto.response.ProductResponse;
import com.example.ShopDt.entity.Product;
import com.example.ShopDt.mapper.product.ProductMapper;
import com.example.ShopDt.repository.ProductRepository;
import com.example.ShopDt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse findById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toResponse(product);
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        return productMapper.toResponse(productRepository.save(product));
    }
    public ProductResponse update(long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productMapper.updateEntity(product, productRequest);
        return productMapper.toResponse(productRepository.save(product));
    }

    public void delete(long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
}
