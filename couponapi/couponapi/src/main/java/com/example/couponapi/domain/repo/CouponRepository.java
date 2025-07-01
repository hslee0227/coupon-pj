package com.example.couponapi.domain.repo;

import com.example.couponapi.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon , Long> {
    boolean existsByUserIdAndCouponPolicyId(Long userId, Long policyId);
}
