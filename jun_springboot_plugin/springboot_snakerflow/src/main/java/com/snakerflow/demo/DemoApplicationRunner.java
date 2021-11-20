package com.snakerflow.demo;

import com.snakerflow.demo.utils.SnakerEngineFacadeImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 说明：在项目启动成功之后，立马部署工作流模板。
 *
 * @author liuxzh
 * @version 1.00
 * @since digibook2.0
 */
@Component
@Order(value = 1)
public class DemoApplicationRunner implements ApplicationRunner {
    @Autowired
    private SnakerEngineFacadeImpl engineUtil;
    //流程模板路径(配置文件读取）
    @Value("${snaker.workflow.model.path}")
    private String path;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        //System.out.println("项目部署成功，当前时间：" + new Date());
        String processId = engineUtil.deploy(path);
        if (StringUtils.isNotBlank(processId)) {
            //部署流程模板成功处理代码
            //System.out.println("部署流程模板成功，流程定义id：" + processId);
        } else {
            //部署流程模板失败处理代码
        }
    }
}
