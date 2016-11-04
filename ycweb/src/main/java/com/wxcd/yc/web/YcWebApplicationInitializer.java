package com.wxcd.yc.web;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by wenjusun on 11/2/16.
 */
//public class YcWebApplicationInitializer implements WebApplicationInitializer {
public class YcWebApplicationInitializer{

//    @Override
    public void onStartup(ServletContext container) {
        ServletRegistration.Dynamic registration = container.addServlet("report", new DispatcherServlet());
        registration.setLoadOnStartup(1);
        registration.addMapping("/report/*");
    }

}
