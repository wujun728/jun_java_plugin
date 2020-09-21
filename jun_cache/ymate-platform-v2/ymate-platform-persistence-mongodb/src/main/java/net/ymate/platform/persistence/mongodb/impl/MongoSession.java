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
package net.ymate.platform.persistence.mongodb.impl;

import com.mongodb.client.*;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.*;
import net.ymate.platform.persistence.base.EntityMeta;
import net.ymate.platform.persistence.base.IEntity;
import net.ymate.platform.persistence.impl.DefaultResultSet;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.IMongoDatabaseHolder;
import net.ymate.platform.persistence.mongodb.IMongoSession;
import net.ymate.platform.persistence.mongodb.expression.ComparisonExp;
import net.ymate.platform.persistence.mongodb.expression.UpdateExp;
import net.ymate.platform.persistence.mongodb.support.Aggregation;
import net.ymate.platform.persistence.mongodb.support.OrderBy;
import net.ymate.platform.persistence.mongodb.support.Query;
import net.ymate.platform.persistence.mongodb.support.ResultSetHelper;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/22 下午10:35
 * @version 1.0
 */
public class MongoSession implements IMongoSession {

    private String __id;

    private IMongoDatabaseHolder __databaseHolder;

    private String __collectionPrefix;

    private ISessionEvent __sessionEvent;

    public MongoSession(IMongoDatabaseHolder databaseHolder) {
        this.__id = UUIDUtils.UUID();
        this.__databaseHolder = databaseHolder;
        __collectionPrefix = databaseHolder.getDataSourceCfgMeta().getCollectionPrefix();
    }

    public String getId() {
        return __id;
    }

    public IMongoSession setSessionEvent(ISessionEvent event) {
        __sessionEvent = event;
        return this;
    }

    public void close() {
    }

    public IMongoDatabaseHolder getDatabaseHolder() {
        return __databaseHolder;
    }

    protected <T extends IEntity> MongoCollection<Document> __doGetCollection(Class<T> entity) {
        String _collectionName = StringUtils.defaultIfBlank(__collectionPrefix, "").concat(EntityMeta.createAndGet(entity).getEntityName());
        return __databaseHolder.getDatabase().getCollection(_collectionName);
    }

    protected boolean __doPageInit(FindIterable<Document> findIterable, Page page) {
        if (page != null && page.page() > 0 && page.pageSize() > 0) {
            findIterable.skip((page.page() - 1) * page.pageSize()).limit(page.pageSize());
            return true;
        }
        return false;
    }

    public <T extends IEntity> IResultSet<T> find(final Class<T> entity) throws Exception {
        if (__sessionEvent != null) {
            __sessionEvent.onQueryBefore(new SessionEventContext(entity));
        }
        return new DefaultResultSet<T>(ResultSetHelper.toEntities(entity, __doGetCollection(entity).find()));
    }

    public <T extends IEntity> IResultSet<T> find(Class<T> entity, OrderBy orderBy) throws Exception {
        return find(entity, orderBy, null);
    }

    public <T extends IEntity> IResultSet<T> find(Class<T> entity, Page page) throws Exception {
        return find(entity, null, page);
    }

    public <T extends IEntity> IResultSet<T> find(Class<T> entity, OrderBy orderBy, Page page) throws Exception {
        MongoCollection<Document> _collection = __doGetCollection(entity);
        FindIterable<Document> _findIterable = _collection.find();
        if (orderBy != null) {
            _findIterable.sort(orderBy.toBson());
        }
        long _recordCount = 0;
        if (__doPageInit(_findIterable, page)) {
            _recordCount = _collection.count();
        }
        return new DefaultResultSet<T>(ResultSetHelper.toEntities(entity, _findIterable), page.page(), page.pageSize(), _recordCount);
    }

    public <T extends IEntity> T findFirst(Class<T> entity, Query filter) throws Exception {
        return ResultSetHelper.toEntity(entity, __doGetCollection(entity).find(filter.toBson()).first());
    }

    public <T extends IEntity> T find(Class<T> entity, Serializable id) throws Exception {
        return findFirst(entity, Query.create(IMongo.OPT.ID, ComparisonExp.eq(new ObjectId(id.toString()))));
    }

    public <T extends IEntity> long count(Class<T> entity) throws Exception {
        return __doGetCollection(entity).count();
    }

    public <T extends IEntity> long count(Class<T> entity, Query filter) throws Exception {
        return __doGetCollection(entity).count(filter.toBson());
    }

    public <T extends IEntity> boolean exists(Class<T> entity, Serializable id) throws Exception {
        return find(entity, id) != null;
    }

    public <T extends IEntity> boolean exists(Class<T> entity, Query filter) throws Exception {
        return findFirst(entity, filter) != null;
    }

    public <T extends IEntity, RESULT> AggregateIterable<RESULT> aggregate(Class<T> entity, Class<RESULT> resultClass, Aggregation... aggregations) throws Exception {
        List<Bson> _pipeline = new ArrayList<Bson>(aggregations.length);
        for (Aggregation _aggregation : aggregations) {
            _pipeline.add(_aggregation.toBson());
        }
        return __doGetCollection(entity).aggregate(_pipeline, resultClass);
    }

    public <T extends IEntity, RESULT> DistinctIterable<RESULT> distinct(Class<T> entity, Class<RESULT> resultClass, String fieldName) throws Exception {
        return __doGetCollection(entity).distinct(fieldName, resultClass);
    }

    public <T extends IEntity, RESULT> DistinctIterable<RESULT> distinct(Class<T> entity, Class<RESULT> resultClass, String fieldName, Query query) throws Exception {
        return __doGetCollection(entity).distinct(fieldName, query.toBson(), resultClass);
    }

    public <T extends IEntity, RESULT> MapReduceIterable<RESULT> mapReduce(Class<T> entity, Class<RESULT> resultClass, String mapFunction, String reduceFunction) throws Exception {
        return __doGetCollection(entity).mapReduce(mapFunction, reduceFunction, resultClass);
    }

    public <T extends IEntity> MapReduceIterable<Document> mapReduce(Class<T> entity, String mapFunction, String reduceFunction) throws Exception {
        return __doGetCollection(entity).mapReduce(mapFunction, reduceFunction);
    }

    public <T extends IEntity> T update(T entity) throws Exception {
        return update(entity, null);
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> T update(T entity, Fields filter) throws Exception {
        Document _document = ResultSetHelper.toDocument(entity);
        Query _cond = Query.create(IMongo.OPT.ID, ComparisonExp.eq(_document.remove(IMongo.OPT.ID)));
        UpdateExp _opt = new UpdateExp();
        if (filter != null && !filter.fields().isEmpty()) {
            if (filter.isExcluded()) {
                _opt.add(UpdateExp.unset(filter));
            } else {
                for (String key : filter.fields()) {
                    _opt.add(UpdateExp.set(key, _document.get(key)));
                }
            }
        } else {
            _opt.add(UpdateExp.set(_document));
        }
        //
        _document = __doGetCollection(entity.getClass()).findOneAndUpdate(_cond.toBson(), _opt.toBson());
        //
        return (T) ResultSetHelper.toEntity(entity.getClass(), _document);
    }

    public <T extends IEntity> List<T> update(List<T> entities) throws Exception {
        return update(entities, null);
    }

    public <T extends IEntity> List<T> update(List<T> entities, Fields filter) throws Exception {
        List<T> _results = new ArrayList<T>();
        for (T _entity : entities) {
            _results.add(update(_entity, filter));
        }
        return _results;
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> T insert(T entity) throws Exception {
        Document _document = ResultSetHelper.toDocument(entity);
        if (entity.getId() == null && StringUtils.isBlank(entity.getId().toString())) {
            _document.remove(IMongo.OPT.ID);
        }
        __doGetCollection(entity.getClass()).insertOne(_document);
        String _id = _document.get(IMongo.OPT.ID).toString();
        entity.setId(_id);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> List<T> insert(List<T> entities) throws Exception {
        for (T _entity : entities) {
            Document _document = ResultSetHelper.toDocument(_entity);
            if (_entity.getId() == null && StringUtils.isBlank(_entity.getId().toString())) {
                _document.remove(IMongo.OPT.ID);
            }
            __doGetCollection(_entity.getClass()).insertOne(_document);
            String _id = _document.get(IMongo.OPT.ID).toString();
            _entity.setId(_id);
        }
        return entities;
    }

    @SuppressWarnings("unchecked")
    public <T extends IEntity> T delete(T entity) throws Exception {
        return (T) delete(entity.getClass(), entity.getId());
    }

    public <T extends IEntity> T delete(Class<T> entity, Serializable id) throws Exception {
        Document _document = __doGetCollection(entity).findOneAndDelete(
                Query.create(IMongo.OPT.ID, ComparisonExp.eq(new ObjectId(id.toString()))).toBson());
        return ResultSetHelper.toEntity(entity, _document);
    }

    public <T extends IEntity> List<T> delete(List<T> entities) throws Exception {
        List<T> _results = new ArrayList<T>();
        for (T _entity : entities) {
            _results.add(delete(_entity));
        }
        return _results;
    }

    public <T extends IEntity> List<T> delete(Class<T> entity, Params ids) throws Exception {
        List<T> _results = new ArrayList<T>();
        for (Object _id : ids.params()) {
            _results.add(delete(entity, _id.toString()));
        }
        return _results;
    }
}
