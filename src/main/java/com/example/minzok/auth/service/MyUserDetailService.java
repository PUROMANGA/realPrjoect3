package com.example.minzok.auth.service;

import com.example.minzok.member.entity.Member;
import com.example.minzok.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.minzok.global.jwt.MyUserDetail;

@Service
@RequiredArgsConstructor
public class  MyUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 멤버 정보를 유저 디테일에 저장한다.
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public MyUserDetail loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmailOrElseThrow(email);
        return new MyUserDetail(member);
    }

}
