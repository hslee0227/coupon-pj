package com.example.couponapi.api.mobile;


import com.example.couponapi.api.mobile.dto.PostApiAppIssuedCouponRequestData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/app")
public class MobileController {
    private final MobileService mobileService;

    @PostMapping("/issued/coupon")
    public ResponseEntity<?> postApiAppIssuedCoupon(@Valid @RequestBody PostApiAppIssuedCouponRequestData requestData) {
        mobileService.issuedCoupon(requestData);
        return ResponseEntity.ok().build();
    }

}
