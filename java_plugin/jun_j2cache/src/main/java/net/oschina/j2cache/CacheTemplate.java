package net.oschina.j2cache;

import net.oschina.j2cache.utils.CacheCustoms;
import net.oschina.j2cache.utils.StrExtUtils;

import java.util.List;

/**
 * 缓存对外开发接口
 *
 * @author FY
 */
public class CacheTemplate extends CacheAbstractTemplate {

    @Override
    public CacheObject get(String region, String key) {
        CacheObject co = new CacheObject();
        co.setRegion(region);
        co.setKey(key);

        if (!StrExtUtils.isNullOrEmpty(region) && !StrExtUtils.isNullOrEmpty(key)) {
            Object object = factory.getCache(CacheCustoms.CACHE_LV_1, region, true, (CacheExpiredListener) getBroadcastChannel()).get(key);

            // 一级缓存中没有找到，到二级中继续查询
            if (object == null && factory.isOpenSecondCache()) {
                object = factory.getCache(CacheCustoms.CACHE_LV_2, region, true, null).get(key);
                co.setLv(CacheCustoms.CACHE_LV_2);
            } else {
                co.setLv(CacheCustoms.CACHE_LV_1);
            }

            // 缓存数据
            if (object != null) {
                co.setUp((CacheBox) object);
            }

        }

        return co;
    }

    @Override
    public void set(String region, String key, Object value) {
        set(region, key, value, 0);
    }

    @Override
    public void set(String region, String key, Object value, Integer expired) {
        if (!StrExtUtils.isNullOrEmpty(region) && !StrExtUtils.isNullOrEmpty(key)) {
            if (value == null) {
                // 删除该key下的所有数据
                evict(region, key);
            } else {
                // 1.清除原有的一级缓存数据
                if (factory.isUseCluster())
                    sendCmdBroadcast(CacheCustoms.OPT_DELTED_KEY, region, key);
                // 2.添加缓存数据
                CacheBox cb = new CacheBox(value, expired);
                factory.getCache(CacheCustoms.CACHE_LV_1, region, true, (CacheExpiredListener) getBroadcastChannel()).put(key, cb);
                if (factory.isOpenSecondCache())
                    factory.getCache(CacheCustoms.CACHE_LV_2, region, true, null).put(key, cb);
            }
        }
    }

    @Override
    public void evict(String region, String key) {
        // 1.删除一级缓存
        factory.getCache(CacheCustoms.CACHE_LV_1, region, true, (CacheExpiredListener) getBroadcastChannel()).evict(key);
        // 2.删除二级缓存
        if (factory.isOpenSecondCache())
            factory.getCache(CacheCustoms.CACHE_LV_2, region, true, null).evict(key);
        // 3.广播删除消息
        if (factory.isUseCluster())
            sendCmdBroadcast(CacheCustoms.OPT_DELTED_KEY, region, key);
    }

    @Override
    public void batchEvict(String region, List keys) {
        // 1.删除一级缓存
        factory.getCache(CacheCustoms.CACHE_LV_1, region, true, (CacheExpiredListener) getBroadcastChannel()).evict(keys);
        // 2.删除二级缓存
        if (factory.isOpenSecondCache())
            factory.getCache(CacheCustoms.CACHE_LV_2, region, true, null).evict(keys);
        // 3.广播删除消息
        if (factory.isUseCluster())
            sendCmdBroadcast(CacheCustoms.OPT_DELTED_KEY, region, keys);
    }

    @Override
    public void clear(String region) {
        // 1.清除一级缓存
        factory.getCache(CacheCustoms.CACHE_LV_1, region, true, (CacheExpiredListener) getBroadcastChannel()).clear();
        // 2.清除二级缓存
        if (factory.isOpenSecondCache())
            factory.getCache(CacheCustoms.CACHE_LV_2, region, true, null).clear();
    }

    @Override
    public List keys(String region) {
        return factory.getCache(CacheCustoms.CACHE_LV_1, region, true, (CacheExpiredListener) getBroadcastChannel()).keys();
    }

}
