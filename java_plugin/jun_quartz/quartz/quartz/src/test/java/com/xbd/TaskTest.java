package com.xbd;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSONObject;
import com.xbd.quartz.service.TaskService;
import com.xbd.quartz.util.QuartzUtil;
import com.xbd.quartz.vo.QuartzTaskVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/spring-*.xml")
public class TaskTest
{
    @Autowired
    private QuartzUtil quartzUtil;

    @Autowired
    private TaskService taskService;

    @Test
    public void testSend() throws Exception {
        List<QuartzTaskVO> quartzTaskVOList = quartzUtil.tasks();

        System.out.println("-------" + JSONObject.toJSONString(quartzTaskVOList));
    }

}
