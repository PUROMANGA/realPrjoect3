package com.example.minzok.global.error.authEntryPoint;

import com.example.minzok.global.error.CustomErrorResponse;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ErrorCode;
import com.example.minzok.global.error.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");

        ErrorCode errorCode;

        if (authException.getCause() instanceof CustomRuntimeException) {
            CustomRuntimeException customException = (CustomRuntimeException) authException.getCause();
            errorCode = customException.getExceptionCode();
        } else {
            errorCode = ExceptionCode.INTERNAL_SERVER_ERROR;
        }

        log.error("[AuthenticationException 발생] cause: {}, message: {}",
                NestedExceptionUtils.getMostSpecificCause(authException),
                errorCode.getMessage());


        response.setStatus(errorCode.gethttpStatus().value());

        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .message(errorCode.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

    }
}

