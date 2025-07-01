package com.example.couponworker.domain;


import com.example.couponworker.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_code", nullable = false, unique = true, length = 50)
    private String couponCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private CouponPolicy couponPolicy;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CouponStatus status;  // ISSUED, USED, EXPIRED

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(name = "order_id")
    private Long orderId;

    public Coupon(String couponCode,
                  CouponPolicy couponPolicy,
                  Long userId, CouponStatus couponStatus, LocalDateTime issuedAt,  LocalDateTime expiredAt) {
        this.couponCode = couponCode;
        this.couponPolicy = couponPolicy;
        this.userId = userId;
        this.status = couponStatus;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }


    public enum CouponStatus {
        ISSUED, USED, EXPIRED
    }


}
