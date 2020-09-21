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

import net.ymate.platform.core.YMP;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 上午9:24
 * @version 1.0
 */
public interface IMongo {

    String MODULE_NAME = "persistence.mongodb";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回MongoDB模块配置对象
     */
    IMongoModuleCfg getModuleCfg();

    IMongoDataSourceAdapter getDefaultDataSourceAdapter() throws Exception;

    IMongoDataSourceAdapter getDataSourceAdapter(String dataSourceName) throws Exception;

    <T> T openSession(IMongoSessionExecutor<T> executor) throws Exception;

    <T> T openSession(IMongoDatabaseHolder databaseHolder, IMongoSessionExecutor<T> executor) throws Exception;

    <T> T openSession(IGridFSSessionExecutor<T> executor) throws Exception;

    <T> T openSession(String bucketName, IGridFSSessionExecutor<T> executor) throws Exception;

    <T> T openSession(IMongoDataSourceAdapter dataSourceAdapter, IGridFSSessionExecutor<T> executor) throws Exception;

    <T> T openSession(IMongoDataSourceAdapter dataSourceAdapter, String bucketName, IGridFSSessionExecutor<T> executor) throws Exception;

    class GridFS {
        public static final String FILE_NAME = "filename";
        public static final String ALIASES = "aliases";
        public static final String CHUNK_SIZE = "chunkSize";
        public static final String UPLOAD_DATE = "uploadDate";
        public static final String LENGTH = "length";
        public static final String CONTENT_TYPE = "contentType";
        public static final String MD5 = "md5";
    }

    class OPT {

        // 主键
        public static final String ID = "_id";

        // 查询条件
        public static final String CMP = "$cmp";
        public static final String EQ = "$eq";
        public static final String GT = "$gt";
        public static final String GTE = "$gte";
        public static final String LT = "$lt";
        public static final String LTE = "$lte";
        public static final String NE = "$ne";
        public static final String IN = "$in";
        public static final String NIN = "$nin";

        public static final String SLICE = "$slice";

        //
        public static final String AND = "$and";
        public static final String OR = "$or";
        public static final String NOT = "$not";
        public static final String NOR = "$nor";

        public static final String EXISTS = "$exists";
        public static final String TYPE = "$type";
        public static final String MOD = "$mod";
        public static final String REGEX = "$regex";

        public static final String TEXT = "$text";
        public static final String SEARCH = "$search";
        public static final String LANGUAGE = "$language";

        public static final String WHERE = "$where";

        public static final String SUBSTR = "$substr";

        //
        public static final String NEAR = "$near";
        public static final String NEAR_SPHERE = "$nearSphere";

        public static final String GEO_WITHIN = "$geoWithin";
        public static final String GEO_INTERSECTS = "$geoIntersects";

        public static final String BOX = "$box";
        public static final String POLYGON = "$polygon";
        public static final String CENTER = "$center";
        public static final String CENTER_SPHERE = "$centerSphere";

        //
        public static final String ALL = "$all";
        public static final String ELEM_MATCH = "$elemMatch";
        public static final String SIZE = "$size";

        //
        public static final String SET = "$set";
        public static final String UNSET = "$unset";
        public static final String INC = "$inc";
        public static final String MUL = "$mul";
        public static final String RENAME = "$rename";
        public static final String SET_ON_INSERT = "$setOnInsert";
        public static final String PULL = "$pull";
        public static final String PULL_ALL = "$pullAll";
        public static final String EACH = "$each";
        public static final String POSITION = "$position";
        public static final String POP = "$pop";

//        public static final String BIT = "$bit"; // Current Not Support.

        public static final String SUM = "$sum";
        public static final String AVG = "$avg";
        public static final String FIRST = "$first";
        public static final String LAST = "$last";
        public static final String MAX = "$max";
        public static final String MIN = "$min";

//        public static final String CURRENT_DATE = "$currentDate"; // Current Not Support.

        public static final String PUSH = "$push";
        public static final String PUSH_ALL = "$pushAll";
        public static final String ADD_TO_SET = "$addToSet";
        public static final String ISOLATED = "$isolated";

        // 聚合类型
        public static final String PROJECT = "$project";
        public static final String MATCH = "$match";
        public static final String REDACT = "$redact";

        public static final String LIMIT = "$limit";
        public static final String SKIP = "$skip";
        public static final String UNWIND = "$unwind";
        public static final String GROUP = "$group";
        public static final String SORT = "$sort";
        public static final String OUT = "$out";

        public static final String META = "$meta";
    }
}
