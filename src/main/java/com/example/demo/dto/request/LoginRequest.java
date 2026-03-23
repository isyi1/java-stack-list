package com.example.demo.dto.request;

/**
 * 로그인 요청 DTO
 * @param userId   사용자 아이디
 * @param password 사용자 비밀번호 (평문)
 */
public record LoginRequest(
        String userId,
        String password
) {}