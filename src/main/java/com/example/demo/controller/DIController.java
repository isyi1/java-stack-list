package com.example.demo.controller;

import com.example.demo.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DIController {

    // final 키워드를 붙여 불변성을 보장합니다.
    private final MessageService messageService;

    // 생성자 주입: 스프링이 MessageService 구현체를 찾아 자동으로 넣어줍니다.
    // 생성자가 하나만 있을 경우 @Autowired를 생략해도 무방합니다.
    public DIController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/di-test")
    public String test() {
        return messageService.getMessage();
    }
}

