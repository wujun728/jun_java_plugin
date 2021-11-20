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
package com.mzlion.easyokhttp.exception;

/**
 * Http Status Code exception,当code不是200时则会跑出本异常信息
 *
 * @author mzlion on 2016/12/15.
 */
public class HttpStatusCodeException extends HttpClientException {

    private static final long serialVersionUID = -1584716934177136972L;
    private final String url;
    private final int statusCode;
    private final String statusMessage;

    public HttpStatusCodeException(String url, int statusCode, String statusMessage) {
        super("Request url[=" + url + "] failed, status code is " + statusCode + ",status message is " + statusMessage);
        this.url = url;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    /**
     * 请求失败时的HTTP Status Code
     *
     * @return Http错误码
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 请求失败时错误消息
     *
     * @return 失败消息
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * 返回请求地址
     *
     * @return 请求地址
     */
    public String getUrl() {
        return url;
    }
}
