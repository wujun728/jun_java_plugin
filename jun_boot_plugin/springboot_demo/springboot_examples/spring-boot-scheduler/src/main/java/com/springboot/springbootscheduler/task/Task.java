package com.springboot.springbootscheduler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/10/5
 * @Time: 23:32
 * @email: inwsy@hotmail.com
 * Description:
 */
@Component
public class Task {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final Logger logger = LoggerFactory.getLogger(Task.class);

    /**
     * cron表达式
     */
    @Scheduled(cron = "*/5 * * * * ?")
    private void task1() {
        logger.info("task1 正在执行，现在时间：{}", dateFormat.format(new Date()));
    }

    /**
     * 上一次开始执行时间点之后5秒再执行
     */
    @Scheduled(fixedRate = 5000)
    public void task2() {
        logger.info("task2 正在执行，现在时间：{}", dateFormat.format(new Date()));
    }

    /**
     * 上一次执行完毕时间点之后5秒再执行
     */
    @Scheduled(fixedDelay = 5000)
    public void task3() {
        logger.info("task3 正在执行，现在时间：{}", dateFormat.format(new Date()));
    }

    /**
     * 第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
     */
    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void task4() {
        logger.info("task4 正在执行，现在时间：{}", dateFormat.format(new Date()));
    }
}
