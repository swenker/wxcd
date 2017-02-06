package com.wxcd.yc.repository;

import com.wxcd.yc.DateRangeObject;
import com.wxcd.yc.model.DeviceCounter;
import com.wxcd.yc.model.SimpleDeviceCounter;

import java.util.List;

/**
 * Created by wenjusun on 11/4/16.
 */
public interface CheckinDataRepository {
    public List<DeviceCounter> getDeviceCounters();
    public List<DeviceCounter> getDeviceCountersByYear(String year);

    public List<DeviceCounter> getDeviceCountersOfMonth(String yearMonth);

    public List<DeviceCounter> getDeviceCountersOfDateRange(DateRangeObject dateRange);

    public List<DeviceCounter> getDeviceCountersOfDay(String dtstr);

    public List<SimpleDeviceCounter> getAllCounterByWeekOfYear(String year);
    public List<SimpleDeviceCounter> getAllCounterByMonthOfYear(String year);



}
