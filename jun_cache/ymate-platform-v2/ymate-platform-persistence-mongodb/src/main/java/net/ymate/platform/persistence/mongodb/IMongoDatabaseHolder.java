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

import com.mongodb.client.MongoDatabase;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 上午11:45
 * @version 1.0
 */
public interface IMongoDatabaseHolder {

    /**
     * @return 获取数据源名称
     */
    MongoDataSourceCfgMeta getDataSourceCfgMeta();

    /**
     * @return 获取数据库对象
     */
    MongoDatabase getDatabase();
}
