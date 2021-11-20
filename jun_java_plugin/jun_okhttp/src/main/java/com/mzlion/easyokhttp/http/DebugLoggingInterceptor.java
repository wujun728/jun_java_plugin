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

import okhttp3.*;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 日志打印
 *
 * @author mzlion on 2017/1/24.
 */
public enum DebugLoggingInterceptor implements Interceptor {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(DebugLoggingInterceptor.class);

    /**
     * 日志级别
     */
    private Level loggingLevel;

    DebugLoggingInterceptor() {
        loggingLevel = Level.NONE;
    }

    public void setLoggingLevel(Level loggingLevel) {
        this.loggingLevel = loggingLevel == null ? Level.NONE : loggingLevel;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        loggingRequest(request);

        long startTime = System.nanoTime();
        Response response = chain.proceed(request);
        long usedTimes = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        loggingResponse(response, usedTimes);

        return response;
    }

    private void loggingResponse(Response response, long usedTimes) throws IOException {
        if (loggingLevel == Level.NONE || loggingLevel == Level.REQUEST) {
            return;
        }
        boolean bodyPrint = loggingLevel == Level.RESPONSE || loggingLevel == Level.BASIC || loggingLevel == Level.ALL,
                headerPrint = loggingLevel == Level.RESPONSE || loggingLevel == Level.HEADER || loggingLevel == Level.ALL;
        HttpUrl httpUrl = response.request().url();
        ResponseBody responseBody = response.peekBody(1024 << 1);

        if (loggingLevel == Level.RESPONSE) {
            logger.info(" |=== Start to print the connection[{}] data ===|", httpUrl.toString());
        }
        logger.info(" |=== The request executes over,http statusCode is {},http message is {},taking {} ms",
                response.code(), response.message(), usedTimes);
        if (headerPrint) {
            logger.info(" |=== Start to print response headers === |");
            Headers headers = response.headers();
            for (String name : headers.names()) {
                logger.info(" |=== {} : {} ===|", name, headers.get(name));
            }
            logger.info(" |=== Finish to print response headers === |");
        }
        if (bodyPrint && HttpHeaders.hasBody(response)) {
            if (isPlainText(responseBody.contentType())) {
                logger.info(" |=== Start to print response body === |");
                logger.info(" |=== The body data is \n{}", responseBody.string());
                logger.info(" |=== Finish to print response body === |");
            } else {
                logger.warn(" |=== The response body may contains 'file' part, ignore to print! ===|");
            }
        }
        logger.info(" |=== Finish to print the connection[{}] data ===|\n", httpUrl.toString());
    }

    private void loggingRequest(Request request) throws IOException {
        if (loggingLevel == Level.NONE || loggingLevel == Level.RESPONSE) {
            return;
        }
        boolean bodyPrint = loggingLevel == Level.REQUEST || loggingLevel == Level.BASIC || loggingLevel == Level.ALL,
                headerPrint = loggingLevel == Level.REQUEST || loggingLevel == Level.HEADER || loggingLevel == Level.ALL;
        HttpUrl httpUrl = request.url();
        logger.info(" |=== Start to print the connection[{}] data ===|", httpUrl.toString());
        if (headerPrint) {
            logger.info(" |=== Start to print request headers === |");
            Headers headers = request.headers();
            for (String name : headers.names()) {
                logger.info(" |=== {} : {} ===|", name, headers.get(name));
            }
            logger.info(" |=== Finish to print request headers === |");
        }
        if (bodyPrint) {
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                if (isPlainText(requestBody.contentType())) {
                    logger.info(" |=== Start to print request body === |");
                    Request copy = request.newBuilder().build();
                    requestBody = copy.body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    MediaType mediaType = requestBody.contentType();
                    logger.info(" |=== The body data is {} ===|", buffer.readString(mediaType.charset(Util.UTF_8)));
                    logger.info(" |=== Finish to print request body === |");
                } else {
                    logger.warn(" |=== The request body may contains 'file' part, ignore to print! ===|");
                }
            }
        }
        if (loggingLevel == Level.REQUEST) {
            logger.info(" |=== Finish to print the connection[{}] data ===|\n", httpUrl.toString());
        }
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     *
     * @param mediaType The content type
     */
    private boolean isPlainText(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") ||
                    subtype.contains("xml") || subtype.contains("html")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 定义日志打印级别
     *
     * @author mzlion on 2017/1/24.
     */
    public enum Level {

        /**
         * 不打印日志
         */
        NONE,

        /**
         * 打印请求参数和响应结果
         */
        BASIC,

        /**
         * 打印请求Header和响应Header
         */
        HEADER,

        /**
         * 打印请求参数和header
         */
        REQUEST,

        /**
         * 打印响应结果和header
         */
        RESPONSE,

        /**
         * 打印所有信息
         */
        ALL,
    }


}
