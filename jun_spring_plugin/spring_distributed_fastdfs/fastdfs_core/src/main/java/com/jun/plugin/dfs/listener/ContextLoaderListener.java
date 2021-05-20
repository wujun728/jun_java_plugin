package com.jun.plugin.dfs.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.dfs.core.service.AppInfoService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@Service
public class ContextLoaderListener implements ServletContextListener {

    @Autowired
    private AppInfoService appInfoService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        appInfoService.loadAppInfoToCache();
        if (log.isDebugEnabled()) {
            log.debug("all appInfo loaded to cache!");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.debug("context stopped!");
    }
}
