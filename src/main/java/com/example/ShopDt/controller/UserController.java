package com.example.ShopDt.controller;

import com.example.ShopDt.dto.request.LoginRequest;
import com.example.ShopDt.dto.request.RegisterRequest;
import com.example.ShopDt.dto.request.UpdateRequest;
import com.example.ShopDt.dto.response.ApiResponse;
import com.example.ShopDt.dto.response.UserResponse;
import com.example.ShopDt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User")

public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Đăng kí")

    public ApiResponse<UserResponse> createUser(@RequestBody RegisterRequest request) {
        try {
            UserResponse user = userService.createUser(request);
            return ApiResponse.<UserResponse>builder()
                    .success(true)
                    .message("Tạo người dùng thành công")
                    .data(user)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserResponse>builder()
                    .success(false)
                    .message("Tạo người dùng thất bại")
                    .error(e.getMessage())
                    .build();
        }
    }


    //  Login bằng username và password
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequest request) {
        userService.login(request.getUsername(), request.getPassword());
        return ApiResponse.<String>builder()
                .success(true)
                .message("Đăng nhập thành công với user: " + request.getUsername())
                .build();
    }

    //Update user hiện đang đăng nhập
    @PutMapping("/update")
    public ApiResponse<UserResponse> updateUser(@RequestBody UpdateRequest request) {
        try {
            UserResponse user = userService.updateUser(request);
            return ApiResponse.<UserResponse>builder()
                    .data(user)
                    .message("Cập nhật thành công")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserResponse>builder()
                    .message("Cập nhật thất bại")
                    .success(false)
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping()
    @Operation(summary = "Lấy danh sách người dùng", description = "Trả về danh sách toàn bộ người dùng trong hệ thống.")

    public ApiResponse<List<UserResponse>> getUsers() {
        try {
            List<UserResponse> users = userService.getUsers();

            if (users == null || users.isEmpty()) {
                return ApiResponse.<List<UserResponse>>builder()
                        .success(true)
                        .message("Không có người dùng nào trong hệ thống.")
                        .data(List.of()) // Trả về mảng rỗng thay vì null
                        .build();
            }

            return ApiResponse.<List<UserResponse>>builder()
                    .success(true)
                    .message("Lấy danh sách người dùng thành công.")
                    .data(users)
                    .build();

        } catch (Exception e) {
            return ApiResponse.<List<UserResponse>>builder()
                    .success(false)
                    .message("Không thể lấy danh sách người dùng.")
                    .error(e.getMessage())
                    .data(List.of())
                    .build();
        }


    }
}
