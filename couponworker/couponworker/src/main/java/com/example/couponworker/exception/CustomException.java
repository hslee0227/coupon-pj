package com.example.couponworker.exception;

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

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause, ErrorCd errorCd) {
        super(message, cause);
        this.errorCd = errorCd;
    }
}
