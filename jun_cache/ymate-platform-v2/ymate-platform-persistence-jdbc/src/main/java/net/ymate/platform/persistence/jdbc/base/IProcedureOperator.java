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

import java.util.List;

/**
 * 数据库存储过程操作器接口定义
 *
 * @author 刘镇 (suninformation@163.com) on 2010-12-25 下午02:40:48
 * @version 1.0
 */
public interface IProcedureOperator<T> extends IOperator {

    IProcedureOperator<T> execute(IResultSetHandler<T> resultSetHandler) throws Exception;

    IProcedureOperator<T> execute(IOutResultProcessor resultProcessor) throws Exception;

    /**
     * @param sqlParamType SQL参数类型(参考java.sql.Types)
     * @return 添加输出参数
     */
    IProcedureOperator<T> addOutParameter(Integer sqlParamType);

    IProcedureOperator<T> addParameter(SQLParameter parameter);

    IProcedureOperator<T> addParameter(Object parameter);

    IProcedureOperator<T> setOutResultProcessor(IOutResultProcessor outResultProcessor);

    IProcedureOperator<T> setResultSetHandler(IResultSetHandler<T> resultSetHandler);

    List<List<T>> getResultSets();

    interface IOutResultProcessor {
        void process(int idx, int paramType, Object result) throws Exception;
    }
}
