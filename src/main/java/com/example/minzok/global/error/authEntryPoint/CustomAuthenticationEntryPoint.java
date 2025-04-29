package com.example.minzok.global.error.authEntryPoint;

import com.example.minzok.global.error.CustomErrorResponse;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ErrorCode;
import com.example.minzok.global.error.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint() {
        this.objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));

        this.objectMapper.registerModule(javaTimeModule);
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ErrorCode errorCode;

        System.out.println(authException.getMessage());

        if (authException instanceof CustomAuthenticationException) {
            CustomAuthenticationException e = (CustomAuthenticationException) authException;
            errorCode = e.getExceptionCode();
        } else {
            System.out.println("1");
            errorCode = ExceptionCode.INTERNAL_SERVER_ERROR;
        }

        log.error("[AuthenticationException 발생] cause: {}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(authException),
                errorCode.getMessage());

        response.setStatus(errorCode.getHttpStatus().value());

        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

    }
}

