package com.osmp.web.system.cache.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.osmp.web.core.tools.HttpClientWrapper;
import com.osmp.web.system.cache.entity.CacheDefined;
import com.osmp.web.system.cache.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {

    @Resource
    HttpClientWrapper clientWrapper;

    @Override
    public void open() {
        clientWrapper.get("http://192.168.2.206:8181/cxf/config/refresh/cache_open");
    }

    @Override
    public void close() {
        clientWrapper.get("http://192.168.2.206:8181/cxf/config/refresh/cache_close");
    }

    @Override
    public String getCacheInfo() {
        String string = clientWrapper.get("http://192.168.2.206:8181/cxf/config/getdata/cache_info");
        Map<String, String> result = new HashMap<String, String>(4);
        result.put("size", "0");
        result.put("memorySize", "0");
        result.put("hitCount", "0");
        result.put("missCount", "0");

        return StringUtils.isBlank(string) || "ok".equals(string) ? JSON.toJSONString(result) : string;
    }

    @Override
    public String getCacheList() {
        String string = clientWrapper.get("http://192.168.2.206:8181/cxf/config/getdata/cache_getdefine");
        return string;
    }

    @Override
    public String getCacheItem(Map<String, String> params) {
        String url = "http://192.168.2.206:8181/cxf/config/getdata/cache_item";
        if (params != null && !params.isEmpty()) {
            Set<Entry<String, String>> entrySet = params.entrySet();
            Iterator<Entry<String, String>> iterator = entrySet.iterator();
            Entry<String, String> next;
            String key;
            String value;
            url += "?";
            while (iterator.hasNext()) {
                next = iterator.next();
                key = next.getKey();
                value = next.getValue();
                if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                    url += key + "=" + value;
                }
            }
        }
        String string = clientWrapper.get(url);
        return string;
    }

    @Override
    public String updateCache(CacheDefined cacheDefined) {
        if (cacheDefined == null || StringUtils.isBlank(cacheDefined.getId())) {
            return "";
        }

        String string = clientWrapper.get("http://192.168.2.206:8181/cxf/config/getdata/cache_update?id="
                + cacheDefined.getId() + "&state=" + cacheDefined.getState() + "&timeToIdle="
                + cacheDefined.getTimeToIdle() + "&timeToLive=" + cacheDefined.getTimeToLive());
        return string;
    }

    @Override
    public String getCacheById(String cacheId) {

        return null;
    }

    @Override
    public String deleteCache(String key) {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        String url = "http://192.168.2.206:8181/cxf/config/getdata/cache_remove?keys=" + key;
        String string = clientWrapper.get(url);
        return string;
    }

}
