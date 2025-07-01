package com.example.couponworker.repo;

import com.example.couponworker.domain.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory , Long> {
}
