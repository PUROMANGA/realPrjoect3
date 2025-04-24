package com.example.minzok.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor

public enum ExceptionCode implements ErrorCode{

    VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "VALID_EXCEPTION가 발생했습니다."),
    CANT_FIND_MEMBER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    CANT_FIND_STORE(HttpStatus.NOT_FOUND, "해당 가게를 찾을 수 없습니다."),
    CANT_FIND_MENU(HttpStatus.NOT_FOUND, "해당 메뉴를 찾을 수 없습니다."),
    NO_EDIT_PERMISSION(HttpStatus.FORBIDDEN, "수정 권한이 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    CANT_FIND_TOKEN(HttpStatus.UNAUTHORIZED, "토큰을 찾지 못했습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_BLACKLISTED(HttpStatus.UNAUTHORIZED, "로그아웃된 토큰입니다."),
    MINIMUM_ORDER_AMOUNT(HttpStatus.NOT_FOUND, "최소금액을 만족하지 못했습니다."),
    NOT_STORE_TIME(HttpStatus.NOT_FOUND, "가게 영업시간이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus gethttpStatus() {
        return httpStatus;
    }
}
