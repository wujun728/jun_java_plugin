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

import com.mzlion.core.io.IOUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Request body封装
 *
 * @author tony on 2016-12-09
 */
public class InputStreamRequestBody extends RequestBody {

    private final byte[] content;
    private final int byteCount;
    private final MediaType mediaType;

    public InputStreamRequestBody(InputStream content, MediaType mediaType) {
        this.mediaType = mediaType;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(content, baos);
            this.content = baos.toByteArray();
            this.byteCount = this.content.length;
        } finally {
            IOUtils.closeQuietly(content);
        }
    }

    /**
     * Returns the number of bytes that will be written to {@code out} in a call to {@link #writeTo},
     * or -1 if that count is unknown.
     */
    @Override
    public long contentLength() throws IOException {
        return this.byteCount;
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
        sink.write(this.content, 0, this.byteCount);
    }
}
