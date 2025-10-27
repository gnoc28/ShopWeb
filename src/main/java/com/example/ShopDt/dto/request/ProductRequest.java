package com.example.ShopDt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private float price;
    private int quantity;
    private String image;
    private float rate;
    private String description;
    private int status;
}
