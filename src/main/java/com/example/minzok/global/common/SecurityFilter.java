package com.example.minzok.global.common;

import com.example.minzok.global.jwt.JwtUtil;
import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.auth.service.MyUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MyUserDetailService myUserDetailService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authToken = request.getHeader("Authorization");

        String token = jwtUtil.substringToken(authToken);
        Claims claims = jwtUtil.extractClaims(token);

        String email = claims.get("email", String.class);
        MyUserDetail myUserDetail1 = myUserDetailService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(myUserDetail1, null, myUserDetail1.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }

}
