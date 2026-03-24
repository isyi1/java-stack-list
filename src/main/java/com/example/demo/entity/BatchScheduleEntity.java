package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_BATCH_SCHEDULE")
public class BatchScheduleEntity {
    @Id
    @Column(name = "JOB_NAME")
    private String jobName;  // 예: "simpleJob"

    @Column(name = "CRON")
    private String cron;     // 예: "0 0/1 * * * *" (1분마다)

    @Column(name = "ENABLED")
    private boolean enabled; // 활성화 여부

    @Column(name = "DESCRIPTION")
    private String description;
}