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
package net.ymate.platform.persistence.mongodb;

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.persistence.mongodb.impl.MongoDataSourceAdapter;
import net.ymate.platform.persistence.mongodb.impl.MongoGridFSSession;
import net.ymate.platform.persistence.mongodb.impl.MongoModuleCfg;
import net.ymate.platform.persistence.mongodb.impl.MongoSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 上午9:24
 * @version 1.0
 */
@Module
public class MongoDB implements IModule, IMongo {

    public static final Version VERSION = new Version(2, 0, 3, MongoDB.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private static final Log _LOG = LogFactory.getLog(MongoDB.class);

    private static volatile IMongo __instance;

    private YMP __owner;

    private IMongoModuleCfg __moduleCfg;

    private Map<String, IMongoDataSourceAdapter> __dataSourceCaches;

    private boolean __inited;

    /**
     * @return 返回默认MongoDB模块管理器实例对象
     */
    public static IMongo get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(MongoDB.class);
                }
            }
        }
        return __instance;
    }

    /**
     * @param owner YMP框架管理器实例
     * @return 返回指定YMP框架管理器容器内的MongoDB模块管理器实例
     */
    public static IMongo get(YMP owner) {
        return owner.getModule(MongoDB.class);
    }

    public String getName() {
        return IMongo.MODULE_NAME;
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing ymate-platform-persistence-mongodb-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new MongoModuleCfg(owner);
            //
            __dataSourceCaches = new HashMap<String, IMongoDataSourceAdapter>();
            for (MongoDataSourceCfgMeta _meta : __moduleCfg.getDataSourceCfgs().values()) {
                IMongoDataSourceAdapter _adapter = new MongoDataSourceAdapter();
                _adapter.initialize(__moduleCfg.getClientOptionsHandler(), _meta);
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

    public IMongoModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    public IMongoDataSourceAdapter getDefaultDataSourceAdapter() throws Exception {
        return __dataSourceCaches.get(__moduleCfg.getDataSourceDefaultName());
    }

    public IMongoDataSourceAdapter getDataSourceAdapter(String dataSourceName) throws Exception {
        return __dataSourceCaches.get(dataSourceName);
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            for (IMongoDataSourceAdapter _adapter : __dataSourceCaches.values()) {
                _adapter.destroy();
            }
            __dataSourceCaches = null;
            __moduleCfg = null;
            __owner = null;
        }
    }

    public <T> T openSession(IMongoSessionExecutor<T> executor) throws Exception {
        return openSession(getDefaultDataSourceAdapter().getDefaultDatabaseHolder(), executor);
    }

    public <T> T openSession(IMongoDatabaseHolder databaseHolder, IMongoSessionExecutor<T> executor) throws Exception {
        IMongoSession _session = new MongoSession(databaseHolder);
        try {
            return executor.execute(_session);
        } finally {
            _session.close();
        }
    }

    public <T> T openSession(IGridFSSessionExecutor<T> executor) throws Exception {
        return openSession(getDefaultDataSourceAdapter(), null, executor);
    }

    public <T> T openSession(String bucketName, IGridFSSessionExecutor<T> executor) throws Exception {
        return openSession(getDefaultDataSourceAdapter(), bucketName, executor);
    }

    public <T> T openSession(IMongoDataSourceAdapter dataSourceAdapter, IGridFSSessionExecutor<T> executor) throws Exception {
        return openSession(dataSourceAdapter, null, executor);
    }

    public <T> T openSession(IMongoDataSourceAdapter dataSourceAdapter, String bucketName, IGridFSSessionExecutor<T> executor) throws Exception {
        IGridFSSession _gridFS = new MongoGridFSSession(dataSourceAdapter, bucketName);
        try {
            return executor.execute(_gridFS);
        } finally {
            _gridFS.close();
        }
    }
}
