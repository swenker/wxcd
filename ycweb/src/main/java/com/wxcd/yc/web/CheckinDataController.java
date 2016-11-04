package com.wxcd.yc.web;

import com.wxcd.yc.service.CheckinDataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wenjusun on 11/2/16.
 */

@Controller
public class CheckinDataController {

    @Autowired
    private CheckinDataStatisticsService checkinDataStatisticsService;

    @RequestMapping(value="/all")
    public String getAllDevicesCounters(){

        return "welcome";
    }

    @RequestMapping(value="/allbyday",method = RequestMethod.GET, produces = "application/json")
//    @RequestMapping(value="/allbyday",method = RequestMethod.GET)
    @ResponseBody
    public String getAllDevicesCountersByday(){

        checkinDataStatisticsService.getAllDeviceCounters();

        return "welcome";
    }
}
