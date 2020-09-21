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
import net.ymate.platform.persistence.mongodb.AbstractOperator;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.IOperator;
import net.ymate.platform.persistence.mongodb.support.Query;
import org.bson.BSONObject;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/28 下午3:08
 * @version 1.0
 */
public class ProjectionExp extends AbstractOperator {

    public static ProjectionExp elemMatch(IOperator... operators) {
        ProjectionExp _exp = new ProjectionExp();
        BasicDBObject _dbObj = new BasicDBObject();
        for (IOperator _opt : operators) {
            _dbObj.putAll((BSONObject) _opt.toBson());
        }
        _exp.__doAddOperator(IMongo.OPT.ELEM_MATCH, _dbObj);
        return _exp;
    }

    public static ProjectionExp elemMatch(Query... queries) {
        ProjectionExp _exp = new ProjectionExp();
        BasicDBObject _dbObj = new BasicDBObject();
        for (Query _query : queries) {
            _dbObj.putAll((BSONObject) _query.toBson());
        }
        _exp.__doAddOperator(IMongo.OPT.ELEM_MATCH, _dbObj);
        return _exp;
    }

    public static ProjectionExp meta(String meta) {
        ProjectionExp _exp = new ProjectionExp();
        _exp.__doAddOperator(IMongo.OPT.META, meta);
        return _exp;
    }

    public static ProjectionExp slice(int slice) {
        ProjectionExp _exp = new ProjectionExp();
        _exp.__doAddOperator(IMongo.OPT.SLICE, slice);
        return _exp;
    }

    public static ProjectionExp slice(int skip, int limit) {
        ProjectionExp _exp = new ProjectionExp();
        _exp.__doAddOperator(IMongo.OPT.SLICE, new int[]{skip, limit});
        return _exp;
    }
}
