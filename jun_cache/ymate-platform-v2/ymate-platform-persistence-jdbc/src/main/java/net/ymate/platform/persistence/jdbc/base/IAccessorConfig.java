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
 * 访问器配置接口，方便对访问器的功能扩展
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30上午09:51:01
 * @version 1.0
 */
public interface IAccessorConfig {

    /**
     * @param conn 连接对象
     * @return 创建自定义Statement对象，默认null
     * @throws Exception 可能产生的异常
     */
    Statement getStatement(Connection conn) throws Exception;

    /**
     * @param conn 连接对象
     * @param sql  SQL语句
     * @return 创建自定义 CallableStatement对象，默认null
     * @throws Exception 可能产生的异常
     */
    CallableStatement getCallableStatement(Connection conn, String sql) throws Exception;

    /**
     * @param conn 连接对象
     * @param sql  SQL语句
     * @return 创建自定义PerparedStatement对象，默认null
     * @throws Exception 可能产生的异常
     */
    PreparedStatement getPreparedStatement(Connection conn, String sql) throws Exception;

    /**
     * Statement对象执行之前调用
     *
     * @param context 访问器配置事件上下文对象
     * @throws Exception 可能产生的异常
     */
    void beforeStatementExecution(AccessorEventContext context) throws Exception;

    /**
     * Statement对象执行之后调用
     *
     * @param context 访问器配置事件上下文对象
     * @throws Exception 可能产生的异常
     */
    void afterStatementExecution(AccessorEventContext context) throws Exception;

    /**
     * 向驱动程序提供关于方向的提示，在使用此 Statement 对象创建的 ResultSet 对象中将按该方向处理行。默认值为 ResultSet.FETCH_FORWARD。
     * 注意，此方法为此 Statement 对象生成的结果集合设置默认获取方向。每个结果集合都具有它自己用于获取和设置其自身获取方向的方法。
     * 取值范围：ResultSet.FETCH_FORWARD、ResultSet.FETCH_REVERSE 和 ResultSet.FETCH_UNKNOWN
     *
     * @return 获取方向
     */
    int getFetchDirection();

    /**
     * 为 JDBC 驱动程序提供一个提示，它提示此 Statement 生成的 ResultSet 对象需要更多行时应该从数据库获取的行数。
     * 指定的行数仅影响使用此语句创建的结果集合。如果指定的值为 0，则忽略该提示。默认值为 0。
     *
     * @return 获取行数
     */
    int getFetchSize();

    /**
     * 设置此 Statement 对象生成的 ResultSet 对象中字符和二进制列值可以返回的最大字节数限制。 此限制仅应用于
     * BINARY、VARBINARY、LONGVARBINARY、CHAR、VARCHAR、NCHAR、NVARCHAR、LONGNVARCHAR 和
     * LONGVARCHAR 字段。如果超过了该限制，则直接丢弃多出的数据。为了获得最大的可移植性，应该使用大于 256 的值。
     * 以字节为单位的新列大小限制；0 表示没有任何限制
     *
     * @return 字段最大数
     */
    int getMaxFieldSize();

    /**
     * 将此 Statement 对象生成的所有 ResultSet 对象可以包含的最大行数限制设置为给定数。如果超过了该限制，则直接撤消多出的行。
     * 新的最大行数限制；0 表示没有任何限制
     *
     * @return 最大行数
     */
    int getMaxRows();

    /**
     * 将驱动程序等待 Statement 对象执行的秒数设置为给定秒数。如果超过该限制，则抛出 SQLException。 JDBC
     * 驱动程序必须将此限制应用于 execute、executeQuery 和 executeUpdate 方法。 JDBC
     * 驱动程序实现也可以将此限制应用于 ResultSet 方法（有关详细信息，请参考驱动程序供应商文档）。
     * 以秒为单位的查询超时限制；0 表示没有任何限制
     *
     * @return 查询超时时间
     */
    int getQueryTimeout();
}
