package com.wxcd.yc.repository;

import com.wxcd.yc.model.DeviceCounter;

import java.util.List;

/**
 * Created by wenjusun on 11/4/16.
 */
public interface CheckinDataRepository {
    public List<DeviceCounter> getDeviceCounters();

}
