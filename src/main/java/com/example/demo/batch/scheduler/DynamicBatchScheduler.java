package com.example.demo.batch.scheduler;

import com.example.demo.entity.BatchScheduleEntity;
import com.example.demo.repository.BatchScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DynamicBatchScheduler {

    private final JobLauncher jobLauncher;
    private final List<Job> registeredJobs; // Spring Context에 등록된 모든 Job 리스트
    private final BatchScheduleRepository repository;
    private final TaskScheduler taskScheduler;

    // 현재 실행 중인 스케줄 정보를 담는 맵 (JobName, Future)
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    // 1분마다 DB를 조회하여 스케줄 동기화
    @Scheduled(fixedDelay = 60000)
    public void refreshSchedule() {
        log.info(">>> [1단계] DB에서 스케줄 목록을 가져옵니다...");
        List<BatchScheduleEntity> configs = repository.findAllByEnabledTrue();

        log.info(">>> [2단계] 조회된 활성 스케줄 개수: {}개", configs.size());

        for (BatchScheduleEntity config : configs) {
            // 이미 등록된 건 스킵
            if (scheduledTasks.containsKey(config.getJobName())) continue;

            // Spring에 등록된 Job 빈 찾기
            Job job = registeredJobs.stream()
                    .filter(j -> j.getName().equals(config.getJobName()))
                    .findFirst()
                    .orElse(null);

            if (job != null) {
                log.info(">>> [3단계] Job 찾음! '{}'을 {} 주기로 예약합니다.", config.getJobName(), config.getCron());
                ScheduledFuture<?> future = taskScheduler.schedule(
                        () -> runBatchJob(job),
                        new CronTrigger(config.getCron())
                );
                scheduledTasks.put(config.getJobName(), future);
            } else {
                // 이 로그가 찍히면 DB의 jobName이 잘못된 것입니다.
                log.error(">>> [경고] DB의 '{}' 이름과 일치하는 @Bean을 찾지 못했습니다.", config.getJobName());
            }
        }
    }

    private void runBatchJob(Job job) {
        try {
            log.info(">>> Launching Batch Job: {}", job.getName());
            jobLauncher.run(job, new JobParametersBuilder()
                    .addLong("requestTime", System.currentTimeMillis())
                    .toJobParameters());
        } catch (Exception e) {
            log.error(">>> Job Execution Failed: {}", job.getName(), e);
        }
    }
}