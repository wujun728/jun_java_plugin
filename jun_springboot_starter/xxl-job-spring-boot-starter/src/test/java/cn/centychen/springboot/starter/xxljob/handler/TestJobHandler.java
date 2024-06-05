package cn.centychen.springboot.starter.xxljob.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/**
 * Create by cent at 2019-05-08.
 */
@JobHandler("testJobHandler")
@Component
public class TestJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        XxlJobLogger.log(">>>>>>> This is a test job.");
        Thread.sleep(5 * 1000L);
        return SUCCESS;
    }
}
