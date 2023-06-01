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
package com.mzlion.easyokhttp.request;

import com.mzlion.core.http.ContentType;
import com.mzlion.core.io.IOUtils;
import com.mzlion.core.lang.Assert;
import com.mzlion.easyokhttp.utils.Utils;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.*;

/**
 * <p>
 * 2016-05-15 22:33 POST提交二进制流，服务端应该从Request请求体获取二进制流。
 * </p>
 *
 * @author mzlion
 */
public class BinaryBodyPostRequest extends BaseBodyHttpRequest<BinaryBodyPostRequest> {

    /**
     * 二进制流内容
     */
    private byte[] content;
    private MediaType mediaType;

    /**
     * 默认构造器
     *
     * @param url 请求地址
     */
    public BinaryBodyPostRequest(String url) {
        super(url);
    }


    /**
     * 设置二进制流
     *
     * @param inputStream 二进制流
     * @return {@link BinaryBodyPostRequest}
     * @see MediaType
     */
    public BinaryBodyPostRequest stream(InputStream inputStream) {
        Assert.notNull(inputStream, "In must not be null.");
        Assert.notNull(mediaType, "MediaType must not be null.");
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            if (IOUtils.copy(inputStream, outputStream) == -1) {
                throw new IOException("Copy failed");
            }
            this.content = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Reading stream failed->", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return this;
    }

    /**
     * 设置文件，转为文件流
     *
     * @param file 文件对象
     * @return {@link BinaryBodyPostRequest}
     */
    public BinaryBodyPostRequest file(File file) {
        Assert.notNull(file, "File must not be null.");
        String filename = file.getName();
        MediaType mediaType = Utils.guessMediaType(filename);
        try {
            this.stream(new FileInputStream(file));
            this.mediaType = mediaType;
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置请求内容类型
     *
     * @param contentType 请求内容类型
     * @return {@link BinaryBodyPostRequest}
     */
    public BinaryBodyPostRequest contentType(String contentType) {
        Assert.hasLength(contentType, "ContentType must not be null.");
        this.mediaType = MediaType.parse(contentType);
        return this;
    }

    /**
     * 设置请求内容类型
     *
     * @param contentType 请求内容类型
     * @return {@link BinaryBodyPostRequest}
     */
    public BinaryBodyPostRequest contentType(ContentType contentType) {
        Assert.notNull(contentType, "ContentType must not be null.");
        this.mediaType = MediaType.parse(contentType.toString());
        return this;
    }

    /**
     * 获取{@linkplain RequestBody}对象
     */
    @Override
    protected RequestBody generateRequestBody() {
        return RequestBody.create(this.mediaType, this.content);
    }

}
