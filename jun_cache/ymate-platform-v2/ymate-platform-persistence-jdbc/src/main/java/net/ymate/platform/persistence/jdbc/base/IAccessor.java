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
package net.ymate.platform.persistence.jdbc.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * 访问器接口定义，用于提供Statement对象生成方式
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30 上午09:47:06
 * @version 1.0
 */
public interface IAccessor {

    /**
     * @param conn 连接对象
     * @return 使用Statement方式进行数据库访问操作，用于直接使用SQL文；
     * @throws Exception 可能产生的异常
     */
    Statement getStatement(Connection conn) throws Exception;

    /**
     * @param conn 访问数据库的连接对象
     * @param sql  预执行的SQL语句
     * @return 使用PerparedStatement(参数化)方式进行数据库访问操作，用于直接使用SQL文
     * @throws Exception 可能产生的异常
     */
    PreparedStatement getPreparedStatement(Connection conn, String sql) throws Exception;

    /**
     * @param conn 访问数据库的连接对象
     * @param sql  预执行的SQL语句
     * @return 使用CallableStatement方式进行数据库访问操作，用于访问存储过程
     * @throws Exception 可能产生的异常
     */
    CallableStatement getCallableStatement(Connection conn, String sql) throws Exception;

    /**
     * @return 获取访问器配置对象
     */
    IAccessorConfig getAccessorConfig();
}
