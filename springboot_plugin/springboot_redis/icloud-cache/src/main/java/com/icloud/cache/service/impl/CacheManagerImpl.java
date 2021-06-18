/**
 *
 */
package com.icloud.cache.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.icloud.cache.api.CacheAPI;
import com.icloud.cache.config.RedisConfig;
import com.icloud.cache.constants.CacheConstants;
import com.icloud.cache.entity.CacheBean;
import com.icloud.cache.service.ICacheManager;
import com.icloud.cache.vo.CacheTree;

/**
 * 解决问题：
 *
 * @author Wujun
 * @version 1.0
 * @date 2018年5月3日
 * @since 1.7
 */
@Service
public class CacheManagerImpl implements ICacheManager {
    @Autowired
    private Environment env;
    @Autowired
    private CacheAPI cacheAPI;
    @Autowired
    private RedisConfig redisConfig;
    /**
     *
     */
    public CacheManagerImpl() {

    }

    @Override
    public void removeAll() {
        cacheAPI.removeByPre(redisConfig.getSysName());
    }

    @Override
    public void remove(String key) {
        cacheAPI.remove(key);
    }

    @Override
    public void remove(List<CacheBean> caches) {
        String[] keys = new String[caches.size()];
        int i = 0;
        for (CacheBean cache : caches) {
            keys[i] = cache.getKey();
            i++;
        }
        cacheAPI.remove(keys);
    }

    @Override
    public void removeByPre(String pre) {
        if (!pre.contains(redisConfig.getSysName())) {
            pre = redisConfig.getSysName()+ ":" + pre+"*";
        }
        cacheAPI.removeByPre(pre);
    }

    @Override
    public List<CacheTree> getAll() {
        List<CacheBean> caches = cacheAPI.listAll();
        List<CacheTree> cts = toTree(caches);
        return cts;
    }

    /**
     * @param caches
     * @return
     * @author Wujun
     * @date 2018年5月11日
     */
    private List<CacheTree> toTree(List<CacheBean> caches) {
        List<CacheTree> result = new ArrayList<CacheTree>();
        Set<CacheTree> cts = new HashSet<CacheTree>();
        CacheTree ct = new CacheTree();
        for (CacheBean cache : caches) {
            String[] split = cache.getKey().split(":");
            for (int i = split.length - 1; i > 0; i--) {
                if (i == split.length - 1) {
                    ct = new CacheTree(cache);
                } else {
                    ct = new CacheTree();
                }
                if (i - 1 >= 0) {
                    ct.setId(split[i]);
                    ct.setParentId(split[i - 1].endsWith(redisConfig.getSysName()) ? "-1" : split[i - 1]);
                }
                if (split.length == 2) {
                    cts.remove(ct);
                }
                cts.add(ct);
            }
        }
        result.addAll(cts);
        return result;
    }

    @Override
    public List<CacheTree> getByPre(String pre) {
        if (StringUtils.isBlank(pre))
            return getAll();
        if (!pre.contains(redisConfig.getSysName())) {
            pre = redisConfig.getSysName() + "*" + pre;
        }
        return toTree(cacheAPI.getCacheBeanByPre(pre));
    }

    public CacheAPI getCacheAPI() {
        return cacheAPI;
    }

    public void setCacheAPI(CacheAPI cacheAPI) {
        this.cacheAPI = cacheAPI;
    }

    @Override
    public void update(String key, int hour) {
        String value = cacheAPI.get(key);
        cacheAPI.set(key, value, hour * 60);
    }

    @Override
    public void update(List<CacheBean> caches, int hour) {
        for (CacheBean cache : caches) {
            String value = cacheAPI.get(cache.getKey());
            cacheAPI.set(cache.getKey(), value, hour);
        }
    }

    @Override
    public String get(String key) {
        return cacheAPI.get(key);
    }
}
