package com.wxcd.yc.repository;

import com.wxcd.yc.DateRangeObject;
import com.wxcd.yc.model.DeviceCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
