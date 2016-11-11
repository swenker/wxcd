package com.wxcd.yc.service;

import com.wxcd.yc.model.DeviceCounter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by wenjusun on 11/4/16.
 */

@ContextConfiguration(locations = {"classpath:spring/business-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jdbc")
public class CheckinDataServiceTest {

    @Before
    public void setup(){
        System.out.println("---------------------------");
    }
    @Autowired
    private CheckinDataStatisticsService checkinDataStatisticsService;

//    @Test
    public void testGetAllDeviceCounters(){

        checkinDataStatisticsService.getAllDeviceCounters();

    }


//    @Test
    public void testGetDeviceCountersOfMonth(){
        List<DeviceCounter> dclist = checkinDataStatisticsService.getDeviceCountersByMonth("2016-11");

        dclist.forEach(System.out::println);

    }

    @Test
    public void testGetDeviceCountersByWeek(){
        List<DeviceCounter> dclist = checkinDataStatisticsService.getDeviceCountersByWeek("2016-11-04");

        dclist.forEach(System.out::println);

    }

}
