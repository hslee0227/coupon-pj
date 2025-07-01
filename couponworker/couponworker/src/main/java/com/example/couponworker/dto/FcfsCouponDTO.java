package com.example.couponworker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcfsCouponDTO {

    private Long userId;
    private Long couponPolicyId;
    private String requestTime;

}
