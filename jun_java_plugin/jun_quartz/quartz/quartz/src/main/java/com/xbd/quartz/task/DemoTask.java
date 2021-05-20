package com.xbd.quartz.task;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DemoTask extends AbstractQuartzTask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void execute() throws Exception {
        logger.info("我是测试任务！");
    }

    public void execute(Integer a, Integer b, JSONObject c, Boolean d) throws Exception {
        logger.info("我是带参测试任务，参数为：a:{},b:{},c:{},d:{}", new Object[] { a, b, c, d});
    }

}
