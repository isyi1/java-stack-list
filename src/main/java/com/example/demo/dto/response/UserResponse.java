package com.example.demo.dto.response;

import com.example.demo.entity.UserEntity;

public record UserResponse(
        String userId,
        String userName,
        String phoneNum,
        String gender
) {
    // Entity를 DTO로 변환하는 정적 팩토리 메서드
    public static UserResponse from(UserEntity user) {
        return new UserResponse(
                user.getUserId(),
                user.getUserName(),
                user.getPhoneNum(),
                user.getGender()
        );
    }
}