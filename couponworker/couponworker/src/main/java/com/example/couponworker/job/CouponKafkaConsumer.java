package com.example.couponworker.job;

import com.example.couponworker.domain.Coupon;
import com.example.couponworker.domain.CouponHistory;
import com.example.couponworker.domain.CouponPolicy;
import com.example.couponworker.dto.FcfsCouponDTO;
import com.example.couponworker.exception.CustomException;
import com.example.couponworker.exception.ErrorCd;
import com.example.couponworker.repo.CouponHistoryRepository;
import com.example.couponworker.repo.CouponPolicyRepository;
import com.example.couponworker.repo.CouponRepository;
import com.example.couponworker.utils.ParseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final RedissonClient redissonClient;
    private final CouponRepository couponRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final CouponHistoryRepository couponHistoryRepository;


    @KafkaListener(topics = "coupon.issue.fcfs.created", groupId = "coupon-consumer-group")
    @Transactional
    public void fcfsCouponConsume(String message, Acknowledgment ack) {
        if (ObjectUtils.isNotEmpty(message)) {
            FcfsCouponDTO fcfsCouponDTO = ParseUtil.readData(objectMapper, message, FcfsCouponDTO.class);
            assert fcfsCouponDTO != null;
            String lockKey = "lock:coupon:" + fcfsCouponDTO.getCouponPolicyId();
            RLock lock = redissonClient.getLock(lockKey);
            boolean locked = false;
            try {
                locked = lock.tryLock(3,2, TimeUnit.SECONDS);
                if (!locked) {
                    ack.acknowledge();
                    throw new RuntimeException("락 획득 실패");
                }

                boolean alreadyIssued = couponRepository.existsByUserIdAndCouponPolicyId(fcfsCouponDTO.getUserId(), fcfsCouponDTO.getCouponPolicyId());
                if (alreadyIssued) {
                    ack.acknowledge();
                    throw new CustomException(ErrorCd.EXISTS_COUPON);
                }

                CouponPolicy couponPolicy = couponPolicyRepository.findById(fcfsCouponDTO.getCouponPolicyId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰 정책"));
                if (couponPolicy.getIssuedQuantity() >= couponPolicy.getTotalQuantity()) {
                    redisTemplate.opsForValue().set("fcfs:coupon" + fcfsCouponDTO.getCouponPolicyId() + ":done", "true");
                    ack.acknowledge();
                    throw new CustomException(ErrorCd.SOLD_OUT_COUPON);
                }


                couponPolicy.increaseIssuedQuantity(couponPolicy.getIssuedQuantity()+1);
                String nowFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String couponCode = String.format("%s%s%s",fcfsCouponDTO.getUserId(),couponPolicy.getCode(),nowFormat);
                Coupon coupon = new Coupon(couponCode,couponPolicy,fcfsCouponDTO.getUserId(), Coupon.CouponStatus.ISSUED,LocalDateTime.now(), LocalDateTime.now().plusMonths(1));
                Coupon savedCoupon = couponRepository.save(coupon);
                couponHistoryRepository.save(new CouponHistory(savedCoupon, CouponHistory.CouponStatus.ISSUED,LocalDateTime.now(),"선착순 쿠폰 발급"));
                ack.acknowledge();

            } catch (Exception e) {
                log.error("[fcfsCouponConsume.Exception] : {}",e.toString());
                ack.acknowledge();
                throw new CustomException(ErrorCd.ISSUED_COUPON_EXCEPTION);
            } finally {
                if (locked) {
                    lock.unlock();
                }
            }
        }


    }
}
