package com.example.couponworker.utils;

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

    public static <T> T readData(ObjectMapper objectMapper,String data, Class<T> tClass) {
        try {
            return objectMapper.readValue(data,tClass);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

}
