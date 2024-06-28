package io.github.wujun728.snakerflow.process;

import java.util.List;

import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;

/**
 * description: SnakerflowTestService
 *
 * @author guoqing.zhao
 * Date: 2020-03-23 9:20 下午
 */
@Service
public class SnakerflowTestService {
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;


    /**
     * 主要用于测试snakerflow是否正常加载
     *
     * @return String
     */
    @Transactional
    public String getProcess() {
        snakerEngineFacets.initFlows();
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        return JSONUtil.toJsonStr(allProcess);
    }

    /**
     * 主要用于测试snakerflow是否正常加载
     *
     * @return String
     */
    @Transactional
    public String start() {
        List<Process> allProcess = snakerEngineFacets.getAllProcess();
        if (CollUtil.isEmpty(allProcess)) {
            return "请先初始化process，http://localhost:8080/getProcessList";
        }
        Order order = snakerEngineFacets.startInstanceById(allProcess.get(0).getId(), "zhaoguoqing", null);
        return JSONUtil.toJsonStr(order);
    }
}