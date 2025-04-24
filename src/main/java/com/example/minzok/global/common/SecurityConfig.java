package com.example.minzok.global.common;

import com.example.minzok.auth.entity.BlackListToken;
import com.example.minzok.auth.service.BlackListTokenService;
import com.example.minzok.auth.service.MyUserDetailService;
import com.example.minzok.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MyUserDetailService myUserDetailService;
    private final BlackListTokenService blackListTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(this::configureSession)
                .authorizeHttpRequests(this::configureAuthorization)
                .addFilterBefore(new SecurityFilter(
                        jwtUtil,
                        myUserDetailService,
                        blackListTokenService
                ), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private void configureSession(SessionManagementConfigurer<HttpSecurity> session) {
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private void configureAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth
                .requestMatchers("/login", "/signup").permitAll()
                .requestMatchers("/profile", "/store").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

//    private void configureLogout(LogoutConfigurer<HttpSecurity> logout) {
//        logout.logoutUrl("/logout")
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    response.setStatus(200);
//                    response.sendRedirect("/login");
//                })
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .clearAuthentication(true);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
