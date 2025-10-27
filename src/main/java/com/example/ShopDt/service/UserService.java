package com.example.ShopDt.service;

import com.example.ShopDt.dto.request.RegisterRequest;
import com.example.ShopDt.dto.request.UpdateRequest;
import com.example.ShopDt.dto.response.UserResponse;
import com.example.ShopDt.entity.Role;
import com.example.ShopDt.entity.User;
import com.example.ShopDt.mapper.user.UserMapper;
import com.example.ShopDt.repository.RoleRepository;
import com.example.ShopDt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private static String currentUserEmail = null;

    public UserResponse createUser(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Email '{}' đã tồn tại", request.getEmail());
            throw new IllegalArgumentException("Email đã tồn tại");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            log.warn("Mật khẩu là bắt buộc");
            throw new IllegalArgumentException("Mật khẩu là bắt buộc");
        }

        User user = userMapper.toUser(request);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());;
        Role role = roleRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Role không tồn tại"));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return userMapper.toUserResponse(savedUser);
    }

    //  Login bằng username và password
    public void login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy username: " + username));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Sai mật khẩu!");
        }

        currentUserEmail = user.getEmail();
        log.info("User '{}' đã đăng nhập thành công", username);
    }

    // ✅ Update user đang login
    public UserResponse updateUser(UpdateRequest request) {
        if (currentUserEmail == null) {
            throw new IllegalStateException("Chưa đăng nhập, không thể cập nhật");
        }

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy user đang đăng nhập"));

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());

        return userMapper.toUserResponse(userRepository.save(user));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

}
