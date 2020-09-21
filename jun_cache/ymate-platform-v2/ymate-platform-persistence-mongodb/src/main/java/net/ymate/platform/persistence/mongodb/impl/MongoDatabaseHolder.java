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

import com.mongodb.client.MongoDatabase;
import net.ymate.platform.persistence.mongodb.IMongoDatabaseHolder;
import net.ymate.platform.persistence.mongodb.MongoDataSourceCfgMeta;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 下午1:10
 * @version 1.0
 */
public class MongoDatabaseHolder implements IMongoDatabaseHolder {

    private MongoDataSourceCfgMeta __cfgMeta;

    private MongoDatabase __database;

    public MongoDatabaseHolder(MongoDataSourceCfgMeta cfgMeta, MongoDatabase database) {
        __cfgMeta = cfgMeta;
        __database = database;
    }

    public MongoDataSourceCfgMeta getDataSourceCfgMeta() {
        return __cfgMeta;
    }

    public MongoDatabase getDatabase() {
        return __database;
    }
}
