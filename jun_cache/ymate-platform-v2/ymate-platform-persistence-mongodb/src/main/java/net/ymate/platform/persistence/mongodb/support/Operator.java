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
package net.ymate.platform.persistence.mongodb.support;

import com.mongodb.BasicDBObject;
import net.ymate.platform.persistence.Fields;
import net.ymate.platform.persistence.Params;
import net.ymate.platform.persistence.mongodb.AbstractOperator;
import net.ymate.platform.persistence.mongodb.IMongo;
import org.bson.BSONObject;
import org.bson.BsonType;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/24 下午1:24
 * @version 1.0
 */
public final class Operator extends AbstractOperator {

    public static Operator create() {
        return new Operator();
    }

    private Operator() {
    }

    public Operator cmp(Object exp1, Object exp2) {
        __doAddOperator(IMongo.OPT.CMP, new Object[]{exp1, exp2});
        return this;
    }

    public Operator eq(Object param) {
        __doAddOperator(IMongo.OPT.EQ, param);
        return this;
    }

    public Operator eq(Params params) {
        __doAddOperator(IMongo.OPT.EQ, params);
        return this;
    }

    public Operator ne(Object param) {
        __doAddOperator(IMongo.OPT.NE, param);
        return this;
    }

    public Operator ne(Params params) {
        __doAddOperator(IMongo.OPT.NE, params);
        return this;
    }

    public Operator gt(Object param) {
        __doAddOperator(IMongo.OPT.GT, param);
        return this;
    }

    public Operator gt(Params params) {
        __doAddOperator(IMongo.OPT.GT, params);
        return this;
    }

    public Operator gte(Object param) {
        __doAddOperator(IMongo.OPT.GTE, param);
        return this;
    }

    public Operator gte(Params params) {
        __doAddOperator(IMongo.OPT.GTE, params);
        return this;
    }

    public Operator lt(Object param) {
        __doAddOperator(IMongo.OPT.LT, param);
        return this;
    }

    public Operator lt(Params params) {
        __doAddOperator(IMongo.OPT.LT, params);
        return this;
    }

    public Operator lte(Object param) {
        __doAddOperator(IMongo.OPT.LTE, param);
        return this;
    }

    public Operator lte(Params params) {
        __doAddOperator(IMongo.OPT.LTE, params);
        return this;
    }

    public Operator in(Params values) {
        __doAddOperator(IMongo.OPT.IN, values.toArray());
        return this;
    }

    public Operator nin(Params values) {
        __doAddOperator(IMongo.OPT.NIN, values.toArray());
        return this;
    }

    @SuppressWarnings("unchecked")
    public Operator or(Query... queries) {
        List<Bson> _queries = (List<Bson>) __operation.get(IMongo.OPT.OR);
        if (_queries == null) {
            _queries = new ArrayList<Bson>();
            __doAddOperator(IMongo.OPT.OR, _queries);
        }
        for (Query _query : queries) {
            _queries.add(_query.toBson());
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public Operator and(Query... queries) {
        List<Bson> _queries = (List<Bson>) __operation.get(IMongo.OPT.AND);
        if (_queries == null) {
            _queries = new ArrayList<Bson>();
            __doAddOperator(IMongo.OPT.AND, _queries);
        }
        for (Query _query : queries) {
            _queries.add(_query.toBson());
        }
        return this;
    }

    public Operator not(Operator operator) {
        __doAddOperator(IMongo.OPT.NOT, operator.toBson());
        return this;
    }

    @SuppressWarnings("unchecked")
    public Operator nor(Query... queries) {
        List<Bson> _queries = (List<Bson>) __operation.get(IMongo.OPT.NOR);
        if (_queries == null) {
            _queries = new ArrayList<Bson>();
            __doAddOperator(IMongo.OPT.NOR, _queries);
        }
        for (Query _query : queries) {
            _queries.add(_query.toBson());
        }
        return this;
    }

    public Operator exists(boolean exists) {
        __doAddOperator(IMongo.OPT.EXISTS, exists);
        return this;
    }

    public Operator type(BsonType type) {
        __doAddOperator(IMongo.OPT.TYPE, type);
        return this;
    }

    public Operator mod(int divisor, int remainder) {
        __doAddOperator(IMongo.OPT.MOD, new int[]{divisor, remainder});
        return this;
    }

    public Operator mod(String divisor, String remainder) {
        __doAddOperator(IMongo.OPT.MOD, new String[]{divisor, remainder});
        return this;
    }

    public Operator regex(String regex) {
        __doAddOperator(IMongo.OPT.REGEX, Pattern.compile(regex));
        return this;
    }

    public Operator text(String search) {
        return text(search, null);
    }

    public Operator text(String search, String language) {
        BasicDBObject _dbObj = new BasicDBObject(IMongo.OPT.SEARCH, search);
        if (language != null) {
            _dbObj.put(IMongo.OPT.LANGUAGE, language);
        }
        __doAddOperator(IMongo.OPT.TEXT, _dbObj);
        return this;
    }

    public Operator where(String jsFunction) {
        __doAddOperator(IMongo.OPT.WHERE, jsFunction);
        return this;
    }

    public Operator all(Params params) {
        __doAddOperator(IMongo.OPT.ALL, params.toArray());
        return this;
    }

    public Operator elemMatch(Operator... operators) {
        BasicDBObject _dbObj = new BasicDBObject();
        for (Operator _opt : operators) {
            _dbObj.putAll((BSONObject) _opt.toBson());
        }
        __doAddOperator(IMongo.OPT.ELEM_MATCH, _dbObj);
        return this;
    }

    public Operator elemMatch(Query... queries) {
        BasicDBObject _dbObj = new BasicDBObject();
        for (Query _query : queries) {
            _dbObj.putAll((BSONObject) _query.toBson());
        }
        __doAddOperator(IMongo.OPT.ELEM_MATCH, _dbObj);
        return this;
    }

    public Operator size(int size) {
        __doAddOperator(IMongo.OPT.SIZE, size);
        return this;
    }

    public Operator size(Object size) {
        __doAddOperator(IMongo.OPT.SIZE, size);
        return this;
    }

    public Operator meta(String meta) {
        __doAddOperator(IMongo.OPT.META, meta);
        return this;
    }

    public Operator slice(int slice) {
        __doAddOperator(IMongo.OPT.SLICE, slice);
        return this;
    }

    public Operator slice(int skip, int limit) {
        __doAddOperator(IMongo.OPT.SLICE, new int[]{skip, limit});
        return this;
    }

    //

    public Operator inc(String field, Number amount) {
        __doPutOperator(IMongo.OPT.INC, field, amount);
        return this;
    }

    public Operator mul(String field, Number number) {
        __doPutOperator(IMongo.OPT.MUL, field, number);
        return this;
    }

    public Operator rename(String field, String newName) {
        __doPutOperator(IMongo.OPT.RENAME, field, newName);
        return this;
    }

    public Operator setOnInsert(String field, Object value) {
        __doPutOperator(IMongo.OPT.SET_ON_INSERT, field, value);
        return this;
    }

    public Operator setOnInsert(Map object) {
        __doPutOperator(IMongo.OPT.SET_ON_INSERT, object);
        return this;
    }

    public Operator set(String field, Object value) {
        __doPutOperator(IMongo.OPT.SET, field, value);
        return this;
    }

    public Operator set(Map object) {
        __doPutOperator(IMongo.OPT.SET, object);
        return this;
    }

    public Operator unset(String field) {
        __doPutOperator(IMongo.OPT.UNSET, field, "");
        return this;
    }

    public Operator unset(Fields fields) {
        Map<String, String> _fields = new HashMap<String, String>();
        for (String _field : fields.fields()) {
            _fields.put(_field, "");
        }
        __doPutOperator(IMongo.OPT.UNSET, _fields);
        return this;
    }

    public Operator min(String field, Object value) {
        __doPutOperator(IMongo.OPT.MIN, field, value);
        return this;
    }

    public Operator min(Object value) {
        __doAddOperator(IMongo.OPT.MIN, value);
        return this;
    }

    public Operator max(String field, Object value) {
        __doPutOperator(IMongo.OPT.MAX, field, value);
        return this;
    }

    public Operator max(Object value) {
        __doAddOperator(IMongo.OPT.MAX, value);
        return this;
    }

    public Operator addToSet(String field, Object value) {
        __doPutOperator(IMongo.OPT.ADD_TO_SET, field, value);
        return this;
    }

    public Operator addToSet(Object value) {
        __doAddOperator(IMongo.OPT.ADD_TO_SET, value);
        return this;
    }

    public Operator each(Object value) {
        __doAddOperator(IMongo.OPT.EACH, value);
        return this;
    }

    public Operator sort(boolean asc) {
        __doAddOperator(IMongo.OPT.SORT, asc ? 1 : -1);
        return this;
    }

    public Operator position(int position) {
        __doAddOperator(IMongo.OPT.POSITION, position);
        return this;
    }

    public Operator isolated() {
        __doAddOperator(IMongo.OPT.ISOLATED, 1);
        return this;
    }

    public Operator push(String field, Object value) {
        __doPutOperator(IMongo.OPT.PUSH, field, value);
        return this;
    }

    public Operator pushAll(String field, Params value) {
        __doPutOperator(IMongo.OPT.PUSH_ALL, field, value.toArray());
        return this;
    }

    public Operator pull(String field, Query query) {
        __doPutOperator(IMongo.OPT.PULL, field, query.toBson());
        return this;
    }

    public Operator pullAll(String field, Query... queries) {
        List<BasicDBObject> _dbObj = new ArrayList<BasicDBObject>();
        for (Query _query : queries) {
            _dbObj.add(_query.toBson());
        }
        __doPutOperator(IMongo.OPT.PULL_ALL, field, _dbObj);
        return this;
    }

    public Operator pop(String field, boolean first) {
        __doPutOperator(IMongo.OPT.POP, field, first ? -1 : 1);
        return this;
    }

    public Operator sum(Object expression) {
        __doAddOperator(IMongo.OPT.SUM, expression);
        return this;
    }

    public Operator avg(Object expression) {
        __doAddOperator(IMongo.OPT.AVG, expression);
        return this;
    }

    public Operator first(Object expression) {
        __doAddOperator(IMongo.OPT.FIRST, expression);
        return this;
    }

    public Operator last(Object expression) {
        __doAddOperator(IMongo.OPT.LAST, expression);
        return this;
    }

    public Operator substr(String string, int start, int length) {
        __doAddOperator(IMongo.OPT.SUBSTR, new Object[]{string, start, length});
        return this;
    }

    public BasicDBObject toBson() {
        return __operation;
    }
}
