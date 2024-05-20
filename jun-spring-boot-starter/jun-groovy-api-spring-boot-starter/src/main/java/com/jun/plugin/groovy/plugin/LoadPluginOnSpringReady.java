package com.jun.plugin.groovy.plugin;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoadPluginOnSpringReady {
    @EventListener
    public void loadPlugins(ApplicationReadyEvent event){
        PluginManager.loadPlugins();
    }
}