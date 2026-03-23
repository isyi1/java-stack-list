package com.example.demo.dto.response;

/**
 * 로그인 응답 DTO
 * @param accessToken 발급된 JWT 토큼
 * @param userName    사용자 이름 (화면 표시용)
 */
public record LoginResponse(
        String accessToken,
        String userName
) {}