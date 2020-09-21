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

import com.mongodb.ServerAddress;
import net.ymate.platform.core.support.IPasswordProcessor;

import java.util.Collections;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 下午1:52
 * @version 1.0
 */
public class MongoDataSourceCfgMeta {

    /**
     * 当前数据源名称
     */
    private String name;

    /**
     * 集合前缀名称，可选参数，默认为空
     */
    private String collectionPrefix;

    /**
     * 服务器主机连接字符串，可选参数，若提供此参数则下面的servers等参数就不在需要提供
     */
    private String connectionUrl;

    /**
     * 服务器主机集合
     */
    private List<ServerAddress> servers;

    /**
     * 用户名称，根据服务器-auth配置选填
     */
    private String userName;

    /**
     * 用户密码，根据服务器-auth配置选填
     */
    private String password;

    /**
     * 默认数据库名称，必填参数
     */
    private String databaseName;

    /**
     * 访问密码是否已加密，默认为false
     */
    private boolean isPasswordEncrypted;

    /**
     * 密码处理器，可选参数，用于对已加密访问密码进行解密，默认为空
     */
    private Class<? extends IPasswordProcessor> passwordClass;

    public MongoDataSourceCfgMeta(String name, String collectionPrefix, String connectionUrl, String databaseName) {
        this.name = name;
        this.collectionPrefix = collectionPrefix;
        this.connectionUrl = connectionUrl;
        this.databaseName = databaseName;
    }

    public MongoDataSourceCfgMeta(String name,
                                  String collectionPrefix,
                                  List<ServerAddress> servers,
                                  String userName,
                                  String password,
                                  String databaseName,
                                  boolean isPasswordEncrypted,
                                  Class<? extends IPasswordProcessor> passwordClass) {
        this.name = name;
        this.collectionPrefix = collectionPrefix;
        this.servers = servers;
        this.userName = userName;
        this.password = password;
        this.databaseName = databaseName;
        this.isPasswordEncrypted = isPasswordEncrypted;
        this.passwordClass = passwordClass;
    }

    public String getName() {
        return name;
    }

    public String getCollectionPrefix() {
        return collectionPrefix;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public List<ServerAddress> getServers() {
        return Collections.unmodifiableList(servers);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public boolean isPasswordEncrypted() {
        return isPasswordEncrypted;
    }

    public Class<? extends IPasswordProcessor> getPasswordClass() {
        return passwordClass;
    }
}
