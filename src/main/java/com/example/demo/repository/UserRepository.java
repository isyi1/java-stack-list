package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // USER_ID 컬럼으로 사용자를 찾는 쿼리 메서드
    Optional<UserEntity> findByUserId(String userId);
}