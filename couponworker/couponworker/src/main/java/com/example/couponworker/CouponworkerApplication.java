package com.example.couponworker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CouponworkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponworkerApplication.class, args);
	}

}
