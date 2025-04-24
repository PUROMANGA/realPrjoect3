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
    NOT_FIND_KEYWORD(HttpStatus.FORBIDDEN, "검색 결과가 없습니다."),
    NO_EDIT_PERMISSION(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus gethttpStatus() {
        return httpStatus;
    }
}
