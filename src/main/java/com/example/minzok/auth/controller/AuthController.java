package com.example.minzok.auth.controller;

import com.example.minzok.auth.dto.request.LoginRequestDto;
import com.example.minzok.auth.dto.request.SignUpRequestDto;
import com.example.minzok.auth.dto.response.TokenResponseDto;
import com.example.minzok.auth.service.AuthService;
import com.example.minzok.auth.service.RefreshTokenService;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    /**
     * 입력한 정보로 회원가입 한다.
     * @param dto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signup(@Valid @RequestBody SignUpRequestDto dto){
        return new ResponseEntity<>(authService.signup(dto), HttpStatus.CREATED);
    }

    /**
     * 입력한 정보로 로그인 한다.
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }

    /**
     * 로그아웃하고 시큐리티 정보를 초기화 한다.
     * @param token
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);

        SecurityContextHolder.clearContext();

        return new ResponseEntity<>( null , HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> reissue(@RequestHeader("Authorization") String refreshToken) {
        return new ResponseEntity<>(refreshTokenService.refresh(refreshToken), HttpStatus.OK);
    }

}
