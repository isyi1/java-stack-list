package com.example.demo.config;

import com.example.demo.filter.LoginCheckFilter;
import com.example.demo.filter.MyLogFilter;
import com.example.demo.interceptor.MyLogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // LOG 필터
    @Bean
    public FilterRegistrationBean<MyLogFilter> logFilter() {
        FilterRegistrationBean<MyLogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new MyLogFilter()); // 내가 만든 필터 등록
        filterRegistrationBean.setOrder(1);                 // 필터 체인 순서 (낮을수록 먼저 실행)
        filterRegistrationBean.addUrlPatterns("/*");        // 모든 URL에 적용

        return filterRegistrationBean;
    }

    // 2번 필터: 인증 체크
    @Bean
    public FilterRegistrationBean<LoginCheckFilter> loginFilter() {
        FilterRegistrationBean<LoginCheckFilter> bean = new FilterRegistrationBean<>(new LoginCheckFilter());
        bean.setOrder(2); // 로그 필터 다음으로 실행됩니다.
        bean.addUrlPatterns("/*");
        return bean;
    }

    // CORS 설정
    // CORS 필터 - > WebMvcConfigurer 상속받아 오버라이딩 하는 Spring 기본 제공 함수 사용으로 대체
    /**
    @Bean
    public FilterRegistrationBean<MyCorsFilter> corsFilter() {
        FilterRegistrationBean<MyCorsFilter> bean = new FilterRegistrationBean<>(new MyCorsFilter());
        bean.setOrder(0); // 가장 먼저 실행되도록 설정
        bean.addUrlPatterns("/*");
        return bean;
    }
    */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 모든 경로에 대해
                .allowedOrigins("*")   // 모든 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);         // 1시간 동안 캐싱
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyLogInterceptor())
                .order(1)                     // 실행 순서
                .addPathPatterns("/**")       // 모든 경로 적용
                .excludePathPatterns("/css/**", "/js/**", "/error"); // 제외할 경로

        // 인터셉터가 여러개인 경우 추가로 기입 .. order(num+1). ...
    }
}