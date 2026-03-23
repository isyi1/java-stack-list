package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyLogInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MyLogInterceptor.class);

    // 1. 컨트롤러 호출 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info(">>> [Interceptor preHandle] URI: {}", requestURI);

        // 어떤 컨트롤러와 메서드가 호출되는지 확인할 수 있음
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("--- 호출될 컨트롤러 메서드: {}", hm.getShortLogMessage());
        }

        return true; // true면 다음 단계로 진행, false면 중단
    }

    // 2. 컨트롤러 호출 후 (예외 발생 시 호출 안 됨)
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">>> [Interceptor postHandle]");
    }

    // 3. 요청 완료 후 (뷰 랜더링 이후, 예외 발생해도 호출 됨)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        long startTime = (long) request.getAttribute("startTime");
//        long endTime = System.currentTimeMillis();
//        log.info("요청 처리 완료! 소요시간: {}ms", (endTime - startTime));

        if (ex != null) {
            log.error("--- 요청 처리 중 에러 발생: ", ex);
        }
    }
}