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
import net.ymate.platform.persistence.Params;
import net.ymate.platform.persistence.mongodb.AbstractOperator;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.IOperator;
import net.ymate.platform.persistence.mongodb.support.Query;
import org.bson.BSONObject;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/28 下午3:03
 * @version 1.0
 */
public class ArrayExp extends AbstractOperator {

    public static ArrayExp all(Params params) {
        ArrayExp _exp = new ArrayExp();
        _exp.__doAddOperator(IMongo.OPT.ALL, params.toArray());
        return _exp;
    }

    public static ArrayExp elemMatch(IOperator... operators) {
        ArrayExp _exp = new ArrayExp();
        BasicDBObject _dbObj = new BasicDBObject();
        for (IOperator _opt : operators) {
            _dbObj.putAll((BSONObject) _opt.toBson());
        }
        _exp.__doAddOperator(IMongo.OPT.ELEM_MATCH, _dbObj);
        return _exp;
    }

    public static ArrayExp elemMatch(Query... queries) {
        ArrayExp _exp = new ArrayExp();
        BasicDBObject _dbObj = new BasicDBObject();
        for (Query _query : queries) {
            _dbObj.putAll((BSONObject) _query.toBson());
        }
        _exp.__doAddOperator(IMongo.OPT.ELEM_MATCH, _dbObj);
        return _exp;
    }

    public static ArrayExp size(int size) {
        ArrayExp _exp = new ArrayExp();
        _exp.__doAddOperator(IMongo.OPT.SIZE, size);
        return _exp;
    }

    public static ArrayExp size(Object size) {
        ArrayExp _exp = new ArrayExp();
        _exp.__doAddOperator(IMongo.OPT.SIZE, size);
        return _exp;
    }
}
