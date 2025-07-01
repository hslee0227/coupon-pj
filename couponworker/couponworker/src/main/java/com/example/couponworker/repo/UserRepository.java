package com.example.couponworker.repo;

import com.example.couponworker.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT c FROM User c WHERE FUNCTION('MONTH', c.birthday) = :month")
    List<User> findAllByBirthdayMonth(@Param("month") int month);
}
