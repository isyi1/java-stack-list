package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        // 1. 사용자 조회 (ID 기준)
        UserEntity user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 아이디입니다."));

        // 2. 비밀번호 일치 여부 확인
        // passwordEncoder.matches(평문 비밀번호, 암호화된 비밀번호)
        if (!passwordEncoder.matches(request.password(), user.getUserPwd())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 성공 시 토큰 생성
        String token = jwtTokenProvider.createToken(user);

        return new LoginResponse(token, user.getUserName());
    }
}