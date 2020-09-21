package com.osmp.web.system.cache.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.osmp.web.system.cache.entity.CacheDefined;
import com.osmp.web.system.cache.entity.CacheInfo;
import com.osmp.web.system.cache.service.CacheService;

@Controller
@RequestMapping("cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping("/cacheOption")
    @ResponseBody
    public Map<String, Object> openCache(@RequestParam(value = "type", required = true) String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if ("close".equals(type)) {
                cacheService.close();
            } else if ("open".equals(type)) {
                cacheService.open();
            }
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "删除失败");
        }

        return map;
    }

    @RequestMapping("/toList")
    public ModelAndView toList() {
    	String res = cacheService.getCacheInfo();
        return new ModelAndView("system/cache/cachelist", "cacheInfo", JSON.parseObject(res,
                CacheInfo.class));
    }

    @RequestMapping("/cacheList")
    @ResponseBody
    public String cacheList() {
        String cacheDefinedLst = cacheService.getCacheList();
        return cacheDefinedLst;
    }

    @RequestMapping("/toItem")
    public ModelAndView toItem(@RequestParam(value = "id", required = false) String cacheId) {
        Map<String, String> modal = new HashMap<String, String>(2);
        modal.put("cacheId", cacheId);
        modal.put("divName", "cacheDetail" + UUID.randomUUID().toString());

        return new ModelAndView("system/cache/cachedetail", "modal", modal);
    }

    @RequestMapping("/itemList")
    @ResponseBody
    public String itemList(@RequestParam(value = "id", required = false) String cacheId) {
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtils.isBlank(cacheId)) {
            map.put("keys", cacheId);
        }
        return cacheService.getCacheItem(map);
    }

    @RequestMapping("/toEdit")
    public ModelAndView toEdit(@RequestParam(value = "id") String cacheId,
            @RequestParam(value = "timeToLive") Integer timeToLive,
            @RequestParam(value = "timeToIdle") Integer timeToIdle, @RequestParam(value = "state") Integer state) {
        CacheDefined cd = new CacheDefined();
        cd.setId(cacheId);
        cd.setState(state);
        cd.setTimeToLive(timeToLive);
        cd.setTimeToIdle(timeToIdle);

        return new ModelAndView("system/cache/cacheEdit", "cacheDefined", cd);
    }

    @RequestMapping("/deleteCache")
    @ResponseBody
    public Map<String, Object> deleteCache(
            @RequestParam(value = "id", required = false, defaultValue = "-1") String itemKey) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            cacheService.deleteCache(itemKey);
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "删除失败");
        }

        return map;
    }

    @RequestMapping("/updateCache")
    @ResponseBody
    public Map<String, Object> updateCache(CacheDefined cacheDefined) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            cacheService.updateCache(cacheDefined);
            map.put("success", true);
            map.put("msg", "更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "更新失败");
        }

        return map;
    }

}
