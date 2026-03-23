package com.example.demo.service.impl;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(UserRequest request) {
        // 1. 중복 가입 확인
        userRepository.findByUserId(request.userId())
                .ifPresent(u -> { throw new RuntimeException("이미 존재하는 아이디입니다."); });

        // 2. 비밀번호 암호화 및 엔티티 생성
        UserEntity user = UserEntity.builder()
                .userId(request.userId())
                .userPwd(passwordEncoder.encode(request.password()))
                .userName(request.userName())
                .phoneNum(request.phoneNum())
                .gender(request.gender())
                .build();

        // 3. 저장
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void withdraw(String userId) {
        UserEntity user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }
}