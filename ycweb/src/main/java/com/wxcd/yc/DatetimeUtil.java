package com.wxcd.yc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

/**
 * Created by wenjusun on 11/7/16.
 */
public class DatetimeUtil {

    public static DateTimeFormatter yyyyMMddFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter yyyyMMFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
    public static DateRangeObject getWeekRange(String dateStr){
        LocalDate localDate = LocalDate.parse(dateStr, yyyyMMddFormatter);

        //4 is ISO compatible
        TemporalField weekdayField = WeekFields.of(DayOfWeek.MONDAY, 4).dayOfWeek();

        return new DateRangeObject(
                localDate.with(weekdayField,1).format(yyyyMMddFormatter),
                localDate.with(weekdayField,7).format(yyyyMMddFormatter)
        );
    }

    public static String getToday(){
        LocalDate localDate = LocalDate.now();

        return localDate.format(yyyyMMddFormatter);
    }

    public static String getYesterday(){
        LocalDate localDate = LocalDate.now().minusDays(1);

        return localDate.format(yyyyMMddFormatter);
    }


    public static String getDayInLastWeek(){
        LocalDate localDate = LocalDate.now().minusDays(7);
        return localDate.format(yyyyMMddFormatter);
    }

    public static String getThisMonth(){
        LocalDate localDate = LocalDate.now();

        return localDate.format(yyyyMMFormatter);
    }
}
