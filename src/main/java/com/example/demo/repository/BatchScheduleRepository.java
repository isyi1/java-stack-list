package com.example.demo.repository;

import com.example.demo.entity.BatchScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatchScheduleRepository extends JpaRepository<BatchScheduleEntity, String> {
    List<BatchScheduleEntity> findAllByEnabledTrue();
}