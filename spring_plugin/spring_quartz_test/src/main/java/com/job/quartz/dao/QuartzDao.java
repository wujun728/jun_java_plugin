package com.job.quartz.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.job.common.constants.Constants;

@Repository("quartzDao")
public class QuartzDao {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Map<String, Object>> getQrtzTriggers() {
        List<Map<String, Object>> results = getJdbcTemplate().queryForList("select * from QRTZ_TRIGGERS order by start_time");
        long val = 0;
        String temp = null;
        for (Map<String, Object> map : results) {
            temp = MapUtils.getString(map, "trigger_name");
            if (StringUtils.indexOf(temp, "&") != -1) {
                map.put("display_name", StringUtils.substringBefore(temp, "&"));
            } else {
                map.put("display_name", temp);
            }

            val = MapUtils.getLongValue(map, "next_fire_time");
            if (val > 0) {
                map.put("next_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            }

            val = MapUtils.getLongValue(map, "prev_fire_time");
            if (val > 0) {
                map.put("prev_fire_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            }

            val = MapUtils.getLongValue(map, "start_time");
            if (val > 0) {
                map.put("start_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            }

            val = MapUtils.getLongValue(map, "end_time");
            if (val > 0) {
                map.put("end_time", DateFormatUtils.format(val, "yyyy-MM-dd HH:mm:ss"));
            }

            map.put("statu", Constants.status.get(MapUtils.getString(map, "trigger_state")));
        }

        return results;
    }

    private JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(this.dataSource);
    }
}
