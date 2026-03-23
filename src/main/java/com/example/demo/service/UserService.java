package com.example.demo.service;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;

public interface UserService {
    // 회원 가입
    void register(UserRequest request);

    // 회원 정보 조회
    UserResponse getUserInfo(String userId);

    // 회원 탈퇴 (예시)
    void withdraw(String userId);
}