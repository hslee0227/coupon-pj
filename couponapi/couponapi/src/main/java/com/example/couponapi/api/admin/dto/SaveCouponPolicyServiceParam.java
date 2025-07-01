package com.example.couponapi.api.admin.dto;

import com.example.couponapi.domain.CouponPolicy;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SaveCouponPolicyServiceParam {
    private String code;
    private CouponPolicy.CouponType type;
    private Integer amount;
    private CouponPolicy.DiscountType discountType;
    private Integer totalQuantity;
    private LocalDateTime startAt;
    private LocalDateTime expiredAt;
    private Integer minOrderAmount;
    private Integer maxDiscountAmount;
}
