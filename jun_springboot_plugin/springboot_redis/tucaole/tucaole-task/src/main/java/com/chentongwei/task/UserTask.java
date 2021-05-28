package com.chentongwei.task;

import com.chentongwei.core.user.biz.user.UserBiz;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户模块Task
 */
@Component
public class UserTask {
    private static final Logger LOG = LogManager.getLogger(UserTask.class);

    @Autowired
    private UserBiz userBiz;

    /**
     * 删除没有激活的用户
     * 每天跑一次小程序删除那些大于两小时还没激活的用户，这些用户被视为无效用户
     */
    @Scheduled(cron = "0 30 03 * * ?")
    public void deleteUnActiveUser() {
        userBiz.deleteUnActiveUser();
        LOG.info("------删除未激活的用户成功------");
    }
}
