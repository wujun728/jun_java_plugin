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

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;

/**
 * 文件请求对象
 *
 * @author mzlion on 2016/12/9.
 */
public class FileRequestBody extends RequestBody {

    private final File file;
    private final MediaType mediaType;

    public FileRequestBody(File file, MediaType mediaType) {
        this.file = file;
        this.mediaType = mediaType;
    }

    /**
     * Returns the number of bytes that will be written to {@code out} in a call to {@link #writeTo},
     * or -1 if that count is unknown.
     */
    @Override
    public long contentLength() throws IOException {
        return file.length();
    }

    /**
     * Returns the Content-Type header for this body.
     */
    @Override
    public MediaType contentType() {
        return this.mediaType;
    }

    /**
     * Writes the content of this request to {@code out}.
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Source source = null;
        try {
            source = Okio.source(file);
            sink.writeAll(source);
        } finally {
            Util.closeQuietly(source);
        }
    }
}
