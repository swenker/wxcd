package com.wxcd.yc.web;

import com.wxcd.yc.model.DeviceCounter;
import com.wxcd.yc.service.CheckinDataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wenjusun on 11/7/16.
 */

@RestController
@RequestMapping("/ckp")

public class CheckinReportController {

    @Autowired
    private CheckinDataStatisticsService checkinDataStatisticsService;

    @RequestMapping(value = "/allunique",method = RequestMethod.GET,produces = "application/json")
    public List<DeviceCounter> getAllAndUniqueCounter(){

        return checkinDataStatisticsService.getAllDeviceCounters();
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeviceCounterListVO> getAllAndUniqueCounters(){

        return new ResponseEntity<>(
                new DeviceCounterListVO(checkinDataStatisticsService.getAllDeviceCounters()), HttpStatus.OK);
    }

    @RequestMapping(value = "/counterbyday", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAllAndUniqueCountersByDay(@RequestParam(name="dt",required = false) String dt) {

        return new ResponseEntity<String>(dt,HttpStatus.OK);
    }

    class DeviceCounterListVO{
        private List<DeviceCounter> dclist;

        public DeviceCounterListVO(List<DeviceCounter> dclist) {
            this.dclist = dclist;
        }

        public List<DeviceCounter> getDclist() {
            return dclist;
        }
    }

}
