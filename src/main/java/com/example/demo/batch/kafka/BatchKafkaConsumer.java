package com.example.demo.batch.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchKafkaConsumer {

    private final JobLauncher jobLauncher;
    private final List<Job> registeredJobs;

    @KafkaListener(topics = "run-batch-topic")
    public void listen(String jobName) {
        log.info(">>> 카프카 메시지 수신! 실행할 Job 이름: {}", jobName);

        // 빈 이름으로 Job 찾기
        Job job = registeredJobs.stream()
                .filter(j -> j.getName().equals(jobName))
                .findFirst()
                .orElse(null);

        if (job != null) {
            try {
                jobLauncher.run(job, new JobParametersBuilder()
                        .addLong("requestTime", System.currentTimeMillis())
                        .toJobParameters());
                log.info(">>> 카프카 트리거에 의해 {} 배치 실행 성공!", jobName);
            } catch (Exception e) {
                log.error(">>> 배치 실행 중 에러 발생", e);
            }
        } else {
            log.warn(">>> '{}' 이름의 Job을 찾을 수 없습니다.", jobName);
        }
    }
}