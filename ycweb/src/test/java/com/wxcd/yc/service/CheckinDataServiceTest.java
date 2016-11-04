package com.wxcd.yc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wenjusun on 11/4/16.
 */

@ContextConfiguration(locations = {"classpath:spring/business-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("jdbc")
public class CheckinDataServiceTest {

    @Autowired
    private CheckinDataStatisticsService checkinDataStatisticsService;

    @Test
    public void testGetAllDeviceCounters(){

        checkinDataStatisticsService.getAllDeviceCounters();


    }

}
