package com.job.biz.initialize;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.job.common.utils.ApplicationContextUtil;
import com.job.quartz.service.SchedulerService;

/**
 * 
 * @Description 项目启动成功后执行一次，初始化JOB List
 * @author 王鑫 
 * @date Oct 19, 2016 9:08:29 AM
 */
public class InitializeServlet extends HttpServlet {

    private static final long serialVersionUID = -8652114793504953973L;

    private static final Logger log = LoggerFactory.getLogger(InitializeServlet.class);

    /**
     * 
     * @Description 初始化JOB List
     * @author 王鑫
     */
    public void initialize() {
        SchedulerService schedulerService = ApplicationContextUtil.getBean("schedulerService", SchedulerService.class);

        schedulerService.schedule("退款", "customerService", "0 0/30 * ? * * *");// 每30秒执行一次
        // schedulerService.schedule("退款", "customerService", "0/5 * * ? * * *");//每30分钟执行一次
    }

    public InitializeServlet() {

    }

    @Override
    public void init(final ServletConfig config) {
        log.info("#初始化定时任务列表......");
        initialize();
    }

}
