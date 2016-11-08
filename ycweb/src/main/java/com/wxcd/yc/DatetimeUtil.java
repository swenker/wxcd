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

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateRangeObject getWeekRange(String dateStr){
        LocalDate localDate = LocalDate.parse(dateStr,dateTimeFormatter);

        //4 is ISO compatible
        TemporalField weekdayField = WeekFields.of(DayOfWeek.MONDAY, 4).dayOfWeek();

        return new DateRangeObject(
                localDate.with(weekdayField,1).format(dateTimeFormatter),
                localDate.with(weekdayField,7).format(dateTimeFormatter)
        );

    }
}
