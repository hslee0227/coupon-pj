package com.example.couponapi.domain.repo;

import com.example.couponapi.domain.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
}
