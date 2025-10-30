package com.example.ShopDt.controller;

import com.example.ShopDt.dto.request.ProductCategoryRequest;
import com.example.ShopDt.dto.response.ApiResponse;
import com.example.ShopDt.dto.response.ProductCategoryResponse;
import com.example.ShopDt.dto.response.ProductResponse;
import com.example.ShopDt.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("/{id}/products")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable Long id) {
        try {
            List<ProductResponse> products = categoryService.getProductByCategory(id);
            return ApiResponse.<List<ProductResponse>>builder()
                    .success(true)
                    .message("Lấy sản phẩm thành công")
                    .data(products)
                    .build();
        } catch(Exception ex) {
            return ApiResponse.<List<ProductResponse>>builder()
                    .success(false)
                    .message("Lấy sản phẩm thành công")
                    .error(ex.getMessage())
                    .build();
        }
    }

    @PostMapping("/add-product")
    public ApiResponse<ProductCategoryResponse> getProductCategory(@RequestBody ProductCategoryRequest request) {
        try{
            ProductCategoryResponse productToCategory = categoryService.addProductToCategory(request);
            return ApiResponse.<ProductCategoryResponse>builder()
                    .success(true)
                    .message("Thêm sản phẩm thành công")
                    .data(productToCategory)
                    .build();
        } catch(Exception ex) {
            return ApiResponse.<ProductCategoryResponse>builder()
                    .success(false)
                    .message("Thêm sản phẩm thất bại")
                    .error(ex.getMessage())
                    .build();
        }
    }
}
