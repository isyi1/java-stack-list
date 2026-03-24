# ## Java 학습 프로젝트

이 프로젝트는 자바 백엔드 개발의 핵심 아키텍처부터 대용량 데이터 처리까지, 단계별 기술 스택을 학습하고 기록하기 위한 저장소입니다.

---

### 🛠 Tech Stack & Tools
* **Language:** Java 17
* **Framework:** Spring Boot 3.4.3
* **Database:** MariaDB (Docker 기반)
* **Security:** Spring Security & JWT
* **Infrastructure:** Docker

---

### 📖 학습 및 구현 내용

#### 1. Spring Core & Web Fundamentals
스프링 프레임워크의 동작 원리와 공통 관심사 처리 방식을 학습하고 적용했습니다.
* **DI (Dependency Injection):** 객체 간 결합도 해소 및 유연한 코드 설계
* **Filter & Interceptor:** HTTP 요청/응답 단계에서의 공통 로직(인증 체크, CORS) 분리 및 실행 시점의 차이 이해
* **AOP (Aspect Oriented Programming):** 로깅 추가 테스트

#### 2. Authentication & Authorization (Security)
* **Spring Security:** 어플리케이션 전반의 보안 가이드라인 및 인증/인가 필터 체인 적용
* **JWT (JSON Web Token):** 인증 환경을 구축하고, Access 토큰 기반의 사용자 권한 관리 구현 (인증방식: HMAC)

#### 3. Spring Batch
* Spring Batch 메타데이터 관리: 배치 실행 이력 및 상태를 9개의 메타 테이블을 통해 관리합니다.
* DB 기반(TB_BATCH_SCHEDULE 테이블) 동적 크론(Cron) 스케줄링
* 서버 재기동 없이 DB 데이터 수정만으로 배치 주기 변경 및 활성화/비활성화가 가능합니다.

#### 4. kafka
sequenceDiagram
autonumber
actor User as 사용자 (Postman/Browser)
participant Ctrl as BatchTestController
participant Service as BatchProducerService
participant Kafka as Kafka Broker (run-batch-topic)
participant Consumer as BatchKafkaConsumer
participant Launcher as JobLauncher (Spring Batch)
participant Job as SimpleJob (Batch Job)

    User->>Ctrl: GET /api/batch/run/simpleJob
    Ctrl->>Service: sendJobRequest("simpleJob")
    Service->>Kafka: KafkaTemplate.send("simpleJob")
    Note over Service,Kafka: 메시지 발행 (Produce)
    
    Kafka-->>Consumer: @KafkaListener 감지
    Note over Kafka,Consumer: 메시지 수신 (Consume)
    
    Consumer->>Consumer: registeredJobs에서 "simpleJob" 찾기
    
    alt Job 존재함
        Consumer->>Launcher: run(job, parameters)
        Launcher->>Job: 배치 실행 시작
        Job-->>User: (비동기) 실행 로그 확인 가능
    else Job 존재하지 않음
        Consumer-->>Consumer: 로그 출력 ("Job을 찾을 수 없음")
    end

    Ctrl-->>User: "실행 요청 전달됨" 응답

---

### 🐳 Infrastructure (Database)
데이터베이스 환경의 일관성과 간편한 설정을 위해 **Docker**를 활용하여 MariaDB, kakfa를 운용하고 있습니다.
