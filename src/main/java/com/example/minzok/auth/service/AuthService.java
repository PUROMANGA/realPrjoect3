package com.example.minzok.auth.service;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.auth.dto.request.LoginRequestDto;
import com.example.minzok.auth.dto.request.SignUpRequestDto;
import com.example.minzok.auth.dto.response.TokenResponseDto;
import com.example.minzok.global.auth.JwtUtil;
import com.example.minzok.global.common.SecurityConfig;
import com.example.minzok.member.entity.Member;
import com.example.minzok.member.enums.UserRole;
import com.example.minzok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    /**
     *
     * @param dto
     * @return TokenResponseDto
     */
    @Transactional
    public TokenResponseDto signup(SignUpRequestDto dto){

        if(memberRepository.existsMemberByEmail(dto.getEmail())){
            throw new RuntimeException();
        }

        if(!dto.getPassword().equals(dto.getPasswordCheck())){
            throw new RuntimeException();
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Member member = Member.of(
                dto.getEmail(),
                encodedPassword,
                UserRole.USER,
                dto.getName(),
                dto.getNickname(),
                dto.getBirth()
        );

        Address.of(dto.getLotNumberAddress(), dto.getDetailAddress(), AddressType.DEFAULT, member);

        String bearerToken = jwtUtil.createToken(member.getEmail());

        return new TokenResponseDto(bearerToken);
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto dto){

        Member member = memberRepository.findMemberByEmailOrElseThrow(dto.getEmail());

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new RuntimeException();
        }

        String bearerToken = jwtUtil.createToken(member.getEmail());

        return new TokenResponseDto(bearerToken);
    }


}


