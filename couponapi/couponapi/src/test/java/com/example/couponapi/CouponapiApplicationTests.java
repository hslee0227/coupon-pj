package com.example.couponapi;

import com.example.couponapi.api.mobile.MobileService;
import com.example.couponapi.api.mobile.dto.PostApiAppIssuedCouponRequestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CouponapiApplicationTests {


	@Autowired
	private MobileService mobileService;

	private final int THREAD_COUNT = 101;

	@Test
	void contextLoads() throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch doneLatch = new CountDownLatch(THREAD_COUNT);

		Long couponPolicyId = 2L;

		for (int i = 0; i < THREAD_COUNT; i++) {
			final Long userId = (long) (i + 1);

			executorService.submit(() -> {
				try {
					startLatch.await(); // 출발 대기
					mobileService.issuedCoupon(PostApiAppIssuedCouponRequestData.builder().requestTime("20250627101011").userId(userId).couponPolicyId(couponPolicyId).build());
				} catch (Exception e) {
					System.out.println("❌ 유저 " + userId + " 실패: " + e.getMessage());
				} finally {
					doneLatch.countDown();
				}
			});
		}

		// 모든 요청 동시에 시작
		startLatch.countDown();
		doneLatch.await();
		executorService.shutdown();

//		// 결과 확인
//		long issuedCount = issuedCouponRepository.count();
//		System.out.println("✅ 실제 발급된 쿠폰 수: " + issuedCount);
//
//		assertEquals(100, issuedCount, "100개까지만 발급돼야 함");
	}

}
