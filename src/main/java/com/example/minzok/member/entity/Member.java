package com.example.minzok.member.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;


@Entity
@Getter
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

    @Column(nullable = false)
    private String address;

    protected Member(){
    }


    private Member(
            String email,
            String password,
            UserRole userRole,
            String name,
            String nickname,
            LocalDate birth,
            String address
    ) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
        this.nickname = nickname;
        this.birth = birth;
        this.address = address;
    }

    public static Member of (
            String email,
            String password,
            UserRole userRole,
            String name,
            String nickname,
            LocalDate birth,
            String address
    ) {
        return new Member(email, password, userRole, name, nickname, birth, address);
    }



}
