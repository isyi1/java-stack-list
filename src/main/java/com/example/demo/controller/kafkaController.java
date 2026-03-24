package com.example.demo.controller;

import com.example.demo.service.BatchProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class kafkaController {
    private final BatchProducerService batchProducerService;

    @GetMapping("/run/{jobName}")
    public String triggerBatch(@PathVariable String jobName) {
        // 코드로 카프카 메시지 전송!
        batchProducerService.sendJobRequest(jobName);
        return jobName + " 배치 실행 요청이 카프카로 전달되었습니다.";
    }

}
