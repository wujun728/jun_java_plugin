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

/**
 * 数据库更新操作器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2010-12-25 下午02:39:48
 * @version 1.0
 */
public interface IUpdateOperator extends IOperator {

    /**
     * 获取更新操作执行后影响的记录行数，不支持批量更新语句；
     * 对于 INSERT、UPDATE 或 DELETE 语句，返回行数; 对于无返回结果的SQL语句，返回 0
     *
     * @return int
     */
    int getEffectCounts();
}
