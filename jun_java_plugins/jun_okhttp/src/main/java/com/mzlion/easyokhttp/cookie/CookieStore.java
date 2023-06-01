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
package com.mzlion.easyokhttp.cookie;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * 定义Cookie存储机制
 *
 * @author mzlion on 2016/4/17.
 */
public interface CookieStore {

    /**
     * 为请求地址{@code url}增加Cookie
     *
     * @param uri     请求地址
     * @param cookies Cookie列表
     * @see Cookie
     */
    void add(HttpUrl uri, List<Cookie> cookies);

    /**
     * 获取某个请求地址的Cookie列表
     *
     * @param uri 请求地址
     * @return Cookie列表
     * @see Cookie
     */
    List<Cookie> get(HttpUrl uri);

    /**
     * 获取所有Cookie列表
     *
     * @return {@link Cookie}
     */
    List<Cookie> getCookies();

    /**
     * 删除请求的某个Cookie
     *
     * @param uri    请求地址
     * @param cookie Cookie对象
     * @return 删除成功则返回{@code true}，否则返回{@code false}
     */
    boolean remove(HttpUrl uri, Cookie cookie);

    /**
     * 清空所有Cookie列表
     *
     * @return 清空成功则返回{@code true}，否则返回{@code false}
     */
    boolean removeAll();

}
