/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.redis;

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.persistence.redis.impl.RedisDataSourceAdapter;
import net.ymate.platform.persistence.redis.impl.RedisModuleCfg;
import net.ymate.platform.persistence.redis.impl.RedisSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/30 上午3:14
 * @version 1.0
 */
@Module
public class Redis implements IModule, IRedis {

    public static final Version VERSION = new Version(2, 0, 3, Redis.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(Redis.class);

    private static volatile IRedis __instance;

    private YMP __owner;

    private IRedisModuleCfg __moduleCfg;

    private Map<String, IRedisDataSourceAdapter> __dataSourceCaches;

    private boolean __inited;

    /**
     * @return 返回默认Redis模块管理器实例对象
     */
    public static IRedis get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Redis.class);
                }
            }
        }
        return __instance;
    }

    /**
     * @param owner YMP框架管理器实例
     * @return 返回指定YMP框架管理器容器内的Redis模块管理器实例
     */
    public static IRedis get(YMP owner) {
        return owner.getModule(Redis.class);
    }

    public String getName() {
        return IRedis.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-platform-persistence-redis-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new RedisModuleCfg(owner);
            //
            __dataSourceCaches = new HashMap<String, IRedisDataSourceAdapter>();
            for (RedisDataSourceCfgMeta _meta : __moduleCfg.getDataSourceCfgs().values()) {
                IRedisDataSourceAdapter _adapter = new RedisDataSourceAdapter();
                _adapter.initialize(_meta);
                // 将数据源适配器添加到缓存
                __dataSourceCaches.put(_meta.getName(), _adapter);
            }
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public YMP getOwner() {
        return __owner;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            for (IRedisDataSourceAdapter _adapter : __dataSourceCaches.values()) {
                _adapter.destroy();
            }
            __dataSourceCaches = null;
            __moduleCfg = null;
            __owner = null;
        }
    }

    public IRedisModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    public IRedisDataSourceAdapter getDefaultDataSourceAdapter() throws Exception {
        return __dataSourceCaches.get(__moduleCfg.getDataSourceDefaultName());
    }

    public IRedisDataSourceAdapter getDataSourceAdapter(String dataSourceName) throws Exception {
        return __dataSourceCaches.get(dataSourceName);
    }

    public <T> T openSession(IRedisSessionExecutor<T> executor) throws Exception {
        IRedisSession _session = new RedisSession(getDefaultDataSourceAdapter().getCommandsHolder());
        try {
            return executor.execute(_session);
        } finally {
            _session.close();
        }
    }

    public <T> T openSession(IRedisCommandsHolder commandsHolder, IRedisSessionExecutor<T> executor) throws Exception {
        IRedisSession _session = new RedisSession(commandsHolder);
        try {
            return executor.execute(_session);
        } finally {
            _session.close();
        }
    }
}
