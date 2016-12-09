package com.wxcd.yc.web;

import com.wxcd.yc.DatetimeUtil;
import com.wxcd.yc.model.DeviceCounter;
import com.wxcd.yc.model.SimpleDeviceCounter;
import com.wxcd.yc.service.CheckinDataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenjusun on 11/7/16.
 */

@RestController
@RequestMapping("/ckp")

public class CheckinReportController {

    @Autowired
    private CheckinDataStatisticsService checkinDataStatisticsService;


    @RequestMapping(value="/menu",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MenuList>getMenuList(HttpServletRequest httpRequest){
        List<MenuItem> menuItemList = new ArrayList<>();
        String contextPath = httpRequest.getContextPath();
        menuItemList.add(new MenuItem("Yesterday",contextPath+"/ckp/"+"counterbyday?dt="+ DatetimeUtil.getYesterday()));
        menuItemList.add(new MenuItem("This Week",contextPath+"/ckp/"+"weekcounterbyday?dt="+ DatetimeUtil.getToday()));
        menuItemList.add(new MenuItem("Last Week",contextPath+"/ckp/"+"weekcounterbyday?dt="+ DatetimeUtil.getDayInLastWeek()));
        menuItemList.add(new MenuItem("This Month",contextPath+"/ckp/"+"monthcounter?dt="+ DatetimeUtil.getThisMonth()));

        return new ResponseEntity<>(new MenuList(menuItemList),HttpStatus.OK);
    }


    @RequestMapping(value = "/all",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeviceCounterListVO> getAllAndUniqueCounters(){

        MultiValueMap<String,String>reponseHeaders = new HttpHeaders();
        reponseHeaders.add("Access-Control-Allow-Origin","*");
        return new ResponseEntity<>(
                new DeviceCounterListVO(checkinDataStatisticsService.getAllDeviceCounters()),
                reponseHeaders,
                HttpStatus.OK);
    }

    @RequestMapping(value = "/counterbyday", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeviceCounterListVO> getAllAndUniqueCountersOfDay(@RequestParam(name="dt",required = false) String dt) {

        List<DeviceCounter>dclist= checkinDataStatisticsService.getDeviceCountersByDay(dt);

        return new ResponseEntity<>(new DeviceCounterListVO(dclist),HttpStatus.OK);
    }

    @RequestMapping(value = "/allcounterbyweekofyear", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SimpleDeviceCounterListVO> getAllCounterByWeekOfYear(@RequestParam(name="year",required = false) String year) {

        List<SimpleDeviceCounter>dclist= checkinDataStatisticsService.getAllCounterByWeekOfYear(year);

        return new ResponseEntity<>(new SimpleDeviceCounterListVO(dclist),HttpStatus.OK);
    }

    @RequestMapping(value = "/allcounterbymonthofyear", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SimpleDeviceCounterListVO> getAllCounterByMonthOfYear(@RequestParam(name="year",required = false) String year) {

        List<SimpleDeviceCounter>dclist= checkinDataStatisticsService.getAllCounterByMonthOfYear(year);

        return new ResponseEntity<>(new SimpleDeviceCounterListVO(dclist),HttpStatus.OK);
    }


    @RequestMapping(value = "/weekcounterbyday", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeviceCounterListVO> getAllAndUniqueCountersOfWeek(@RequestParam(name="dt",required = false) String dt) {

        List<DeviceCounter>dclist= checkinDataStatisticsService.getDeviceCountersByWeek(dt);

        return new ResponseEntity<>(new DeviceCounterListVO(dclist),HttpStatus.OK);
    }

    @RequestMapping(value = "/monthcounter", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    public ResponseEntity<String> getAllAndUniqueCountersOfMonth(@RequestParam(name="dt",required = false) String dt) {
    public ResponseEntity<DeviceCounterListVO> getAllAndUniqueCountersOfMonth(@RequestParam String dt) {

        MultiValueMap<String,String>reponseHeaders = new HttpHeaders();
        reponseHeaders.add("Access-Control-Allow-Origin","*");

        List<DeviceCounter>dclist= checkinDataStatisticsService.getDeviceCountersByMonth(dt);
        return new ResponseEntity<>(new DeviceCounterListVO(dclist),reponseHeaders,HttpStatus.OK);
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

    class SimpleDeviceCounterListVO{
        private List<SimpleDeviceCounter> dclist;

        public SimpleDeviceCounterListVO(List<SimpleDeviceCounter> dclist) {
            this.dclist = dclist;
        }

        public List<SimpleDeviceCounter> getDclist() {
            return dclist;
        }
    }

    class MenuItem{
        private String name;
        private String link;

        public MenuItem(String name, String link) {
            this.name = name;
            this.link = link;
        }

        public String getName() {
            return name;
        }

        public String getLink() {
            return link;
        }

        @Override
        public String toString() {
            return "MenuItem{" +
                    "name='" + name + '\'' +
                    ", link='" + link + '\'' +
                    '}';
        }
    }
    class MenuList{
        private List<MenuItem> menuItemList;

        public MenuList(List<MenuItem> menuItemList) {
            this.menuItemList = menuItemList;
        }

        public List<MenuItem> getMenuItemList() {
            return menuItemList;
        }
    }

}
