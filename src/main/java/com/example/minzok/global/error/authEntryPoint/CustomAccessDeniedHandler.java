package com.example.minzok.global.error.authEntryPoint;

import com.example.minzok.global.error.CustomErrorResponse;
import com.example.minzok.global.error.ErrorCode;
import com.example.minzok.global.error.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");

        ErrorCode errorCode = ExceptionCode.ACCESS_DENIED;

        log.error("[AuthenticationException 발생] cause: {}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(accessDeniedException),
                errorCode.getMessage());

        response.setStatus(errorCode.gethttpStatus().value());

        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

    }
}
