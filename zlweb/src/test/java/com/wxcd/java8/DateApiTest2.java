package com.wxcd.java8;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Created by wenjusun on 10/10/16.
 */
public class DateApiTest2 {

    @Test
    public void testNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.getHour());
//        long nowInMillionSeconds = LocalDate.now().

        localDateTime = LocalDateTime.now(ZoneId.of("PRC"));
        System.out.println(localDateTime.getHour());

        localDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        System.out.println(localDateTime.getHour());

        System.out.println(System.currentTimeMillis());
        System.out.println(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());

    }
}
