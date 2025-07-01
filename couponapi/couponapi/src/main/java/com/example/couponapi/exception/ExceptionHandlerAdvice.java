package com.example.couponapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException e){
        log.error("[handleCustomException] -> {}", e);
        return ErrorResponseDTO.toResponseEntity(e.getErrorCd());
    }


}
