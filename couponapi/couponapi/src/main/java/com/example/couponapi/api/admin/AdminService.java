package com.example.couponapi.api.admin;

import com.example.couponapi.api.couponPolicy.CouponPolicyService;
import com.example.couponapi.api.admin.dto.PostApiAdmCouponSaveRequestData;
import com.example.couponapi.api.admin.dto.SaveCouponPolicyServiceParam;
import com.example.couponapi.domain.CouponPolicy;
import com.example.couponapi.domain.repo.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final CouponPolicyService couponPolicyService;

    @Transactional
    public void saveAdmCouponPolicy(PostApiAdmCouponSaveRequestData requestData) {
        CouponPolicy couponPolicy = couponPolicyService.saveCouponPolicy(SaveCouponPolicyServiceParam.builder()
                .type(requestData.getType())
                .discountType(requestData.getDiscountType())
                .code(requestData.getCode())
                .startAt(requestData.getStartAt())
                .expiredAt(requestData.getExpiredAt())
                .totalQuantity(requestData.getTotalQuantity())
                .amount(requestData.getAmount())
                .minOrderAmount(requestData.getMinOrderAmount())
                .maxDiscountAmount(requestData.getMaxDiscountAmount())
                .build());
    }
}