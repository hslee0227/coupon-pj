package com.example.couponapi.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseDTO {

    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseDTO> toResponseEntity(ErrorCd e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseDTO.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
