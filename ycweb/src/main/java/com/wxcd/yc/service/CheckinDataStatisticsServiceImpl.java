package com.wxcd.yc.service;

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
        checkinDataRepository.getDeviceCounters();
        System.out.println("is null:"+checkinDataRepository==null);
        return null;
    }
}
