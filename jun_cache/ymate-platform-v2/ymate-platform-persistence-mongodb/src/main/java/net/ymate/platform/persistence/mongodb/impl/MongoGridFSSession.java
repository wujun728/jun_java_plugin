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

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.mongodb.gridfs.GridFSInputFile;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.persistence.ISessionEvent;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.mongodb.IGridFSSession;
import net.ymate.platform.persistence.mongodb.IMongo;
import net.ymate.platform.persistence.mongodb.IMongoDataSourceAdapter;
import net.ymate.platform.persistence.mongodb.support.GridFSFileBuilder;
import net.ymate.platform.persistence.mongodb.support.Operator;
import net.ymate.platform.persistence.mongodb.support.OrderBy;
import net.ymate.platform.persistence.mongodb.support.Query;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/26 上午11:12
 * @version 1.0
 */
public class MongoGridFSSession implements IGridFSSession {

    private String __id;

    private IMongoDataSourceAdapter __dataSourceHolder;

    private String __bucketName;

    private GridFS __gridFS;

    private DBCollection __dbCollection;

    private ISessionEvent __sessionEvent;

    public MongoGridFSSession(IMongoDataSourceAdapter dataSourceAdapter) throws Exception {
        this(dataSourceAdapter, GridFS.DEFAULT_BUCKET);
    }

    public MongoGridFSSession(IMongoDataSourceAdapter dataSourceAdapter, String bucketName) throws Exception {
        this.__id = UUIDUtils.UUID();
        this.__dataSourceHolder = dataSourceAdapter;
        this.__bucketName = StringUtils.defaultIfBlank(bucketName, GridFS.DEFAULT_BUCKET);
        //
        __gridFS = new GridFS(new DB(dataSourceAdapter.getMongoClient(), dataSourceAdapter.getDataSourceCfgMeta().getDatabaseName()), __bucketName);
        __dbCollection = __gridFS.getDB().getCollection(__bucketName.concat(".files"));
    }

    public IMongoDataSourceAdapter getDataSourceAdapter() {
        return __dataSourceHolder;
    }

    public GridFS getGridFS() {
        return __gridFS;
    }

    public String getId() {
        return __id;
    }

    public IGridFSSession setSessionEvent(ISessionEvent event) {
        __sessionEvent = event;
        return this;
    }

    public String getBucketName() {
        return __bucketName;
    }

    public void close() {
    }

    public GridFSFile upload(GridFSFileBuilder inputFile) throws Exception {
        GridFSInputFile _inFile = inputFile.build(this);
        _inFile.save();
        return _inFile;
    }

    public boolean existsById(String id) {
        return findById(id) != null;
    }

    public boolean exists(String filename) {
        return __gridFS.findOne(filename) != null;
    }

    public GridFSDBFile findFirst(String filename) {
        return __gridFS.findOne(filename);
    }

    public GridFSDBFile findFirst(Query query) {
        return __gridFS.findOne(query.toBson());
    }

    public GridFSDBFile findById(String id) {
        return __gridFS.find(new ObjectId(id));
    }

    public List<GridFSDBFile> findAll() {
        return findAll(null, null);
    }

    public List<GridFSDBFile> findAll(OrderBy orderBy) {
        return findAll(orderBy, null);
    }

    public List<GridFSDBFile> findAll(OrderBy orderBy, Page page) {
        DBCursor _cursor = __dbCollection.find();
        if (orderBy != null) {
            _cursor.sort(orderBy.toBson());
        }
        if (page != null && page.page() > 0 && page.pageSize() > 0) {
            _cursor.skip((page.page() - 1) * page.pageSize()).limit(page.pageSize());
        }
        List<GridFSDBFile> _results = new ArrayList<GridFSDBFile>();
        while (_cursor.hasNext()) {
            _results.add((GridFSDBFile) _cursor.next());
        }
        _cursor.close();
        return _results;
    }

    public List<GridFSDBFile> find(String filename) {
        return __gridFS.find(filename);
    }

    public List<GridFSDBFile> find(String filename, OrderBy orderBy) {
        return __gridFS.find(filename, orderBy.toBson());
    }

    public List<GridFSDBFile> find(Query query) {
        return __gridFS.find(query.toBson());
    }

    public List<GridFSDBFile> find(Query query, OrderBy orderBy) {
        return __gridFS.find(query.toBson(), orderBy.toBson());
    }

    public List<GridFSDBFile> find(Query query, OrderBy orderBy, Page page) {
        DBCursor _cursor = __dbCollection.find(query.toBson());
        if (orderBy != null) {
            _cursor.sort(orderBy.toBson());
        }
        if (page != null && page.page() > 0 && page.pageSize() > 0) {
            _cursor.skip((page.page() - 1) * page.pageSize()).limit(page.pageSize());
        }
        List<GridFSDBFile> _results = new ArrayList<GridFSDBFile>();
        while (_cursor.hasNext()) {
            _results.add((GridFSDBFile) _cursor.next());
        }
        _cursor.close();
        return _results;
    }

    public List<GridFSDBFile> find(Query query, Page page) {
        return find(query, null, page);
    }

    public void renameById(String id, String newName) {
        DBObject _dbObj = __dbCollection.findOne(new ObjectId(id));
        _dbObj.put(IMongo.GridFS.FILE_NAME, newName);
        __dbCollection.save(_dbObj);
    }

    public void rename(String currentName, String newName) {
        DBObject _dbObj = __dbCollection.findOne(Query.create().cond(IMongo.GridFS.FILE_NAME, Operator.create().eq(currentName)).toBson());
        _dbObj.put(IMongo.GridFS.FILE_NAME, newName);
        __dbCollection.save(_dbObj);
    }

    public void removeById(String id) {
        __gridFS.remove(new ObjectId(id));
    }

    public void remove(String filename) {
        __gridFS.remove(filename);
    }

    public void remove(Query query) {
        __gridFS.remove(query.toBson());
    }
}
