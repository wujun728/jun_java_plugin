package tqlin.service.impl;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.rpc.RpcContext;
import tqlin.service.IDemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoServiceImpl implements IDemoService {
    private final static Logger LOGGER = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] 你好 " + name + ", 请求消费者地址: " + RpcContext.getContext().getRemoteAddress());
        return "你好 " + name + ", 服务提供者地址: " + RpcContext.getContext().getLocalAddress();
    }
}
