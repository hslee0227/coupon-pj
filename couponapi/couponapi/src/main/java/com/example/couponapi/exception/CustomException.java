package com.example.couponapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException{
    private ErrorCd errorCd;

    public CustomException(ErrorCd errorCd) {
        super(errorCd.getMessage());
        this.errorCd = errorCd;
    }
}
