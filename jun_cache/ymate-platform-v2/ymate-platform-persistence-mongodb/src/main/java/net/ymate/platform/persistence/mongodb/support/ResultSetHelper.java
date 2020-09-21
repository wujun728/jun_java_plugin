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

import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.mongodb.MongoDB;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/22 下午11:41
 * @version 1.0
 */
public class ResultSetHelper {

    @SuppressWarnings("unchecked")
    public static <T extends IEntity> T toEntity(Class<T> entity, Document document) throws Exception {
        if (document == null) {
            return null;
        }
        ClassUtils.BeanWrapper<T> _wrapper = ClassUtils.wrapper(entity);
        assert _wrapper != null;
        EntityMeta _entityMeta = EntityMeta.createAndGet(entity);
        Object _primaryKeyObject = null;
        if (_entityMeta.isMultiplePrimaryKey()) {
            _primaryKeyObject = _entityMeta.getPrimaryKeyClass().newInstance();
            //
            _wrapper.getTargetObject().setId((Serializable) _primaryKeyObject);
        }
        for (EntityMeta.PropertyMeta _propMeta : _entityMeta.getProperties()) {
            Object _propValue = null;
            if (_entityMeta.isPrimaryKey(_propMeta.getName())) {
                _propValue = document.getObjectId(_propMeta.getName()).toString();
            } else {
                _propValue = document.get(_propMeta.getName());
            }
            if (_propValue == null) {
                if (StringUtils.trimToNull(_propMeta.getDefaultValue()) != null) {
                    _propValue = BlurObject.bind(_propMeta.getDefaultValue()).toObjectValue(_propMeta.getField().getType());
                } else {
                    continue;
                }
            }
            if (_entityMeta.isPrimaryKey(_propMeta.getName()) && _entityMeta.isMultiplePrimaryKey()) {
                _propMeta.getField().set(_primaryKeyObject, _propValue);
            } else {
                _propMeta.getField().set(_wrapper.getTargetObject(), _propValue);
            }
        }
        return _wrapper.getTargetObject();
    }

    public static <T extends IEntity> List<T> toEntities(Class<T> entity, MongoIterable<Document> iterable) throws Exception {
        MongoCursor<Document> _documentIt = iterable.iterator();
        List<T> _resultSet = new ArrayList<T>();
        while (_documentIt.hasNext()) {
            _resultSet.add(toEntity(entity, _documentIt.next()));
        }
        return _resultSet;
    }

    public static <T extends IEntity> Document toDocument(T entity) throws Exception {
        if (entity == null) {
            return null;
        }
        EntityMeta _entityMeta = EntityMeta.createAndGet(entity.getClass());
        Document _returnObj = new Document();
        for (EntityMeta.PropertyMeta _propMeta : _entityMeta.getProperties()) {
            Object _value = _propMeta.getField().get(entity);
            if (_value == null) {
                _returnObj.append(_propMeta.getName(), null);
                continue;
            }
            if (MongoDB.OPT.ID.equals(_propMeta.getName())) {
                _returnObj.put(_propMeta.getName(), new ObjectId(_value.toString()));
            } else {
                _returnObj.put(_propMeta.getName(), _value);
            }
        }
        return _returnObj;
    }
}
