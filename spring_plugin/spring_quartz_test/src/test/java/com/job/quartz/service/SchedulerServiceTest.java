package com.job.quartz.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.job.quartz.service.SchedulerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext.xml", "classpath*:spring/applicationContext-quartz.xml" })
public class SchedulerServiceTest {

    @Autowired
    private SchedulerService schedulerService;

    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Test
    public void test() {
        try {

            // 执行业务逻辑...

            // 设置高度任务
            // 每2秒中执行调试一次
            schedulerService.schedule("0/2 * * ? * * *");

            Date startTime = this.parse("2016-09-08 20:55:00");
            Date endTime = this.parse("2016-09-08 21:55:00");
            // 2009-06-01 21:50:00开始执行调度
            // schedulerService.schedule(startTime);
            schedulerService.schedule(startTime, endTime);

            // 2009-06-01 21:50:00开始执行调度，2009-06-01 21:55:00结束执行调试
            // schedulerService.schedule(startTime, endTime);

            // 2009-06-01 21:50:00开始执行调度，执行5次结束
            // schedulerService.schedule(startTime, null, 5);

            // 2009-06-01 21:50:00开始执行调度，每隔20秒执行一次，执行5次结束
            // schedulerService.schedule(startTime, null, 5, 20);

            // 等等，查看com.sundoctor.quartz.service.SchedulerService
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Date parse(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
