package com.osmp.web.system.config.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.core.tools.HttpClientWrapper;
import com.osmp.web.system.config.service.ConfigService;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.utils.Assert;

/**
 * 配置信息相关操作
 * @author heyu
 *
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    private Logger logger = Logger.getLogger(ConfigServiceImpl.class);

    @Resource
    private HttpClientWrapper httpClientWrapper;
    
    public boolean refresh(String target) {
        String freshAddr = SystemFrameWork.proMap.get("configFresh");
        if(Assert.isEmpty(freshAddr)){
            logger.error("插件容器刷新接口配置(key:configFresh) 未加入资源配置...");
            return false;
        }
        List<Servers> servers = SystemFrameWork.servers;
        for(Servers server : servers){
            if(server.getState() != 1) continue;
            String url = "http://" + server.getServerIp() + ":" + server.getServerPort() + "/" + freshAddr + "/"+target;
            String res = httpClientWrapper.get(url);
            logger.info("target:"+target+",fresh server:"+server.getServerIp()+",response:"+res);
        }
        return true;
    }

}
