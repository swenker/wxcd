package com.wxcd.yc.service;

import com.wxcd.yc.model.DeviceCounter;
import com.wxcd.yc.model.SimpleDeviceCounter;

import java.util.List;

/**
 * Created by wenjusun on 11/3/16.
 */
public interface CheckinDataStatisticsService {
    public List<DeviceCounter> getAllDeviceCounters();

    public List<DeviceCounter> getDeviceCountersByMonth(String yearMonth);

    public List<DeviceCounter> getDeviceCountersByWeek(String dateStr);
    public List<DeviceCounter> getDeviceCountersByDay(String dateStr);

    public List<SimpleDeviceCounter> getAllCounterByWeekOfYear(String year);
    public List<SimpleDeviceCounter> getAllCounterByMonthOfYear(String year);


}
