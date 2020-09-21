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
package net.ymate.platform.persistence.mongodb.impl;

import com.mongodb.ServerAddress;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.support.IPasswordProcessor;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.IMongoClientOptionsHandler;
import net.ymate.platform.persistence.mongodb.IMongoModuleCfg;
import net.ymate.platform.persistence.mongodb.MongoDataSourceCfgMeta;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/22 上午12:42
 * @version 1.0
 */
public class MongoModuleCfg implements IMongoModuleCfg {

    private String dataSourceDefaultName;

    private IMongoClientOptionsHandler clientOptionsHandler;

    private Map<String, MongoDataSourceCfgMeta> dataSourceCfgMetas;

    public MongoModuleCfg(YMP owner) throws Exception {
        Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IMongo.MODULE_NAME);
        //
        this.dataSourceDefaultName = StringUtils.defaultIfBlank(_moduleCfgs.get("ds_default_name"), "default");
        this.clientOptionsHandler = ClassUtils.impl(_moduleCfgs.get("ds_options_handler_class"), IMongoClientOptionsHandler.class, this.getClass());
        //
        this.dataSourceCfgMetas = new HashMap<String, MongoDataSourceCfgMeta>();
        String _dsNameStr = StringUtils.defaultIfBlank(_moduleCfgs.get("ds_name_list"), "default");
        if (StringUtils.contains(_dsNameStr, this.dataSourceDefaultName)) {
            String[] _dsNameList = StringUtils.split(_dsNameStr, "|");
            for (String _dsName : _dsNameList) {
                MongoDataSourceCfgMeta _meta = __doParserDataSourceCfgMeta(_dsName, _moduleCfgs);
                if (_meta != null) {
                    this.dataSourceCfgMetas.put(_dsName, _meta);
                }
            }
        } else {
            throw new IllegalArgumentException("The default datasource name does not match");
        }
    }

    @SuppressWarnings("unchecked")
    protected MongoDataSourceCfgMeta __doParserDataSourceCfgMeta(String dsName, Map<String, String> _moduleCfgs) throws Exception {
        MongoDataSourceCfgMeta _meta = null;
        //
        Map<String, String> _dataSourceCfgs = new HashMap<String, String>();
        for (Map.Entry<String, String> _cfgEntry : _moduleCfgs.entrySet()) {
            String _key = _cfgEntry.getKey();
            String _prefix = "ds." + dsName + ".";
            if (StringUtils.startsWith(_key, _prefix)) {
                String _cfgKey = StringUtils.substring(_key, _prefix.length());
                _dataSourceCfgs.put(_cfgKey, _cfgEntry.getValue());
            }
        }
        //
        if (!_dataSourceCfgs.isEmpty()) {
            String _connectionUrl = StringUtils.trimToNull(_dataSourceCfgs.get("connection_url"));
            if (_connectionUrl != null) {
                _meta = new MongoDataSourceCfgMeta(dsName,
                        _dataSourceCfgs.get("collection_prefix"),
                        _connectionUrl,
                        _dataSourceCfgs.get("database_name"));
            } else {
                List<ServerAddress> _servers = new ArrayList<ServerAddress>();
                String[] _serversArr = StringUtils.split(_dataSourceCfgs.get("servers"), "|");
                if (_serversArr != null) {
                    for (String _serverStr : _serversArr) {
                        String[] _server = StringUtils.split(_serverStr, ":");
                        if (_server.length > 1) {
                            _servers.add(new ServerAddress(_server[0], Integer.parseInt(_server[1])));
                        } else {
                            _servers.add(new ServerAddress(_server[0]));
                        }
                    }
                }
                //
                boolean _isPwdEncrypted = new BlurObject(_dataSourceCfgs.get("password_encrypted")).toBooleanValue();
                Class<? extends IPasswordProcessor> _passwordClass = null;
                if (_isPwdEncrypted) {
                    _passwordClass = (Class<? extends IPasswordProcessor>) ClassUtils.loadClass(_dataSourceCfgs.get("password_class"), this.getClass());
                }
                _meta = new MongoDataSourceCfgMeta(dsName,
                        _dataSourceCfgs.get("collection_prefix"),
                        _servers,
                        _dataSourceCfgs.get("username"),
                        _dataSourceCfgs.get("password"),
                        _dataSourceCfgs.get("database_name"),
                        _isPwdEncrypted,
                        _passwordClass);
            }
        }
        return _meta;
    }

    public String getDataSourceDefaultName() {
        return dataSourceDefaultName;
    }

    public IMongoClientOptionsHandler getClientOptionsHandler() {
        return clientOptionsHandler;
    }

    public Map<String, MongoDataSourceCfgMeta> getDataSourceCfgs() {
        return Collections.unmodifiableMap(dataSourceCfgMetas);
    }

    public MongoDataSourceCfgMeta getDefaultDataSourceCfg() {
        return dataSourceCfgMetas.get(dataSourceDefaultName);
    }

    public MongoDataSourceCfgMeta getDataSourceCfg(String name) {
        return dataSourceCfgMetas.get(name);
    }
}
