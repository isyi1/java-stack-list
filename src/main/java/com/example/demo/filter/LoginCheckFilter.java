package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class LoginCheckFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoginCheckFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        try {
            log.info("인증 체크 필터 시작: {}", requestURI);

            // 세션을 가져옵니다. (없으면 null 반환)
/*
            HttpSession session = httpRequest.getSession(false);

            // 로그인 세션이 없으면 (예: "loginUser"라는 이름의 객체가 세션에 없으면)
            if (session == null || session.getAttribute("loginUser") == null) {
                log.info("미인증 사용자 요청 거부: {}", requestURI);

                // 로그인 페이지로 리다이렉트 시키거나 401 에러를 보냅니다.
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");

                // 중요: chain.doFilter를 호출하지 않아야 다음 단계로 진행되지 않습니다!
                return;
            }
*/
            // 인증된 사용자라면 다음 필터나 서블릿으로 진행
            chain.doFilter(request, response);

        } catch (Exception e) {
            throw e; // 예외 처리는 톰캣까지 던집니다.
        } finally {
            log.info("인증 체크 필터 종료: {}", requestURI);
        }
    }
}