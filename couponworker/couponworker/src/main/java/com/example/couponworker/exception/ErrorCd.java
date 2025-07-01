package com.example.couponworker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCd {

    EXISTS_COUPON(HttpStatus.BAD_REQUEST,"400200" ,"이미 발급 받은 쿠폰입니다." ),
    SOLD_OUT_COUPON(HttpStatus.BAD_REQUEST, "400201" ,"쿠폰이 모두 소진 되었습니다."),
    ISSUED_COUPON_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "500" ,"쿠폰 발급 처리중 문제 발생" );


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
