package com.wxcd.yc.repository;

import com.wxcd.yc.model.DeviceCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenjusun on 11/4/16.
 */

@Repository
public class CheckinDataRepositoryImpl implements CheckinDataRepository{


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CheckinDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<DeviceCounter> getDeviceCounters() {

        String sql = "SELECT event_date,all_counter,unique_counter FROM devices_counter_byday ORDER BY event_date";

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


//        counters.stream().forEach(System.out::println);

        return counters;
    }
}
