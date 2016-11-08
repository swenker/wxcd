package com.wxcd.yc.lab;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by wenjusun on 11/7/16.
 */
public class CalendarTest {

//    @Test
    public void testGetWeek_legacy(){
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime();
    }

    @Test
    public void testGetWeek8(){

        LocalDate ld = LocalDate.parse("2017-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println(ld.get(WeekFields.of(Locale.CHINA).weekOfYear()));

        TemporalField weekdayField = WeekFields.of(DayOfWeek.MONDAY,7).dayOfWeek();
        TemporalField weekyearField = WeekFields.of(DayOfWeek.MONDAY,1).weekOfYear();


//        System.out.println(ld.);
        System.out.println(weekdayField);

        System.out.println(ld.with(weekdayField,1));
        System.out.println(ld.with(weekdayField,7));

        System.out.println(ld.with(weekyearField,1));

    }
}
