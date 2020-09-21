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
package net.ymate.platform.persistence.redis.impl;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.support.IPasswordProcessor;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.redis.IRedis;
import net.ymate.platform.persistence.redis.IRedisModuleCfg;
import net.ymate.platform.persistence.redis.RedisDataSourceCfgMeta;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/30 上午3:23
 * @version 1.0
 */
public class RedisModuleCfg implements IRedisModuleCfg {

    private String dataSourceDefaultName;

    private Map<String, RedisDataSourceCfgMeta> dataSourceCfgMetas;

    public RedisModuleCfg(YMP owner) throws Exception {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IRedis.MODULE_NAME);
//
        this.dataSourceDefaultName = StringUtils.defaultIfBlank(_moduleCfgs.get("ds_default_name"), "default");
        //
        this.dataSourceCfgMetas = new HashMap<String, RedisDataSourceCfgMeta>();
        String _dsNameStr = StringUtils.defaultIfBlank(_moduleCfgs.get("ds_name_list"), "default");
        if (StringUtils.contains(_dsNameStr, this.dataSourceDefaultName)) {
            String[] _dsNameList = StringUtils.split(_dsNameStr, "|");
            for (String _dsName : _dsNameList) {
                RedisDataSourceCfgMeta _meta = __doParserDataSourceCfgMeta(_dsName, _moduleCfgs);
                if (_meta != null) {
                    this.dataSourceCfgMetas.put(_dsName, _meta);
                }
            }
        } else {
            throw new IllegalArgumentException("The default datasource name does not match");
        }
    }

    @SuppressWarnings("unchecked")
    protected RedisDataSourceCfgMeta __doParserDataSourceCfgMeta(String dsName, Map<String, String> _moduleCfgs) throws Exception {
        RedisDataSourceCfgMeta _meta = null;
        //
        Map<String, String> _dataSourceCfgs = RuntimeUtils.keyStartsWith(_moduleCfgs, "ds." + dsName + ".");
        //
//        if (!_dataSourceCfgs.isEmpty()) {
        String _connectionType = StringUtils.defaultIfBlank(_dataSourceCfgs.get("connection_type"), "default");
        String _masterServerName = StringUtils.defaultIfBlank(_dataSourceCfgs.get("master_server_name"), "default");
        List<ServerMeta> _servers = new ArrayList<ServerMeta>();
        String[] _serverNames = StringUtils.split(StringUtils.defaultIfBlank(_dataSourceCfgs.get("server_name_list"), "default"), "|");
        Map<String, String> _tmpCfgs = null;
        if (_serverNames != null) {
            for (String _serverName : _serverNames) {
                _tmpCfgs = RuntimeUtils.keyStartsWith(_dataSourceCfgs, "server." + _serverName + ".");
                if (!_tmpCfgs.isEmpty()) {
                    ServerMeta _servMeta = new ServerMeta();
                    _servMeta.setName(_serverName);
                    _servMeta.setHost(StringUtils.defaultIfBlank(_tmpCfgs.get("host"), "localhost"));
                    _servMeta.setPort(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("port"), "6379")).toIntValue());
                    _servMeta.setTimeout(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("timeout"), "2000")).toIntValue());
                    _servMeta.setWeight(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("weight"), "1")).toIntValue());
                    _servMeta.setDatabase(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("database"), "0")).toIntValue());
                    _servMeta.setClientName(StringUtils.trimToNull(_tmpCfgs.get("client_name")));
                    _servMeta.setPassword(StringUtils.trimToNull(_tmpCfgs.get("password")));
                    //
                    boolean _pwdEncrypted = new BlurObject(_tmpCfgs.get("password_encrypted")).toBooleanValue();
                    //
                    if (_pwdEncrypted
                            && StringUtils.isNotBlank(_servMeta.getPassword())
                            && StringUtils.isNotBlank(_tmpCfgs.get("password_class"))) {
                        IPasswordProcessor _proc = ClassUtils.impl(_dataSourceCfgs.get("password_class"), IPasswordProcessor.class, this.getClass());
                        if (_proc != null) {
                            _servMeta.setPassword(_proc.decrypt(_servMeta.getPassword()));
                        }
                    }
                    //
                    _servers.add(_servMeta);
                }
            }
        }
        //
        GenericObjectPoolConfig _poolConfig = new GenericObjectPoolConfig();
        _tmpCfgs = RuntimeUtils.keyStartsWith(_dataSourceCfgs, "pool.");
        if (!_tmpCfgs.isEmpty()) {
            _poolConfig.setMinIdle(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("min_idle"), GenericObjectPoolConfig.DEFAULT_MIN_IDLE + "")).toIntValue());
            _poolConfig.setMaxIdle(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("max_idle"), GenericObjectPoolConfig.DEFAULT_MAX_IDLE + "")).toIntValue());
            _poolConfig.setMaxTotal(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("max_total"), GenericObjectPoolConfig.DEFAULT_MAX_TOTAL + "")).toIntValue());
            _poolConfig.setBlockWhenExhausted(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("block_when_exhausted"), GenericObjectPoolConfig.DEFAULT_BLOCK_WHEN_EXHAUSTED + "")).toBooleanValue());
            _poolConfig.setFairness(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("fairness"), GenericObjectPoolConfig.DEFAULT_FAIRNESS + "")).toBooleanValue());
            _poolConfig.setJmxEnabled(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("jmx_enabled"), GenericObjectPoolConfig.DEFAULT_JMX_ENABLE + "")).toBooleanValue());
            _poolConfig.setJmxNameBase(StringUtils.defaultIfBlank(_tmpCfgs.get("jmx_name_base"), GenericObjectPoolConfig.DEFAULT_JMX_NAME_BASE));
            _poolConfig.setJmxNamePrefix(StringUtils.defaultIfBlank(_tmpCfgs.get("jmx_name_prefix"), GenericObjectPoolConfig.DEFAULT_JMX_NAME_PREFIX));
            _poolConfig.setEvictionPolicyClassName(StringUtils.defaultIfBlank(_tmpCfgs.get("eviction_policy_class_name"), GenericObjectPoolConfig.DEFAULT_EVICTION_POLICY_CLASS_NAME));
            _poolConfig.setLifo(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("lifo"), GenericObjectPoolConfig.DEFAULT_LIFO + "")).toBooleanValue());
            _poolConfig.setMaxWaitMillis(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("max_wait_millis"), GenericObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS + "")).toLongValue());
            _poolConfig.setMinEvictableIdleTimeMillis(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("min_evictable_idle_time_millis"), GenericObjectPoolConfig.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS + "")).toLongValue());
            _poolConfig.setSoftMinEvictableIdleTimeMillis(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("soft_min_evictable_idle_time_millis"), GenericObjectPoolConfig.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS + "")).toLongValue());
            _poolConfig.setTestOnBorrow(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("test_on_borrow"), GenericObjectPoolConfig.DEFAULT_TEST_ON_BORROW + "")).toBooleanValue());
            _poolConfig.setTestOnReturn(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("test_on_return"), GenericObjectPoolConfig.DEFAULT_TEST_ON_RETURN + "")).toBooleanValue());
            _poolConfig.setTestOnCreate(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("test_on_create"), GenericObjectPoolConfig.DEFAULT_TEST_ON_CREATE + "")).toBooleanValue());
            _poolConfig.setTestWhileIdle(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("test_while_idle"), GenericObjectPoolConfig.DEFAULT_TEST_WHILE_IDLE + "")).toBooleanValue());
            _poolConfig.setNumTestsPerEvictionRun(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("num_tests_per_eviction_run"), GenericObjectPoolConfig.DEFAULT_NUM_TESTS_PER_EVICTION_RUN + "")).toIntValue());
            _poolConfig.setTimeBetweenEvictionRunsMillis(BlurObject.bind(StringUtils.defaultIfBlank(_tmpCfgs.get("time_between_eviction_runs_millis"), GenericObjectPoolConfig.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS + "")).toLongValue());
        }
        _meta = new RedisDataSourceCfgMeta(dsName, _connectionType, _masterServerName, _servers, _poolConfig);
//        }
        return _meta;
    }

    public String getDataSourceDefaultName() {
        return dataSourceDefaultName;
    }

    public Map<String, RedisDataSourceCfgMeta> getDataSourceCfgs() {
        return Collections.unmodifiableMap(dataSourceCfgMetas);
    }

    public RedisDataSourceCfgMeta getDefaultDataSourceCfg() {
        return dataSourceCfgMetas.get(dataSourceDefaultName);
    }

    public RedisDataSourceCfgMeta getDataSourceCfg(String name) {
        return dataSourceCfgMetas.get(name);
    }
}
