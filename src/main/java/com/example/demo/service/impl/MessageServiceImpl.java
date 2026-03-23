package com.example.demo.service.impl;

import com.example.demo.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "Hello! 이것은 스프링 DI를 통해 주입된 메시지입니다.";
    }
}