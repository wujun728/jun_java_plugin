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
package net.ymate.platform.persistence.jdbc.impl;

import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.support.IPasswordProcessor;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.persistence.jdbc.*;
import org.apache.commons.lang.StringUtils;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认数据库JDBC持久化模块配置类
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-29 下午2:19:18
 * @version 1.0
 */
public class DefaultModuleCfg implements IDatabaseModuleCfg {

    private String dataSourceDefaultName;

    private Map<String, DataSourceCfgMeta> dataSourceCfgMetas;

    public DefaultModuleCfg(YMP owner) throws Exception {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IDatabase.MODULE_NAME);
        //
        this.dataSourceDefaultName = StringUtils.defaultIfBlank(_moduleCfgs.get("ds_default_name"), "default");
        //
        this.dataSourceCfgMetas = new HashMap<String, DataSourceCfgMeta>();
        String _dsNameStr = StringUtils.defaultIfBlank(_moduleCfgs.get("ds_name_list"), "default");
        if (StringUtils.contains(_dsNameStr, this.dataSourceDefaultName)) {
            String[] _dsNameList = StringUtils.split(_dsNameStr, "|");
            for (String _dsName : _dsNameList) {
                DataSourceCfgMeta _meta = __doParserDataSourceCfgMeta(_dsName, _moduleCfgs);
                if (_meta != null) {
                    this.dataSourceCfgMetas.put(_dsName, _meta);
                }
            }
        } else {
            throw new IllegalArgumentException("The default datasource name does not match");
        }
    }

    /**
     * @param dsName      数据源名称
     * @param _moduleCfgs 模块配置参数映射
     * @return 分析并封装数据源配置
     * @throws Exception 可能产生的异常
     */
    @SuppressWarnings("unchecked")
    protected DataSourceCfgMeta __doParserDataSourceCfgMeta(String dsName, Map<String, String> _moduleCfgs) throws Exception {
        Map<String, String> _dataSourceCfgs = RuntimeUtils.keyStartsWith(_moduleCfgs, "ds." + dsName + ".");
        if (!_dataSourceCfgs.isEmpty()) {
            //
            DataSourceCfgMeta _meta = new DataSourceCfgMeta();
            _meta.setName(dsName);
            _meta.setConnectionUrl(_dataSourceCfgs.get("connection_url"));
            _meta.setUsername(_dataSourceCfgs.get("username"));
            // 验证必填参数
            if (StringUtils.isNotBlank(_meta.getConnectionUrl()) && StringUtils.isNotBlank(_meta.getUsername())) {
                // 基础参数
                _meta.setIsShowSQL(new BlurObject(_dataSourceCfgs.get("show_sql")).toBooleanValue());
                _meta.setIsStackTraces(new BlurObject(_dataSourceCfgs.get("stack_traces")).toBooleanValue());
                _meta.setStackTraceDepth(new BlurObject(_dataSourceCfgs.get("stack_trace_depth")).toIntValue());
                _meta.setStackTracePackage(_dataSourceCfgs.get("stack_trace_package"));
                _meta.setTablePrefix(_dataSourceCfgs.get("table_prefix"));
                // 数据源适配器
                String _adapterClassName = JDBC.DS_ADAPTERS.get(StringUtils.defaultIfBlank(_dataSourceCfgs.get("adapter_class"), "default"));
                _meta.setAdapterClass((Class<? extends IDataSourceAdapter>) ClassUtils.loadClass(_adapterClassName, this.getClass()));
                //
                // 连接和数据库类型
                try {
                    _meta.setType(JDBC.DATABASE.valueOf(StringUtils.defaultIfBlank(_dataSourceCfgs.get("type"), "").toUpperCase()));
                } catch (IllegalArgumentException e) {
                    // 通过连接字符串分析数据库类型
                    String _connUrl = URI.create(_meta.getConnectionUrl()).toString();
                    String[] _type = StringUtils.split(_connUrl, ":");
                    if (_type != null && _type.length > 0) {
                        if (_type[1].equals("microsoft")) {
                            _type[1] = "sqlserver";
                        }
                        _meta.setType(JDBC.DATABASE.valueOf(_type[1].toUpperCase()));
                    }
                }
                //
                _meta.setDialectClass(_dataSourceCfgs.get("dialect_class"));
                _meta.setDriverClass(StringUtils.defaultIfBlank(_dataSourceCfgs.get("driver_class"), JDBC.DB_DRIVERS.get(_meta.getType())));
                _meta.setPassword(_dataSourceCfgs.get("password"));
                _meta.setIsPasswordEncrypted(new BlurObject(_dataSourceCfgs.get("password_encrypted")).toBooleanValue());
                //
                if (_meta.isPasswordEncrypted()
                        && StringUtils.isNotBlank(_meta.getPassword())
                        && StringUtils.isNotBlank(_dataSourceCfgs.get("password_class"))) {
                    _meta.setPasswordClass((Class<? extends IPasswordProcessor>) ClassUtils.loadClass(_dataSourceCfgs.get("password_class"), this.getClass()));
                }
                //
                return _meta;
            }
        }
        return null;
    }

    public String getDataSourceDefaultName() {
        return dataSourceDefaultName;
    }

    public Map<String, DataSourceCfgMeta> getDataSourceCfgs() {
        return Collections.unmodifiableMap(dataSourceCfgMetas);
    }

    public DataSourceCfgMeta getDefaultDataSourceCfg() {
        return dataSourceCfgMetas.get(dataSourceDefaultName);
    }

    public DataSourceCfgMeta getDataSourceCfg(String name) {
        return dataSourceCfgMetas.get(name);
    }
}
