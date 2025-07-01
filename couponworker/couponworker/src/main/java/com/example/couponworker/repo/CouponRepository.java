package com.example.couponworker.repo;

import com.example.couponworker.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {


    boolean existsByUserIdAndCouponPolicyId(Long userId, Long couponPolicyId);

    Coupon findByUserIdAndCouponPolicyId(Long userId, Long couponPolicyId);
}
