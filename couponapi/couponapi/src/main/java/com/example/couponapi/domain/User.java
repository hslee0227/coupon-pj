package com.example.couponapi.domain;

import com.example.couponapi.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column
    private LocalDate birthday;

    @Column(length = 100)
    private String email;

    public User(String hyunseok, LocalDate birth, String email) {
        this.name = hyunseok;
        this.birthday = birth;
        this.email = email;
    }
}
