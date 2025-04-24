package com.example.minzok.member.entity;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "members")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    /**
     * 사장님이 가게 몇 개씩 들고 있는지 체크하는 변수
     */
    @Column(nullable = false)
    private int storeCount;

    protected Member(){
    }

    private Member(
            String email,
            String password,
            UserRole userRole,
            String name,
            String nickname,
            LocalDate birth
    ) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
    }

    public static Member of (
            String email,
            String password,
            UserRole userRole,
            String name,
            String nickname,
            LocalDate birth
    ) {
        return new Member(email, password, userRole, name, nickname, birth);
    }

    public void validatePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword, this.password)) {
            throw new RuntimeException();
        }
    }

    /**
     * 테스트용 멤버 이메일
     */

    public Member(String email) {
        this.email = email;
    }



}
