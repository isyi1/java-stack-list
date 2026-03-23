package com.example.demo.service;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.LoginResponse;

public interface AuthService {
    /**
     * 로그인을 처리하고 JWT 토큰을 반환합니다.
     */
    LoginResponse login(LoginRequest request);
}