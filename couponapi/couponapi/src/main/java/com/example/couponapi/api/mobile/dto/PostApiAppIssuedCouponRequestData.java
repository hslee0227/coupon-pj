package com.example.couponapi.api.mobile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class PostApiAppIssuedCouponRequestData {


    @NotNull
    private Long userId;


    @NotNull
    private Long couponPolicyId;

    @NotBlank
    private String requestTime;

}
