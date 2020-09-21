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

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/30 上午11:52
 * @version 1.0
 */
public class RedisDataSourceCfgMeta {

    private String __dataSourceName;

    private String __connectionType;

    private String __masterServerName;

    private Map<String, IRedisModuleCfg.ServerMeta> __servers;

    private GenericObjectPoolConfig __poolConfig;

    public RedisDataSourceCfgMeta(String dataSourceName, String connectionType, String masterServerName, List<IRedisModuleCfg.ServerMeta> servers, GenericObjectPoolConfig poolConfig) {
        this.__dataSourceName = dataSourceName;
        this.__connectionType = connectionType;
        this.__masterServerName = masterServerName;
        this.__servers = new HashMap<String, IRedisModuleCfg.ServerMeta>();
        for (IRedisModuleCfg.ServerMeta _meta : servers) {
            this.__servers.put(_meta.getName(), _meta);
        }
        this.__poolConfig = poolConfig;
    }

    public String getName() {
        return __dataSourceName;
    }

    public String getConnectionType() {
        return __connectionType;
    }

    public String getMasterServerName() {
        return __masterServerName;
    }

    public IRedisModuleCfg.ServerMeta getMasterServerMeta() {
        return __servers.get(__masterServerName);
    }

    public Map<String, IRedisModuleCfg.ServerMeta> getServers() {
        return __servers;
    }

    public GenericObjectPoolConfig getPoolConfig() {
        return __poolConfig;
    }
}
