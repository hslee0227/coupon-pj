package com.example.couponapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCd {

    FCFS_END_EXCEPTION(HttpStatus.BAD_REQUEST, "400100", "선착순 쿠폰 이벤트 종료"),
    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "400101" , "잘못된 요청 입니다."),
    EXISTS_COUPON(HttpStatus.BAD_REQUEST,"400200" ,"이미 발급 받은 쿠폰입니다." );

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
