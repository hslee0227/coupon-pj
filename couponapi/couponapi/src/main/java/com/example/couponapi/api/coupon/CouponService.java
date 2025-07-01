package com.example.couponapi.api.coupon;

import com.example.couponapi.domain.repo.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(readOnly = true)
    public boolean existByUserIdAndCouponPolicyId(Long userId, Long policyId) {
        return couponRepository.existsByUserIdAndCouponPolicyId(userId,policyId);
    }
}
