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

import com.mongodb.gridfs.GridFSInputFile;
import net.ymate.platform.persistence.mongodb.IGridFSSession;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/27 上午12:01
 * @version 1.0
 */
public final class GridFSFileBuilder {

    private Object __targetObject;
    private String __filename;
    private String __contentType;

    private int __chunkSize;

    private int __type;

    private Map<String, Object> __attributes;

    public static GridFSFileBuilder create(File file) {
        return new GridFSFileBuilder(file, 1);
    }

    public static GridFSFileBuilder create(InputStream inputStream) {
        return new GridFSFileBuilder(inputStream, 2);
    }

    public static GridFSFileBuilder create(byte[] data) {
        return new GridFSFileBuilder(data, 3);
    }

    private GridFSFileBuilder(Object targetObject, int type) {
        __targetObject = targetObject;
        __type = type;
        __attributes = new HashMap<String, Object>();
    }

    public GridFSFileBuilder filename(String filename) {
        __filename = filename;
        return this;
    }

    public GridFSFileBuilder contentType(String contentType) {
        __contentType = contentType;
        return this;
    }

    public GridFSFileBuilder chunkSize(int chunkSize) {
        __chunkSize = chunkSize;
        return this;
    }

    public GridFSFileBuilder attribute(String attrName, Object attrValue) {
        __attributes.put(attrName, attrValue);
        return this;
    }

    public GridFSFileBuilder attribute(Map<String, Object> attributes) {
        __attributes.putAll(attributes);
        return this;
    }

    public GridFSInputFile build(IGridFSSession gridFS) throws Exception {
        GridFSInputFile _inFile = null;
        switch (__type) {
            case 1: // is File
                _inFile = gridFS.getGridFS().createFile((File) __targetObject);
                break;
            case 2: // is InputStream
                _inFile = gridFS.getGridFS().createFile((InputStream) __targetObject);
                break;
            case 3: // is Array
                _inFile = gridFS.getGridFS().createFile((byte[]) __targetObject);
        }
        if (_inFile != null) {
            _inFile.setFilename(__filename);
            _inFile.setContentType(__contentType);
            if (__chunkSize > 0) {
                _inFile.setChunkSize(__chunkSize);
            }
            if (!__attributes.isEmpty()) {
                for (Map.Entry<String, Object> _entry : __attributes.entrySet()) {
                    _inFile.put(_entry.getKey(), _entry.getValue());
                }
            }
        }
        return _inFile;
    }
}
