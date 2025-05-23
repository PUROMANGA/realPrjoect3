package com.example.minzok.global.common;

import com.example.minzok.auth.service.BlackListTokenService;
import com.example.minzok.auth.service.MyUserDetailService;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.global.error.authEntryPoint.CustomAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.minzok.global.jwt.JwtUtil;
import com.example.minzok.auth.entity.MyUserDetail;

import java.io.IOException;
import java.util.List;

@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MyUserDetailService myUserDetailService;
    private final BlackListTokenService blackListTokenService;

    private static final List<String> WHITE_LIST = List.of("/login", "/signup");

    public SecurityFilter(JwtUtil jwtUtil, MyUserDetailService myUserDetailService, BlackListTokenService blackListTokenService) {
        this.jwtUtil = jwtUtil;
        this.myUserDetailService = myUserDetailService;
        this.blackListTokenService = blackListTokenService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (WHITE_LIST.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String authToken = request.getHeader("Authorization");


            String token = jwtUtil.substringToken(authToken);

            if (!jwtUtil.validateToken(token)) {
                throw new CustomAuthenticationException(ExceptionCode.TOKEN_INVALID);
            }

            if (blackListTokenService.isTokenBlacklisted(authToken)) {
                throw new CustomAuthenticationException(ExceptionCode.TOKEN_BLACKLISTED);
            }

            Claims claims = jwtUtil.extractClaims(token);

            String email = claims.getSubject();
            MyUserDetail myUserDetail1 = myUserDetailService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(myUserDetail1, null, myUserDetail1.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            throw new CustomAuthenticationException(ExceptionCode.TOKEN_EXPIRED);
        } catch (CustomAuthenticationException e) {
            throw e;
        } catch (AuthenticationException | AccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomAuthenticationException(ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }
}
