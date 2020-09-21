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
import net.ymate.platform.persistence.mongodb.IBsonable;
import net.ymate.platform.persistence.mongodb.IMongo;
import org.bson.BSONObject;
import org.bson.conversions.Bson;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/23 下午5:07
 * @version 1.0
 */
public final class Aggregation implements IBsonable {

    private BasicDBObject __pipeline;

    public static Aggregation create() {
        return new Aggregation();
    }

    private Aggregation() {
        __pipeline = new BasicDBObject();
    }

    public Aggregation project(Bson project) {
        __pipeline.put(IMongo.OPT.PROJECT, project);
        return this;
    }

    public Aggregation project(Fields fields) {
        BasicDBObject _dbObj = new BasicDBObject();
        for (String _field : fields.fields()) {
            _dbObj.put(_field, 1);
        }
        return project(_dbObj);
    }

    public Aggregation match(Bson match) {
        __pipeline.put(IMongo.OPT.MATCH, match);
        return this;
    }

    public Aggregation match(Query query) {
        return match(query.toBson());
    }

    public Aggregation redact(Bson expression) {
        __pipeline.put(IMongo.OPT.REDACT, expression);
        return this;
    }

    public Aggregation limit(int n) {
        __pipeline.put(IMongo.OPT.LIMIT, n);
        return this;
    }

    public Aggregation skip(int n) {
        __pipeline.put(IMongo.OPT.SKIP, n);
        return this;
    }

    public Aggregation unwind(String field) {
        if (!field.startsWith("$")) {
            field = "$" + field;
        }
        __pipeline.put(IMongo.OPT.UNWIND, field);
        return this;
    }

    public Aggregation group(Bson expression) {
        __pipeline.put(IMongo.OPT.GROUP, expression);
        return this;
    }

    public Aggregation group(Operator id, Query... queries) {
        BasicDBObject _dbObj = new BasicDBObject(IMongo.OPT.ID, id == null ? null : id.toBson());
        for (Query _query : queries) {
            _dbObj.putAll((BSONObject) _query.toBson());
        }
        return group(_dbObj);
    }

    public Aggregation sort(OrderBy orderBy) {
        __pipeline.put(IMongo.OPT.SORT, orderBy.toBson());
        return this;
    }

    public Aggregation out(String targetCollection) {
        __pipeline.put(IMongo.OPT.OUT, targetCollection);
        return this;
    }

    public BasicDBObject toBson() {
        return __pipeline;
    }
}
