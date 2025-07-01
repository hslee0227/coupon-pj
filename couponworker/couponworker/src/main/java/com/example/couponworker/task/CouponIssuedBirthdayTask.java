package com.example.couponworker.task;

import com.example.couponworker.domain.Coupon;
import com.example.couponworker.domain.CouponHistory;
import com.example.couponworker.domain.CouponPolicy;
import com.example.couponworker.domain.User;
import com.example.couponworker.repo.CouponHistoryRepository;
import com.example.couponworker.repo.CouponPolicyRepository;
import com.example.couponworker.repo.CouponRepository;
import com.example.couponworker.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableScheduling
@RequiredArgsConstructor
public class CouponIssuedBirthdayTask  {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponHistoryRepository couponHistoryRepository;
    private final JobLauncher jobLauncher;

    // ItemReader: 현재 월에 생일이 있는 사용자 조회
    @Bean
    @StepScope
    public ListItemReader<User> reader () {
        int month = LocalDate.now().getMonth().getValue();
        List<User> userList = userRepository.findAllByBirthdayMonth(month);
        return new ListItemReader<>(userList);
    }

    // ItemProcessor: 쿠폰 생성 로직
    @Bean
    public ItemProcessor<User, Coupon> processor() {
       return user -> {

           String birthdayCp001 = "BIRTHDAYCP001";
           CouponPolicy couponPolicy = couponPolicyRepository.findByCode(birthdayCp001).orElseThrow(() -> new RuntimeException("no coupon"));

           String nowFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

           String couponCode = String.format("%s%s%s",user.getId(),birthdayCp001,nowFormat);
           Coupon existingCoupon = couponRepository.findByUserIdAndCouponPolicyId(user.getId(), couponPolicy.getId());

           if (existingCoupon == null) {
               return new Coupon(
                       couponCode,
                       couponPolicy,
                       user.getId(),
                       Coupon.CouponStatus.ISSUED,
                       LocalDateTime.now(),
                       LocalDateTime.now().plusMonths(1)
               );
           }
           return null;
       };
    }

    // ItemWriter: 쿠폰과 쿠폰 이력 저장
    @Bean
    public ItemWriter<Coupon> writer() {
        return coupons -> {
            for (Coupon coupon : coupons) {
                if (coupon != null) {
                    Coupon savedCoupon = couponRepository.save(coupon);
                    couponHistoryRepository.save(
                            new CouponHistory(
                                    savedCoupon,
                                    CouponHistory.CouponStatus.ISSUED,
                                    LocalDateTime.now(),
                                    "생일 쿠폰 발급"
                            )
                    );
                }
            }
        };
    }

    @Bean
    public Step birthdayCouponStep() {
        return new StepBuilder("birthdayCouponStep", jobRepository)
                .<User, Coupon> chunk(10, platformTransactionManager) //10개씩
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .retryLimit(3)
                .retry(RuntimeException.class)
                .build();
    }

    @Bean
    public Job birthdayCouponJob() {
        return new JobBuilder("birthdayCouponJob", jobRepository)
                .start(birthdayCouponStep())
                .build();
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void runBirthDayCouponJob() throws Exception {
        String currentTimeKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("run.dateTime", currentTimeKey, true)
                .toJobParameters();

        jobLauncher.run(birthdayCouponJob(), jobParameters);

    }
}
