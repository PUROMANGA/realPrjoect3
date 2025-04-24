package com.example.minzok.member.repository;

import com.example.minzok.member.entity.Member;
import jakarta.validation.groups.Default;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByEmail(String email);
    Boolean existsMemberByEmail(String email);

    default Member findMemberByEmailOrElseThrow(String email){
        Member member = findMemberByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 없습니다."));
        if(member.isWithdrawn()){
            throw new RuntimeException();
        }
        return member;
    }

}
