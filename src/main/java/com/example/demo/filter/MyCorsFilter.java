package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

public class MyCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 모든 도메인에서의 요청을 허용 (보안상 실제 서비스에서는 특정 도메인만 지정하는 것이 좋습니다)
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        // 허용할 HTTP 메서드 설정
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");

        // 브라우저가 보낼 수 있는 요청 헤더 설정
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");

        // 브라우저가 이 설정 정보를 캐싱할 시간 (1시간)
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        // OPTIONS 메서드는 브라우저가 실제 요청 전 권한이 있는지 확인하는 'Preflight' 요청입니다.
        // 이 요청에는 비즈니스 로직을 탈 필요가 없으므로 OK를 응답하고 끝냅니다.
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }
}