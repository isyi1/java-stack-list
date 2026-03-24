package com.example.demo.service.impl;

import com.example.demo.service.BatchProducerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchProducerServiceImpl implements BatchProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "run-batch-topic";
    private static final Logger log = LoggerFactory.getLogger(BatchProducerServiceImpl.class);

    @Override
    public void sendJobRequest(String jobName) {
        log.info(">>> 카프카로 배치 실행 요청 전송: {}", jobName);
        // send(토픽명, 전송할 내용)
        kafkaTemplate.send(TOPIC, jobName);
    }

}
