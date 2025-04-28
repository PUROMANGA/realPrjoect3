package com.example.minzok.global.common;

import com.example.minzok.auth.entity.BlackListToken;
import com.example.minzok.auth.service.BlackListTokenService;
import com.example.minzok.auth.service.MyUserDetailService;
import com.example.minzok.global.error.authEntryPoint.CustomAccessDeniedHandler;
import com.example.minzok.global.error.authEntryPoint.CustomAuthenticationEntryPoint;
import com.example.minzok.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtUtil jwtUtil;
    private final MyUserDetailService myUserDetailService;
    private final BlackListTokenService blackListTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(this::configureSession)
                .authorizeHttpRequests(this::configureAuthorization)
                .exceptionHandling(this::configureExceptionHandling)
                .logout(logout->logout.disable())
                .addFilterBefore(new SecurityFilter(
                        jwtUtil,
                        myUserDetailService,
                        blackListTokenService
                ), AuthorizationFilter.class)
                .build();
    }

    private void configureSession(SessionManagementConfigurer<HttpSecurity> session) {
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers("/login", "/signup","logout", "/error").permitAll()
                .requestMatchers("/profile","/stores/**").hasRole("MANAGER")
                .anyRequest().authenticated();
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
