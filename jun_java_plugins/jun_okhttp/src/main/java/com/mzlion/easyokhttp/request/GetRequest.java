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

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * <p>
 * 2016-04-16 HTTP的GET请求对象
 * </p>
 *
 * @author mzlion
 */
public class GetRequest extends AbsHttpRequest<GetRequest> {

    /**
     * 构造GET请求对象
     *
     * @param url 请求的URL地址
     */
    public GetRequest(String url) {
        super(url);
    }

    /**
     * 获取{@linkplain RequestBody}对象
     */
    @Override
    protected RequestBody generateRequestBody() {
        return null;
    }

    /**
     * 根据不同的请求方式，将RequestBody转换成Request对象
     *
     * @param requestBody 请求体
     * @return {@link Request}
     * @see RequestBody
     */
    @Override
    protected Request generateRequest(RequestBody requestBody) {
        Request.Builder builder = new Request.Builder();
        HttpUrl httpUrl = HttpUrl.parse(this.buildUrl());
        builder.url(httpUrl);
        this.collectHeader(builder, httpUrl);
        return builder.build();
    }

}
