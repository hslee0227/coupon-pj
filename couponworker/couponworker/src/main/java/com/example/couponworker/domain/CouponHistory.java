package com.example.couponworker.domain;


import com.example.couponworker.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Entity
@Table(name = "coupon_history")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CouponStatus status;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(columnDefinition = "TEXT")
    private String memo;

    public CouponHistory(Coupon coupon, CouponStatus status, LocalDateTime changedAt, String memo) {
        this.coupon = coupon;
        this.status = status;
        this.changedAt = changedAt;
        this.memo = memo;

    }

    public enum CouponStatus {
        ISSUED, USED, EXPIRED
    }
}
