package com.example.minzok.auth.service;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.addresss.enums.AddressType;
import com.example.minzok.addresss.repository.AddressRepository;
import com.example.minzok.auth.dto.request.LoginRequestDto;
import com.example.minzok.auth.dto.request.SignUpRequestDto;
import com.example.minzok.auth.dto.response.TokenResponseDto;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ErrorCode;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.global.jwt.JwtUtil;
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
    private final BlackListTokenService blackListTokenService;
    private final JwtUtil jwtUtil;
    private final AddressRepository addressRepository;


    /**
     * 입력받은 정보로 회원가입합니다
     * 이미 존재하는 이메일일때, 패스워드와 패스워드확인이 서로 다를 때 예외가 발생합니다.
     * @param dto
     * @return TokenResponseDto
     */
    @Transactional
    public TokenResponseDto signup(SignUpRequestDto dto){

        if(memberRepository.existsMemberByEmail(dto.getEmail())){
            throw new CustomRuntimeException(ExceptionCode.DUPLICATE_EMAIL);
        }

        if(!dto.getPassword().equals(dto.getPasswordCheck())){
            throw new CustomRuntimeException(ExceptionCode.PASSWORD_MISMATCH);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Member member = Member.of(
                dto.getEmail(),
                encodedPassword,
                UserRole.valueOf(dto.getUserRole()),
                dto.getName(),
                dto.getNickname(),
                dto.getBirth()
        );

        Address address = Address.of(dto.getLotNumberAddress(), dto.getDetailAddress(), AddressType.DEFAULT, member);

        memberRepository.save(member);
        addressRepository.save(address);

        String bearerToken = jwtUtil.createToken(member.getEmail());

        return new TokenResponseDto(bearerToken);
    }

    /**
     * 입력받은 정보로 로그인 합니다.
     * 입력받은 비밀번호가 저장된 비밀번호와 다를때 예외를 발생시킵니다.
     * @param dto
     * @return
     */
    @Transactional
    public TokenResponseDto login(LoginRequestDto dto){

        Member member = memberRepository.findMemberByEmail(dto.getEmail())
                .orElseThrow(()-> new CustomRuntimeException(ExceptionCode.LOGIN_FAILED));

        member.validatePassword(dto.getPassword(), passwordEncoder);

        String bearerToken = jwtUtil.createToken(member.getEmail());

        return new TokenResponseDto(bearerToken);
    }

    /**
     * 토큰을 제공받고 그 토큰을 블랙리스트에 등로하면서 로그아웃 합니다.
     * @param token
     */
    @Transactional
    public void logout(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = jwtUtil.substringToken(token);
            blackListTokenService.addToBlacklist(token);
        }
    }

}


