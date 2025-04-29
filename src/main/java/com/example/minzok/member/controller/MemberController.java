package com.example.minzok.member.controller;

import com.example.minzok.auth.entity.MyUserDetail;
import com.example.minzok.member.dto.request.MemberDeleteRequestDto;
import com.example.minzok.member.dto.request.MemberUpdateRequestDto;
import com.example.minzok.member.dto.response.MyMemberResponseDto;
import com.example.minzok.member.dto.response.OtherMemberResponseDto;
import com.example.minzok.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            @AuthenticationPrincipal MyUserDetail myUserDetail,
            @Valid @RequestBody MemberUpdateRequestDto dto
            ) {

        return new ResponseEntity<>(memberService.updateMember(userId, myUserDetail, dto),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String,String>> deleteMember(
            @AuthenticationPrincipal MyUserDetail myUserDetail ,
            @Valid @RequestBody MemberDeleteRequestDto dto
            ){
        memberService.deleteMember(myUserDetail, dto);
        return new ResponseEntity<>(Map.of("message","회원탈퇴 되었습니다."),HttpStatus.OK);
    }

}
