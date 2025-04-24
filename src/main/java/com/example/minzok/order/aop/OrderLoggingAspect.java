package com.example.minzok.order.aop;

import com.example.minzok.global.jwt.JwtUtil;
import com.example.minzok.order.dto.response.OrderResponseDto;
import com.example.minzok.order.dto.response.OrderStatusResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OrderLoggingAspect {
    private final ObjectMapper objectMapper;
    private JwtUtil jwtUtil;

    @Around("@annotation(com.example.minzok.order.aop.OrderLogging)")
    public Object logOrder(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        // JWT에서 사용자 정보 추출
        String userId = "anonymous";
        if (token != null && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.extractClaims(token);
            userId = claims.getSubject(); // 사용자 이메일 또는 ID
        }

        // 2. 로그에 사용자 ID 포함
        log.info("Request User ID = {}", userId);
        log.info("Request Time = {}", LocalDateTime.now());
        log.info("Request URL = {}", request.getRequestURL());

        // 메서드 실행
        Object result = joinPoint.proceed();

        // 응답에서 주문/가게 ID 추출
        if (result instanceof OrderResponseDto) {
            OrderResponseDto response = (OrderResponseDto) result;
            log.info("[주문 생성] 주문 ID: {}, 가게 ID: {}", response.getOrderId(), response.getStoreId());
        } else if (result instanceof OrderStatusResponseDto) {
            OrderStatusResponseDto response = (OrderStatusResponseDto) result;
            log.info("[주문 상태 변경] 주문 ID: {}, 상태: {}", response.getOrderId(), response.getOrderStatus());
        }

        return result;
    }
}
