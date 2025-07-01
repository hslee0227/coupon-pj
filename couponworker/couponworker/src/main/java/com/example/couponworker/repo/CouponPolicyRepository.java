package com.example.couponworker.repo;

import com.example.couponworker.domain.CouponPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy , Long> {
    Optional<CouponPolicy> findByCode(String code);
}
