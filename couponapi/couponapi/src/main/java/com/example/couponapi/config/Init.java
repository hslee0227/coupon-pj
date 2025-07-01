package com.example.couponapi.config;

import com.example.couponapi.domain.repo.CouponPolicyRepository;
import com.example.couponapi.api.admin.dto.SaveCouponPolicyServiceParam;
import com.example.couponapi.domain.repo.MobileRepository;
import com.example.couponapi.domain.CouponPolicy;
import com.example.couponapi.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class Init {

    private final CouponPolicyRepository couponPolicyRepository;
    private final MobileRepository mobileRepository;
//    @PostConstruct
    void initData () {
//        생일 쿠폰
        couponPolicyRepository.save(new CouponPolicy(
                SaveCouponPolicyServiceParam.builder()
                        .type(CouponPolicy.CouponType.BIRTHDAY)
                        .discountType(CouponPolicy.DiscountType.PERCENT)
                        .code("BIRTHDAYCP001")
                        .amount(40)
                        .minOrderAmount(10000)
                        .maxDiscountAmount(1000000)
                        .build()
        ));

        couponPolicyRepository.save(new CouponPolicy(
                SaveCouponPolicyServiceParam.builder()
                        .type(CouponPolicy.CouponType.PUBLIC)
                        .discountType(CouponPolicy.DiscountType.PERCENT)
                        .code("FCFSCP100")
                        .amount(25)
                        .startAt(LocalDateTime.now())
                        .expiredAt(LocalDateTime.now().plusMonths(1))
                        .totalQuantity(100)
                        .minOrderAmount(30000)
                        .maxDiscountAmount(1000000)
                        .build()
        ));
        for (int i = 1; i <= 101; i++) {
            mobileRepository.save(new User("hyunseok"+ i, LocalDate.of(2025, 2,27).plusDays(i), "ggmcjy"+i));
        }
    }

}
