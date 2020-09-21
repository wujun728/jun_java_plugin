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
package net.ymate.platform.persistence.mongodb;

import com.mongodb.BasicDBObject;
import net.ymate.platform.persistence.Params;

import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/27 下午6:04
 * @version 1.0
 */
public abstract class AbstractOperator implements IOperator {

    protected BasicDBObject __operation = new BasicDBObject();

    protected void __doAddOperator(String opt, Object param) {
        if (param instanceof Params) {
            __operation.put(opt, ((Params) param).toArray());
        } else {
            __operation.put(opt, param);
        }
    }

    protected void __doPutOperator(String opt, String field, Object value) {
        BasicDBObject _dbObj = (BasicDBObject) __operation.get(opt);
        if (_dbObj == null) {
            _dbObj = new BasicDBObject(field, value);
            __doAddOperator(opt, _dbObj);
        } else {
            _dbObj.put(field, value);
        }
    }

    protected void __doPutOperator(String opt, Map object) {
        BasicDBObject _dbObj = (BasicDBObject) __operation.get(opt);
        if (_dbObj == null) {
            _dbObj = new BasicDBObject();
            _dbObj.putAll(object);
            __doAddOperator(opt, _dbObj);
        } else {
            _dbObj.putAll(object);
        }
    }

    public IOperator add(IOperator operator) {
        __operation.putAll((Map) operator.toBson());
        return this;
    }

    public BasicDBObject toBson() {
        return __operation;
    }
}
