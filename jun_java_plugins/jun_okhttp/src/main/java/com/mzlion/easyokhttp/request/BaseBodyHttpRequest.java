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
 * POST请求基类
 *
 * @author mzlion  on 2016/12/9.
 */
abstract class BaseBodyHttpRequest<Req extends HttpRequest<Req>> extends AbsHttpRequest<Req> {

    BaseBodyHttpRequest(String url) {
        super(url);
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
        builder.post(requestBody);
        return builder.build();
    }
}
