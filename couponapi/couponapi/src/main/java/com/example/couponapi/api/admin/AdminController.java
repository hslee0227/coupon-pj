package com.example.couponapi.api.admin;


import com.example.couponapi.api.admin.dto.PostApiAdmCouponSaveRequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adm")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/coupon/save")
    public ResponseEntity<?> postApiAdmCouponSave(
        @RequestBody PostApiAdmCouponSaveRequestData requestData
    ) {
        adminService.saveAdmCouponPolicy(requestData);
        return ResponseEntity.ok().build();
    }

}
