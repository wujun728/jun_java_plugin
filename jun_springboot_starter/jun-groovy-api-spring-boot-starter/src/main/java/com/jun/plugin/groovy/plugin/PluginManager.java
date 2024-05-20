package com.jun.plugin.groovy.plugin;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class PluginManager {

//    private static Map<String, CachePlugin> cachePlugins = new ConcurrentHashMap<>();
//    private static Map<String, TransformPlugin> transformPlugins = new ConcurrentHashMap<>();
//    private static Map<String, TestPlugin> alarmPlugins = new ConcurrentHashMap<>();
    private static Map<String, BasePlugin> plugins = new ConcurrentHashMap<>();
    
    public static void main(String[] args) {
		PluginManager.loadPlugins();
	}

    public static void loadPlugins() {

        ServiceLoader<BasePlugin> serviceLoader = ServiceLoader.load(BasePlugin.class);
        Iterator<BasePlugin> CachePlugins = serviceLoader.iterator();
        while (CachePlugins.hasNext()) {
        	BasePlugin plugin = CachePlugins.next();
            plugin.init();
            log.info("BasePlugin {} registered", plugin.getClass().getName());
            plugins.put(plugin.getClass().getName(), plugin);
        }
        log.info("scan BasePlugin is finish");
        
        
//        ServiceLoader<CachePlugin> serviceLoader = ServiceLoader.load(CachePlugin.class);
//        Iterator<CachePlugin> CachePlugins = serviceLoader.iterator();
//        while (CachePlugins.hasNext()) {
//        	CachePlugin plugin = CachePlugins.next();
//        	plugin.init();
//        	log.info("{} registered", plugin.getClass().getName());
//        	cachePlugins.put(plugin.getClass().getName(), plugin);
//        }
//        log.info("scan cache plugin finish");
//
//        ServiceLoader<TransformPlugin> serviceLoader2 = ServiceLoader.load(TransformPlugin.class);
//        Iterator<TransformPlugin> TransformPlugins = serviceLoader2.iterator();
//        while (TransformPlugins.hasNext()) {
//            TransformPlugin plugin = TransformPlugins.next();
//            plugin.init();
//            log.info("{} registered", plugin.getClass().getName());
//            transformPlugins.put(plugin.getClass().getName(), plugin);
//        }
//        log.info("scan transform plugin finish");
//
//        ServiceLoader<TestPlugin> serviceLoader3 = ServiceLoader.load(TestPlugin.class);
//        Iterator<TestPlugin> AlarmPlugins = serviceLoader3.iterator();
//        while (AlarmPlugins.hasNext()) {
//            TestPlugin plugin = AlarmPlugins.next();
//            plugin.init();
//            log.info("{} registered", plugin.getClass().getName());
//            alarmPlugins.put(plugin.getClass().getName(), plugin);
//        }
//        log.info("scan alarm plugin finish");
    }

    public static BasePlugin getPlugin(String className) {
        if (!plugins.containsKey(className)) {
            throw new RuntimeException("Plugin not found: " + className);
        }
        return plugins.get(className);
    }

//    public static TransformPlugin getTransformPlugin(String className) {
//        if (!transformPlugins.containsKey(className)) {
//            throw new RuntimeException("Plugin not found: " + className);
//        }
//        return transformPlugins.get(className);
//    }
//
//    public static TestPlugin getAlarmPlugin(String className) {
//        if (!alarmPlugins.containsKey(className)) {
//            throw new RuntimeException("Plugin not found: " + className);
//        }
//        return alarmPlugins.get(className);
//    }

    public static List<JSONObject> getAllPlugin() {
        List<JSONObject> collect = plugins.values().stream().map(t -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("className", t.getClass().getName());
            jsonObject.put("name", t.getName());
            jsonObject.put("description", t.getDescription());
            jsonObject.put("paramDescription", t.getParamDescription());
            return jsonObject;
        }).collect(Collectors.toList());
        return collect;
    }

//    public static List<JSONObject> getAllTransformPlugin() {
//        List<JSONObject> collect = transformPlugins.values().stream().map(t -> {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("className", t.getClass().getName());
//            jsonObject.put("name", t.getName());
//            jsonObject.put("description", t.getDescription());
//            jsonObject.put("paramDescription", t.getParamDescription());
//            return jsonObject;
//        }).collect(Collectors.toList());
//        return collect;
//    }
//
//    public static List<JSONObject> getAllAlarmPlugin() {
////        return alarmPlugins.keySet();
//        List<JSONObject> collect = alarmPlugins.values().stream().map(t -> {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("className", t.getClass().getName());
//            jsonObject.put("name", t.getName());
//            jsonObject.put("description", t.getDescription());
//            jsonObject.put("paramDescription", t.getParamDescription());
//            return jsonObject;
//        }).collect(Collectors.toList());
//        return collect;
//    }
}
