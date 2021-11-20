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

import com.mzlion.core.lang.CollectionUtils;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存缓存Cookie
 *
 * @author mzlionon 2016/4/17.
 */
public class MemoryCookieStore implements CookieStore {
    private final Map<String, List<Cookie>> allCookies = new ConcurrentHashMap<>();

    @Override
    public void add(HttpUrl uri, List<Cookie> cookies) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null.");
        }
        if (CollectionUtils.isEmpty(cookies)) {
            throw new NullPointerException("Cookies must not be null.");
        }
        List<Cookie> oldCookies = allCookies.get(uri.host());
        List<Cookie> deleteCookies = new ArrayList<>(oldCookies.size());
        for (Cookie cookie : cookies) {
            for (Cookie oldCookie : oldCookies) {
                if (oldCookie.name().equals(cookie.name())) {
                    deleteCookies.add(oldCookie);
                }
            }
        }
        oldCookies.removeAll(deleteCookies);
        oldCookies.addAll(cookies);
    }

    @Override
    public List<Cookie> get(HttpUrl uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null.");
        }
        List<Cookie> cookies = allCookies.get(uri.host());
        if (cookies == null) {
            cookies = new ArrayList<>();
            allCookies.put(uri.host(), cookies);
        }
        return cookies;
    }

    @Override
    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList<>(20);
        for (String host : allCookies.keySet()) {
            cookies.addAll(allCookies.get(host));
        }
        return cookies;
    }

    @Override
    public boolean remove(HttpUrl uri, Cookie cookie) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null.");
        }
        if (cookie == null) {
            throw new NullPointerException("Cookie must not be null.");
        }
        return allCookies.remove(uri.host()) != null;
    }

    @Override
    public boolean removeAll() {
        allCookies.clear();
        return true;
    }
}
