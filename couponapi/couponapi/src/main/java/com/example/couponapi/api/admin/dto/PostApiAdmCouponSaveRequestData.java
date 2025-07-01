package com.example.couponapi.api.admin.dto;

import com.example.couponapi.domain.CouponPolicy;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostApiAdmCouponSaveRequestData {
    private String code;
    private CouponPolicy.CouponType type;
    private Integer amount;
    private CouponPolicy.DiscountType discountType;
    private Integer totalQuantity;
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    private LocalDateTime startAt;
    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm")
    private LocalDateTime expiredAt;
    private Integer minOrderAmount;
    private Integer maxDiscountAmount;
}
