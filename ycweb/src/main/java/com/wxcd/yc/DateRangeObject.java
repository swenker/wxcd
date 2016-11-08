package com.wxcd.yc;

/**
 * Created by wenjusun on 11/7/16.
 */
public class DateRangeObject {
    private String startDate;
    private String endDate;

    public DateRangeObject(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "DateRangeObject{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
