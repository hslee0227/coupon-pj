package com.example.couponworker.domain;



import com.example.couponworker.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_policy")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPolicy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("쿠폰 정책 고유 ID")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @Comment("쿠폰 정책 코드 (예: WELCOME100, SPRING_SALE)")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Comment("쿠폰 종류 (PUBLIC: 모두 대상, PRIVATE: 특정 대상, BIRTHDAY: 생일 쿠폰 등)")
    private CouponType type;

    @Column(nullable = false)
    @Comment("할인 금액 또는 할인율 값")
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @Comment("할인 타입 (FIXED: 고정 금액 할인, PERCENT: 비율 할인)")
    private DiscountType discountType;

    @Column(name = "total_quantity")
    @Comment("총 발급 가능한 쿠폰 수량 (null이면 무제한)")
    private Integer totalQuantity;

    @Column(name = "issued_quantity")
    @Comment("현재까지 발급된 쿠폰 수량")
    private Integer issuedQuantity = 0;

    @Column(name = "start_at")
    @Comment("쿠폰 사용 가능 시작 시각")
    private LocalDateTime startAt;

    @Column(name = "expired_at")
    @Comment("쿠폰 만료 시각")
    private LocalDateTime expiredAt;

    @Column(name = "min_order_amount")
    @Comment("쿠폰 적용을 위한 최소 주문 금액 조건")
    private Integer minOrderAmount;

    @Column(name = "max_discount_amount")
    @Comment("최대 할인 금액 (퍼센트 할인 시 적용 최대 한도)")
    private Integer maxDiscountAmount;

    public void increaseIssuedQuantity(int cnt) {
        this.issuedQuantity = cnt;
    }

    // Enum 클래스 예시
    public enum CouponType {
        PUBLIC, PRIVATE, BIRTHDAY
    }

    public enum DiscountType {
        PERCENT, FIXED
    }
}
