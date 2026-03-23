package com.example.demo.dto.request;

public record UserRequest(
        String userId,
        String password,
        String userName,
        String phoneNum,
        String gender
) {}