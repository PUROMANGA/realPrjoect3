package com.example.minzok.auth.service;

import com.example.minzok.global.auth.JwtUtil;
import com.example.minzok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

}
