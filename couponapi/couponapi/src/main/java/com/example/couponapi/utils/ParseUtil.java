package com.example.couponapi.utils;

import com.example.couponapi.api.mobile.dto.PostApiAppIssuedCouponRequestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParseUtil {

    public static <T> String parseString(ObjectMapper objectMapper,T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }

    }
}
