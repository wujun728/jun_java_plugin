package com.chentongwei.task;

import com.chentongwei.core.tucao.biz.article.ReadCountBiz;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 吐槽文章的小任务
 */
@Component
public class TucaoArticleTask {
    private static final Logger LOG = LogManager.getLogger(TucaoArticleTask.class);

    @Autowired
    private ReadCountBiz readCountBiz;

    /**
     * 每十分钟就update文章阅读量
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void updateReadCount() {
        readCountBiz.triggerUpdateReadCount();
    }

}
