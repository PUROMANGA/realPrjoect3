package com.example.minzok.member.controller;

import com.example.minzok.global.jwt.MyUserDetail;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;
import com.example.minzok.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MyMemberResponseDto> findMeByLoginUser(@AuthenticationPrincipal MyUserDetail myUserDetail){
        return new ResponseEntity<>(memberService.findMe(myUserDetail),HttpStatus.OK);
    }

    @Secured("Role_MANAGER")
    @GetMapping("/{userId}")
    public ResponseEntity<OtherMemberResponseDto> findOtherById(
            @PathVariable Long userId,
            @AuthenticationPrincipal MyUserDetail myUserDetail
    ){
        return new ResponseEntity<>(memberService.findOther(userId, myUserDetail),HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<MyMemberResponseDto> updateMember(
            @PathVariable Long userId,
            @AuthenticationPrincipal MyUserDetail myUserDetail
    ) {

        return null;
    }



}
