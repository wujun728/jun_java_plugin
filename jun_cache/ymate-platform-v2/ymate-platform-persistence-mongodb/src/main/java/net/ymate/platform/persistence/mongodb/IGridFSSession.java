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

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import net.ymate.platform.persistence.ISessionBase;
import net.ymate.platform.persistence.Page;
import net.ymate.platform.persistence.mongodb.support.GridFSFileBuilder;
import net.ymate.platform.persistence.mongodb.support.OrderBy;
import net.ymate.platform.persistence.mongodb.support.Query;

import java.util.List;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/26 上午10:06
 * @version 1.0
 */
public interface IGridFSSession extends ISessionBase {

    /**
     * @return 获取数据源适配器对象
     */
    IMongoDataSourceAdapter getDataSourceAdapter();

    /**
     * @return 获取GridFS对象
     */
    GridFS getGridFS();

    String getBucketName();

    com.mongodb.gridfs.GridFSFile upload(GridFSFileBuilder inputFile) throws Exception;

    boolean existsById(String id);

    boolean exists(String filename);

    com.mongodb.gridfs.GridFSFile findFirst(String filename);

    com.mongodb.gridfs.GridFSFile findFirst(Query query);

    com.mongodb.gridfs.GridFSFile findById(String id);

    List<GridFSDBFile> findAll();

    List<GridFSDBFile> findAll(OrderBy orderBy);

    List<GridFSDBFile> findAll(OrderBy orderBy, Page page);

    List<GridFSDBFile> find(String filename);

    List<GridFSDBFile> find(String filename, OrderBy orderBy);

    List<GridFSDBFile> find(Query query);

    List<GridFSDBFile> find(Query query, OrderBy orderBy);

    List<GridFSDBFile> find(Query query, OrderBy orderBy, Page page);

    List<GridFSDBFile> find(Query query, Page page);

    void renameById(String id, String newName);

    void rename(String currentName, String newName);

    void removeById(String id);

    void remove(String filename);

    void remove(Query query);
}
