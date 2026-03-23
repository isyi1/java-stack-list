-- test.TB_USER definition

CREATE TABLE `TB_USER` (
                           `USER_SEQ` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '회원 고유 번호',
                           `USER_ID` varchar(50) NOT NULL COMMENT '로그인 아이디',
                           `USER_PWD` varchar(255) NOT NULL COMMENT '비밀번호 (해시 암호화)',
                           `USER_NAME` varchar(50) NOT NULL COMMENT '이름',
                           `ADDRESS` varchar(255) DEFAULT NULL COMMENT '주소',
                           `PHONE_NUM` varchar(20) DEFAULT NULL COMMENT '연락처',
                           `BIRTH_DATE` date DEFAULT NULL COMMENT '생년월일',
                           `GENDER` varchar(1) DEFAULT NULL,
                           `USER_STATUS` varchar(10) DEFAULT 'ACTIVE' COMMENT '계정 상태 (ACTIVE, SLEEP, WITHDRAW)',
                           `LAST_LOGIN_AT` datetime DEFAULT NULL COMMENT '마지막 로그인 일시',
                           `DEL_YN` varchar(1) DEFAULT NULL,
                           `REG_DT` datetime DEFAULT current_timestamp() COMMENT '작성일',
                           `REG_ID` varchar(50) DEFAULT NULL COMMENT '작성자',
                           `UPDT_DT` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일',
                           `UPDT_ID` varchar(50) DEFAULT NULL COMMENT '수정자',
                           PRIMARY KEY (`USER_SEQ`),
                           UNIQUE KEY `USER_ID` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci COMMENT='회원 정보 테이블';