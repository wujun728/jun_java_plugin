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
package net.ymate.platform.persistence.mongodb.expression;

import net.ymate.platform.persistence.mongodb.AbstractOperator;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.IOperator;
import net.ymate.platform.persistence.mongodb.support.Query;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/28 下午2:40
 * @version 1.0
 */
public class LogicalExp extends AbstractOperator {

    @SuppressWarnings("unchecked")
    public static LogicalExp or(Query... queries) {
        LogicalExp _exp = new LogicalExp();
        List<Bson> _queries = (List<Bson>) _exp.__operation.get(IMongo.OPT.OR);
        if (_queries == null) {
            _queries = new ArrayList<Bson>();
            _exp.__doAddOperator(IMongo.OPT.OR, _queries);
        }
        for (Query _query : queries) {
            _queries.add(_query.toBson());
        }
        return _exp;
    }

    @SuppressWarnings("unchecked")
    public static LogicalExp and(Query... queries) {
        LogicalExp _exp = new LogicalExp();
        List<Bson> _queries = (List<Bson>) _exp.__operation.get(IMongo.OPT.AND);
        if (_queries == null) {
            _queries = new ArrayList<Bson>();
            _exp.__doAddOperator(IMongo.OPT.AND, _queries);
        }
        for (Query _query : queries) {
            _queries.add(_query.toBson());
        }
        return _exp;
    }

    public static LogicalExp not(IOperator operator) {
        LogicalExp _exp = new LogicalExp();
        _exp.__doAddOperator(IMongo.OPT.NOT, operator.toBson());
        return _exp;
    }

    @SuppressWarnings("unchecked")
    public static LogicalExp nor(Query... queries) {
        LogicalExp _exp = new LogicalExp();
        List<Bson> _queries = (List<Bson>) _exp.__operation.get(IMongo.OPT.NOR);
        if (_queries == null) {
            _queries = new ArrayList<Bson>();
            _exp.__doAddOperator(IMongo.OPT.NOR, _queries);
        }
        for (Query _query : queries) {
            _queries.add(_query.toBson());
        }
        return _exp;
    }
}
