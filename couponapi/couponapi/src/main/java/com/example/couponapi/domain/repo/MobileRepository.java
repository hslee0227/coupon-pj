package com.example.couponapi.domain.repo;

import com.example.couponapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRepository extends JpaRepository<User, Long> {
}
