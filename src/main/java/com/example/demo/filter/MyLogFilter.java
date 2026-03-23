package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class MyLogFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(MyLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 1. 서버 기동시 한 번 실행
        log.info("LogFilter 초기화 - init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("--- [Filter 시작] Request URI: {} ---", requestURI);

            // 핵심: 다음 필터나 서블릿(DispatcherServlet)으로 요청을 넘깁니다.
            chain.doFilter(request, response);

        } finally {
            log.info("--- [Filter 종료] Request URI: {} ---", requestURI);
        }
    }

    @Override
    public void destroy() {
        // 3. 서버 다운시 한 번 실행
        log.info("LogFilter 소멸 - destroy");
    }
}