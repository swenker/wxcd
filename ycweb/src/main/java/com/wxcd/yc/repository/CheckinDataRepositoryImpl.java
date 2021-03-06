package com.wxcd.yc.repository;

import com.wxcd.yc.DateRangeObject;
import com.wxcd.yc.model.DeviceCounter;
import com.wxcd.yc.model.SimpleDeviceCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by wenjusun on 11/4/16.
 */

@Repository
public class CheckinDataRepositoryImpl implements CheckinDataRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CheckinDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    private List<DeviceCounter> getDeviceCountersList(String sql){
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return DeviceCounter.Builder.create()
                    .setAllCounter(resultSet.getInt("all_counter"))
                    .setUniqueCounter(resultSet.getInt("unique_counter"))
                    .setEventDate(resultSet.getString("event_date"))
                    .build();
        });
    }

    private List<SimpleDeviceCounter> getSimpleCounterList(String sql){
        return jdbcTemplate.query(sql,(resultSet,rowNum)->{
            return SimpleDeviceCounter.Builder.create()
                    .setCounter(resultSet.getInt("devices_counter"))
                    .setEventTime(resultSet.getString("event_time"))
                    .build();
        });
    }


    @Override
    public List<DeviceCounter> getDeviceCounters() {

        String sql = "SELECT event_date,all_counter,unique_counter FROM devices_counter_byday ORDER BY event_date";

/*
        RowMapper<DeviceCounter> rowMapper = new RowMapper<DeviceCounter>() {
            @Override
            public DeviceCounter mapRow(ResultSet resultSet, int i) throws SQLException {
                final DeviceCounter deviceCounter = DeviceCounter.Builder.create()
                        .setAllCounter(resultSet.getInt("all_counter"))
                        .setUniqueCounter(resultSet.getInt("unique_counter"))
                        .setEventDate(resultSet.getString("event_date"))
                        .build();

                return deviceCounter;
            }
        };

        List<DeviceCounter> counters = jdbcTemplate.query(sql,rowMapper);
*/



//        counters.stream().forEach(System.out::println);

        return this.getDeviceCountersList(sql);
    }

    @Override
    public List<DeviceCounter> getDeviceCountersByYear(String year) {

        String sql = "SELECT event_date,all_counter,unique_counter FROM devices_counter_byday WHERE DATE_FORMAT(event_date,'%Y')='"+year+"' ORDER BY event_date";

        return this.getDeviceCountersList(sql);
    }


    @Override
    public List<DeviceCounter> getDeviceCountersOfMonth(String yearMonth) {

        String sql = "SELECT event_date,all_counter,unique_counter FROM devices_counter_byday WHERE DATE_FORMAT(event_date,'%Y-%m') LIKE '"+yearMonth+"%' ORDER BY event_date";

        return this.getDeviceCountersList(sql);

    }

    @Override
    public List<DeviceCounter> getDeviceCountersOfDateRange(DateRangeObject dateRange) {

//        System.out.println(dateRange);
        String sql = "SELECT event_date,all_counter,unique_counter FROM devices_counter_byday WHERE DATE_FORMAT(event_date,'%Y-%m-%d')>='"+dateRange.getStartDate()
                +"' AND DATE_FORMAT(event_date,'%Y-%m-%d') <='"
                +dateRange.getEndDate()
                +"' ORDER BY event_date";

        return this.getDeviceCountersList(sql);
    }

    @Override
    public List<DeviceCounter> getDeviceCountersOfDay(String dtstr) {
        String sql = "SELECT event_date,all_counter,unique_counter FROM devices_counter_byday WHERE DATE_FORMAT(event_date,'%Y-%m-%d')='"+dtstr+"'";

        return this.getDeviceCountersList(sql);

    }

    @Override
    public List<SimpleDeviceCounter> getAllCounterByWeekOfYear(String year) {
        String sql = "select event_week as event_time,all_counter as devices_counter from devices_counter_byweek where event_week like'"+year+"%' ORDER BY event_week";

        return this.getSimpleCounterList(sql);
    }

    @Override
    public List<SimpleDeviceCounter> getAllCounterByMonthOfYear(String year) {
        String sql = "select event_month as event_time,all_counter as devices_counter from devices_counter_bymonth where event_month like'"+year+"%' ORDER BY event_month";

        return this.getSimpleCounterList(sql);
    }
}
