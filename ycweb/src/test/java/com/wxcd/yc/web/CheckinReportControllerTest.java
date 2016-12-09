package com.wxcd.yc.web;

import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wenjusun on 11/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/business-config.xml","classpath:spring/mvc-core-config.xml"})
@ActiveProfiles("jdbc")
public class CheckinReportControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void testGetCountersByDay()throws Exception{

        mockMvc.perform(get("/ckp/counterbyday?dt=abc").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("abc"))
        ;

    }
    @Test
    public void testGetWeekCountersByDay()throws Exception{

        mockMvc.perform(get("/ckp/weekcounterbyday?dt=2016-11-01").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("abc"))
        ;
    }

//    @Test
    public void testGetMenuList() throws Exception{
        mockMvc.perform(get("/ckp/menu").accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(StringContains.containsString("abc")))
                ;
    }

    @Test
    public void testGetMonthCounter()throws Exception{
        mockMvc.perform(get("/ckp/monthcounter?dt=2016-11").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(StringContains.containsString(":218834")));

    }

    @Test
    public void testGetAllCounterByWeekOfYear()throws Exception{
        mockMvc.perform(get("/ckp/allcounterbyweekofyear?year=2016").accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(StringContains.containsString("2511981")))
                ;

    }

    @Test
    public void testGetAllCounterByMonthOfYear()throws Exception{

        mockMvc.perform(get("/ckp/allcounterbymonthofyear?year=2016").accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(StringContains.containsString(":8906127")))
                ;
    }

}
