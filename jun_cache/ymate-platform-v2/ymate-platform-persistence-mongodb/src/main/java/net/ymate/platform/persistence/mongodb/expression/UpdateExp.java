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

import com.mongodb.BasicDBObject;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.Params;
import net.ymate.platform.persistence.mongodb.AbstractOperator;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.support.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/28 下午3:23
 * @version 1.0
 */
public class UpdateExp extends AbstractOperator {

    public static UpdateExp inc(String field, Number amount) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.INC, field, amount);
        return _exp;
    }

    public static UpdateExp mul(String field, Number number) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.MUL, field, number);
        return _exp;
    }

    public static UpdateExp rename(String field, String newName) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.RENAME, field, newName);
        return _exp;
    }

    public static UpdateExp setOnInsert(String field, Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.SET_ON_INSERT, field, value);
        return _exp;
    }

    public static UpdateExp setOnInsert(Map object) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.SET_ON_INSERT, object);
        return _exp;
    }

    public static UpdateExp set(String field, Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.SET, field, value);
        return _exp;
    }

    public static UpdateExp set(Map object) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.SET, object);
        return _exp;
    }

    public static UpdateExp unset(String field) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.UNSET, field, "");
        return _exp;
    }

    public static UpdateExp unset(Fields fields) {
        UpdateExp _exp = new UpdateExp();
        Map<String, String> _fields = new HashMap<String, String>();
        for (String _field : fields.fields()) {
            _fields.put(_field, "");
        }
        _exp.__doPutOperator(IMongo.OPT.UNSET, _fields);
        return _exp;
    }

    public static UpdateExp min(String field, Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.MIN, field, value);
        return _exp;
    }

    public static UpdateExp min(Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.MIN, value);
        return _exp;
    }

    public static UpdateExp max(String field, Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.MAX, field, value);
        return _exp;
    }

    public static UpdateExp max(Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.MAX, value);
        return _exp;
    }

    public static UpdateExp addToSet(String field, Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.ADD_TO_SET, field, value);
        return _exp;
    }

    public static UpdateExp addToSet(Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.ADD_TO_SET, value);
        return _exp;
    }

    public static UpdateExp each(Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.EACH, value);
        return _exp;
    }

    public static UpdateExp sort(boolean asc) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.SORT, asc ? 1 : -1);
        return _exp;
    }

    public static UpdateExp position(int position) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.POSITION, position);
        return _exp;
    }

    public static UpdateExp isolated() {
        UpdateExp _exp = new UpdateExp();
        _exp.__doAddOperator(IMongo.OPT.ISOLATED, 1);
        return _exp;
    }

    public static UpdateExp push(String field, Object value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.PUSH, field, value);
        return _exp;
    }

    public static UpdateExp pushAll(String field, Params value) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.PUSH_ALL, field, value.toArray());
        return _exp;
    }

    public static UpdateExp pull(String field, Query query) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.PULL, field, query.toBson());
        return _exp;
    }

    public static UpdateExp pullAll(String field, Query... queries) {
        UpdateExp _exp = new UpdateExp();
        List<BasicDBObject> _dbObj = new ArrayList<BasicDBObject>();
        for (Query _query : queries) {
            _dbObj.add(_query.toBson());
        }
        _exp.__doPutOperator(IMongo.OPT.PULL_ALL, field, _dbObj);
        return _exp;
    }

    public static UpdateExp pop(String field, boolean first) {
        UpdateExp _exp = new UpdateExp();
        _exp.__doPutOperator(IMongo.OPT.POP, field, first ? -1 : 1);
        return _exp;
    }
}
