package com.example.ShopDt.dto.response;

import lombok.Data;

import java.util.Date;

@Data

public class UserResponse {
    private String username;
    private String email;
    private Date dob;
    private String role;
}
