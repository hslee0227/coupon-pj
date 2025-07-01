package com.example.couponapi.domain.repo;

import com.example.couponapi.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
