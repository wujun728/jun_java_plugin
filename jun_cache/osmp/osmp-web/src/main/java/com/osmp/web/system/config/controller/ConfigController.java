package com.osmp.web.system.config.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.config.service.ConfigService;

@Controller
@RequestMapping("config")
public class ConfigController {
    
    @Resource
    private ConfigService configService;
    

    @RequestMapping("toList")
    public String toList(){
        return "system/config/toList";
    }
    
    private Map<String, String> getConfigMap(String configName){
        Map<String, String> configMap = new HashMap<String, String>();
        if("proMap".equals(configName)){
            configMap.put("key", "proMap");
            configMap.put("name", "资源配置");
            configMap.put("size", SystemFrameWork.proMap.size() + "");
            configMap.put("type", "Map");
            configMap.put("desc", "资源配置信息");
        }else if("dictMap".equals(configName)){
            configMap.put("key", "dictMap");
            configMap.put("name", "字典配置");
            configMap.put("size", SystemFrameWork.dictMap.size() + "");
            configMap.put("type", "Map");
            configMap.put("desc", "数据字典配置信息");
        }else if("servers".equals(configName)){
            configMap.put("key", "servers");
            configMap.put("name", "服务器配置");
            configMap.put("size", SystemFrameWork.servers.size() + "");
            configMap.put("type", "List");
            configMap.put("desc", "服务器配置信息");
        }
        return configMap;
    }
    
    @RequestMapping("systemConfigList")
    @ResponseBody
    public List<Map<String, String>> systemConfigList(){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(getConfigMap("proMap"));
        list.add(getConfigMap("dictMap"));
        list.add(getConfigMap("servers"));
        return list;
    }
    
    @RequestMapping("/refreshSystemConfig/{target}")
    @ResponseBody
    public String refreshSystemConfig(@PathVariable("target")String target){
        if("proMap".equals(target)){
            SystemFrameWork.refreshPro();
        }else if("servers".equals(target)){
            SystemFrameWork.refreshServer();
        }else if("dictMap".equals(target)){
            SystemFrameWork.refreshDict();
        }else if("all".equals(target)){
            SystemFrameWork.refreshPro();
            SystemFrameWork.refreshServer();
            SystemFrameWork.refreshDict();
        }
        return "success";
    }
    
    @RequestMapping("/refreshBundleConfig/{target}")
    @ResponseBody
    public String refresh(@PathVariable("target")String target){
        boolean ok = configService.refresh(target);
        return ok ? "success" : "fail";
    }
}
