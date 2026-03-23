package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_USER")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "USER_ID", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "USER_PWD", nullable = false, length = 255)
    private String userPwd;

    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String userName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUM", length = 20)
    private String phoneNum;

    @Column(name = "BIRTH_DATE")
    private java.time.LocalDate birthDate;

    @Column(name = "GENDER", length = 1)
    private String gender;

    @Column(name = "USER_STATUS", length = 10)
    @Builder.Default
    private String userStatus = "ACTIVE";

    @Column(name = "LAST_LOGIN_AT")
    private LocalDateTime lastLoginAt;

    @Column(name = "DEL_YN", length = 1)
    @Builder.Default
    private String delYn = "N";

    // 생성 시 자동 시간 입력
    @CreationTimestamp
    @Column(name = "REG_DT", updatable = false)
    private LocalDateTime regDt;

    @Column(name = "REG_ID", length = 50)
    private String regId;

    // 수정 시 자동 시간 갱신
    @UpdateTimestamp
    @Column(name = "UPDT_DT")
    private LocalDateTime updtDt;

    @Column(name = "UPDT_ID", length = 50)
    private String updtId;
}