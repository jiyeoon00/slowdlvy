package com.slow.slowdelivery.commons.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    /* 400 Bad Request */

    /* 404 Not Found */
    SHOP_NOT_FOUND(NOT_FOUND, "가게정보를 찾을 수 없습니다"),
    MENU_NOT_FOUND(NOT_FOUND, "메뉴정보를 찾을 수 없습니다"),

    DOCS_NOT_FOUND(NOT_FOUND, "document가 존재하지 않습니다"),
    MENU_DOCS_NOT_FOUND(NOT_FOUND, "검색한 메뉴가 존재하지 않습니다"),
    SHOP_DOCS_NOT_FOUND(NOT_FOUND, "검색한 가게가 존재하지 않습니다"),

    /* 500 Internal Server Error */

    ;

    private final HttpStatus httpStatus;
    private final String description;

}

