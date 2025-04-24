package com.example.minzok.member.entity;

import com.example.minzok.addresss.entity.Address;
import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.global.error.CustomRuntimeException;
import com.example.minzok.global.error.ExceptionCode;
import com.example.minzok.member.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "members")
@SQLDelete(sql = "UPDATE member SET withdrawn = true WHERE id = ?")
@Where(clause = "withdrawn = false")
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
            throw new CustomRuntimeException(ExceptionCode.LOGIN_FAILED);
        }
    }

    public void updateMember (
            String newPassword,
            String nickname,
            LocalDate birth
    ){
        if(newPassword != null){
            this.password = newPassword;
        }
        if (nickname != null && !nickname.trim().isEmpty()) {
            this.nickname = nickname;
        }
        if (birth != null) {
            this.birth = birth;
        }
    }



}
