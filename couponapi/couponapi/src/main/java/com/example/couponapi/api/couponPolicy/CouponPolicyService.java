package com.example.couponapi.api.couponPolicy;

import com.example.couponapi.api.admin.dto.SaveCouponPolicyServiceParam;
import com.example.couponapi.domain.CouponPolicy;
import com.example.couponapi.domain.repo.CouponPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;


    @Transactional
    public CouponPolicy saveCouponPolicy(SaveCouponPolicyServiceParam serviceParam) {
        return couponPolicyRepository.save(new CouponPolicy(serviceParam));
    }
}
