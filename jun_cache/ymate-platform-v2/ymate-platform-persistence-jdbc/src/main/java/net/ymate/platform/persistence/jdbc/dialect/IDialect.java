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
package net.ymate.platform.persistence.jdbc.dialect;

import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.IShardingable;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库方言接口定义，用于适配不同数据库特性
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30 下午01:02:44
 * @version 1.0
 */
public interface IDialect {

    /**
     * @return 返回方言名称
     */
    String getName();

    /**
     * @param origin 字段或表名称
     * @return 返回添加引用标识符的字段或表名称，防止字段或表名与关键字冲突，此接口方法默认实现按原样返回
     */
    String wrapIdentifierQuote(String origin);

    /**
     * @param prefix       数据表名称前缀
     * @param entityMeta   数据实体属性描述对象
     * @param shardingable 分片(表)参数对象
     * @return 返回完整的数据表名称，并根据数据库特性决定是否添加引用标识符
     */
    String buildTableName(String prefix, EntityMeta entityMeta, IShardingable shardingable);

    /**
     * @param statement Statement对象
     * @return 返回主键值（按 long 类型返回），采用JDBC Statement对象获取自动生成的主键（仅处理单主键）
     * @throws SQLException 可能产生的异常
     */
    Object[] getGeneratedKey(Statement statement) throws SQLException;

    /**
     * @param sequenceName 序列名称
     * @return 返回获取下一序列值的SQL语句
     */
    String getSequenceNextValSql(String sequenceName);

    /**
     * @param originSql 原SQL语句
     * @param page      页号
     * @param pageSize  每页记录数
     * @return 返回分页SQL语句
     */
    String buildPagedQuerySQL(String originSql, int page, int pageSize);

    /**
     * @param entityClass  实体模型类
     * @param prefix       实体名称前缀
     * @param shardingable 分片(表)参数对象
     * @return 返回创建实体数据表SQL语句
     */
    String buildCreateSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable);

    /**
     * @param entityClass  实体模型类
     * @param prefix       实体名称前缀
     * @param shardingable 分片(表)参数对象
     * @return 返回删除实体数据表SQL语句
     */
    String buildDropSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable);

    /**
     * @param entityClass  实体模模型类
     * @param prefix       实体名称前缀
     * @param shardingable 分片(表)参数对象
     * @param fields       字段名称集合，可选参数，若不指定则包括全部字段
     * @return 返回插入实体数据记录SQL语句
     */
    String buildInsertSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields fields);

    /**
     * @param entityClass  实体模模型类
     * @param prefix       实体名称前缀
     * @param shardingable 分片(表)参数对象
     * @param pkFields     主键字段名称集合，可选参数，若不指定则包括全部主键
     * @return 返回删除实体数据记录SQL语句
     */
    String buildDeleteByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields pkFields);

    /**
     * @param entityClass  实体模模型类
     * @param prefix       前缀名称
     * @param shardingable 分片(表)参数对象
     * @param pkFields     主键字段名称集合，可选参数，若不指定则包括全部主键
     * @param fields       字段名称集合，可选参数，若不指定则包括全部字段
     * @return 返回更新实体数据记录SQL语句
     */
    String buildUpdateByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields pkFields, Fields fields);

    /**
     * @param entityClass  实体模模型类
     * @param prefix       实体名称前缀
     * @param shardingable 分片(表)参数对象
     * @param pkFields     主键字段名称集合，可选参数，若不指定则包括全部主键
     * @param fields       字段名称集合，可选参数，若不指定则包括全部字段
     * @return 返回根据主键查询实体数据记录SQL语句
     */
    String buildSelectByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields pkFields, Fields fields);

    /**
     * @param entityClass  实体模模型类
     * @param prefix       实体名称前缀
     * @param shardingable 分片(表)参数对象
     * @param fields       字段名称集合，可选参数，若不指定则包括全部字段
     * @return 返回查询全部实体数据记录SQL语句
     */
    String buildSelectSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable, Fields fields);
}
