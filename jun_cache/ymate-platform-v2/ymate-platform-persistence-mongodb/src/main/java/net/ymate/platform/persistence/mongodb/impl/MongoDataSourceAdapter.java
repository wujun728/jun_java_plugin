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

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import net.ymate.platform.persistence.mongodb.IMongoClientOptionsHandler;
import net.ymate.platform.persistence.mongodb.IMongoDataSourceAdapter;
import net.ymate.platform.persistence.mongodb.IMongoDatabaseHolder;
import net.ymate.platform.persistence.mongodb.MongoDataSourceCfgMeta;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/22 上午12:00
 * @version 1.0
 */
public class MongoDataSourceAdapter implements IMongoDataSourceAdapter {

    private MongoClient __mongoClient;

    private MongoDataSourceCfgMeta __cfgMeta;

    public void initialize(IMongoClientOptionsHandler optionsHandler, MongoDataSourceCfgMeta cfgMeta) throws Exception {
        __cfgMeta = cfgMeta;
        MongoClientOptions.Builder _builder = null;
        if (optionsHandler != null) {
            _builder = optionsHandler.handler(cfgMeta.getName());
        }
        if (_builder == null) {
            _builder = MongoClientOptions.builder();
        }
        if (StringUtils.isNotBlank(cfgMeta.getConnectionUrl())) {
            __mongoClient = new MongoClient(new MongoClientURI(cfgMeta.getConnectionUrl(), _builder));
        } else {
            String _username = StringUtils.trimToNull(cfgMeta.getUserName());
            String _password = StringUtils.trimToNull(cfgMeta.getPassword());
            if (_username != null && _password != null) {
                if (__cfgMeta.isPasswordEncrypted() && __cfgMeta.getPasswordClass() != null) {
                    _password = __cfgMeta.getPasswordClass().newInstance().decrypt(_password);
                }
                MongoCredential _credential = MongoCredential.createCredential(cfgMeta.getUserName(), cfgMeta.getDatabaseName(), _password == null ? null : _password.toCharArray());
                __mongoClient = new MongoClient(cfgMeta.getServers(), Collections.singletonList(_credential), _builder.build());
            } else {
                __mongoClient = new MongoClient(cfgMeta.getServers(), _builder.build());
            }
        }
    }

    public MongoDataSourceCfgMeta getDataSourceCfgMeta() {
        return __cfgMeta;
    }

    public MongoClient getMongoClient() {
        return __mongoClient;
    }

    public IMongoDatabaseHolder getDefaultDatabaseHolder() {
        return new MongoDatabaseHolder(__cfgMeta, __mongoClient.getDatabase(__cfgMeta.getDatabaseName()));
    }

    public IMongoDatabaseHolder getDatabaseHolder(String databaseName) {
        return new MongoDatabaseHolder(__cfgMeta, __mongoClient.getDatabase(databaseName));
    }

    public void destroy() {
        if (__mongoClient != null) {
            __mongoClient.close();
            __mongoClient = null;
        }
        __cfgMeta = null;
    }
}
