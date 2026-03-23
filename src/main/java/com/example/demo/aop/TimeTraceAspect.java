package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect // AOP로 사용하겠다는 선언
@Component // 스프링 빈으로 등록
public class TimeTraceAspect {

    private static final Logger log = LoggerFactory.getLogger(TimeTraceAspect.class);

    // execution(* com.example.demo.service..*(..)) :
    // com.example.demo.service 패키지 하위의 모든 메서드에 적용하겠다는 의미 (Pointcut)
    // @Before (서비스 호출 전) , @After (서비스 호출 후), @Around (전후 모두 제어)
    @Around("execution(* com.example.demo.service..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        log.info("START: {}", joinPoint.toString());

        try {
            // 실제 메서드를 호출합니다.
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            log.info("END: {} {}ms", joinPoint.toString(), timeMs);
        }
    }
}