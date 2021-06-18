package com.job.biz.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("serverBuilderContext")
public class ServerBuilderContext {

    private static Logger log = LoggerFactory.getLogger(ServerBuilderContext.class);

    public static Map<String, Class<? extends DefaultService>> SERVERS = new HashMap<String, Class<? extends DefaultService>>();

    @Autowired
    private ApplicationContext applicationContext;

    public static void addServer(String serverName, Class<? extends DefaultService> clazz) {
        log.info("##ServerBuilderContext.addServer({} , {})", serverName, clazz);
        SERVERS.put(serverName, clazz);
    }

    private DefaultService getServer(String serverName) {
        Class<? extends DefaultService> clazz = SERVERS.get(serverName);
        DefaultService processor = applicationContext.getBean(clazz);
        return processor;
    }

    public void execute(String serverName) {
        log.info("#ServerBuilderContext.execute({})", serverName);
        DefaultService server = getServer(serverName);
        try {
            server.execute();
        } catch (Exception e) {
            log.info("#ServerBuilderContext.execute({}) error  , [{}]", serverName, e.getLocalizedMessage());
        }
    }

}
