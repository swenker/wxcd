package com.wxcd.yc.service;

import com.wxcd.yc.DatetimeUtil;
import com.wxcd.yc.model.DeviceCounter;
import com.wxcd.yc.repository.CheckinDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wenjusun on 11/3/16.
 */

@Service
public class CheckinDataStatisticsServiceImpl implements CheckinDataStatisticsService{


    @Autowired
    private CheckinDataRepository checkinDataRepository;



    @Override
    public List<DeviceCounter> getAllDeviceCounters() {
        return checkinDataRepository.getDeviceCounters();
    }

    @Override
    public List<DeviceCounter> getDeviceCountersByMonth(String yearMonth) {

        return checkinDataRepository.getDeviceCountersOfMonth(yearMonth);
    }

    @Override
    public List<DeviceCounter> getDeviceCountersByWeek(String dateStr) {

        return checkinDataRepository.getDeviceCountersOfDateRange(DatetimeUtil.getWeekRange(dateStr));

    }

    @Override
    public List<DeviceCounter> getDeviceCountersByDay(String dateStr) {
        return checkinDataRepository.getDeviceCountersOfDay(dateStr);
    }
}
