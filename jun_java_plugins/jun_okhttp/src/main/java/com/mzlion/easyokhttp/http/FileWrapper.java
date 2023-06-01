/*
 * Copyright (C) 2016-2017 mzlion(mzllon@qq.com).
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
package com.mzlion.easyokhttp.http;

import com.mzlion.core.lang.Assert;
import com.mzlion.easyokhttp.exception.HttpClientException;
import com.mzlion.easyokhttp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.File;
import java.io.InputStream;

/**
 * 文件包装类
 *
 * @author mzlion on 2016/12/9.
 */
public class FileWrapper {

    private InputStream content;
    private File file;
    private String filename;

    private MediaType mediaType;

    FileWrapper(Builder builder) {
        this.mediaType = builder.mediaType;
        this.file = builder.file;
        this.filename = builder.filename;
        this.content = builder.content;
    }

    public String getFilename() {
        return filename;
    }

    public static Builder create() {
        return new Builder();
    }

    public RequestBody requestBody() {
        if (this.file != null) {
            return new FileRequestBody(this.file, this.mediaType);
        }
        return new InputStreamRequestBody(this.content, this.mediaType);
    }

    public static class Builder {

        private InputStream content;

        private File file;
        private String filename;

        private MediaType mediaType;


        public Builder file(File file) {
            Assert.notNull(file, "File may not be null.");
            if (!file.exists()) {
                throw new HttpClientException("File does not exist.");
            }
            this.file = file;
            return this;
        }

        public Builder filename(String filename) {
            Assert.hasLength(filename, "Filename may not be null.");
            this.filename = filename;
            return this;
        }

        public Builder stream(InputStream stream) {
            Assert.notNull(stream, "Stream may not be null.");
            this.content = stream;
            return this;
        }

        public Builder contentType(String contentType) {
            Assert.hasLength(contentType, "ContentType may not be null.");
            this.mediaType = MediaType.parse(contentType);
            return this;
        }

        public Builder mediaType(MediaType mediaType) {
            Assert.notNull(mediaType, "Media may not be null.");
            this.mediaType = mediaType;
            return this;
        }

        public FileWrapper build() {
            if (this.file != null) {
                if (this.filename == null) {
                    this.filename = file.getName();
                }
            } else if (this.content != null) {
                if (this.filename == null) {
                    throw new HttpClientException("Filename may not be null");
                }
            } else {
                throw new HttpClientException("The content is null.");
            }
            if (this.mediaType == null) {
                this.mediaType = Utils.guessMediaType(this.filename);
            }
            return new FileWrapper(this);
        }

    }
}
