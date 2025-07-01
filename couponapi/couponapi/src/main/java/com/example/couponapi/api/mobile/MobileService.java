package com.example.couponapi.api.mobile;

import com.example.couponapi.api.coupon.CouponService;
import com.example.couponapi.api.mobile.dto.PostApiAppIssuedCouponRequestData;
import com.example.couponapi.domain.repo.CouponRepository;
import com.example.couponapi.exception.CustomException;
import com.example.couponapi.exception.ErrorCd;
import com.example.couponapi.utils.ParseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class MobileService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private final CouponService couponService;

    public void issuedCoupon(PostApiAppIssuedCouponRequestData requestData) {
        String done = redisTemplate.opsForValue().get("fcfs:coupon" + requestData.getCouponPolicyId() + ":done");

        boolean alreadyIssuedCoupon  = couponService.existByUserIdAndCouponPolicyId(requestData.getUserId(), requestData.getCouponPolicyId());
        if (alreadyIssuedCoupon) {
            throw new CustomException(ErrorCd.EXISTS_COUPON);
        }

        if (ObjectUtils.isNotEmpty(done) && done.equals("true")) {
            log.info("done : {}" ,done);
            throw new CustomException(ErrorCd.FCFS_END_EXCEPTION);
        }

        String json = ParseUtil.parseString(objectMapper,requestData);
        if (ObjectUtils.isNotEmpty(json)) {
            kafkaTemplate.send("coupon.issue.fcfs.created", json);
        }
    }
}
