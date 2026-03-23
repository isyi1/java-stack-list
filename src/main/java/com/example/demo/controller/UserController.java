package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService; // 인터페이스 주입

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserRequest request) {
        userService.register(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getInfo(@PathVariable String userId) {
        UserResponse user = userService.getUserInfo(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("사용자를 찾을 수 없습니다."));
        }

        return ResponseEntity.ok(ApiResponse.success(user));
    }
}