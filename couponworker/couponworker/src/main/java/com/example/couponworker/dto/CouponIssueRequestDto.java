package com.example.couponworker.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
public class CouponIssueRequestDto {
    private Long userId;
    private Long couponPolicyId;
    private String requestTime;
}
