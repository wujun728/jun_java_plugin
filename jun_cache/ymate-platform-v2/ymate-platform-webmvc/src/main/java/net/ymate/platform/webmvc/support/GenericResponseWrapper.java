/*
 * Copyright 2007-2017 the original author or authors.
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
package net.ymate.platform.webmvc.support;

import net.ymate.platform.core.lang.PairObject;
import net.ymate.platform.webmvc.base.Type;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 刘镇 (suninformation@163.com) on 16/3/10 下午10:20
 * @version 1.0
 */
public class GenericResponseWrapper extends HttpServletResponseWrapper {

    private int statusCode = SC_OK;

    private int contentLength;
    private String contentType;
    private final Map<String, PairObject<Type.HeaderType, Object>> headersMap = new TreeMap<String, PairObject<Type.HeaderType, Object>>(String.CASE_INSENSITIVE_ORDER);

    public GenericResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void setStatus(final int code) {
        statusCode = code;
        super.setStatus(code);
    }

    public void setStatus(final int code, final String msg) {
        statusCode = code;
        super.setStatus(code);
    }

    public int getStatus() {
        return statusCode;
    }

    public void sendError(int i, String string) throws IOException {
        statusCode = i;
        super.sendError(i, string);
    }

    public void sendError(int i) throws IOException {
        statusCode = i;
        super.sendError(i);
    }

    public void sendRedirect(String string) throws IOException {
        statusCode = HttpServletResponse.SC_MOVED_TEMPORARILY;
        super.sendRedirect(string);
    }

    public void reset() {
        super.reset();
        headersMap.clear();
        statusCode = SC_OK;
        contentType = null;
        contentLength = 0;
    }

    public void setContentLength(final int length) {
        this.contentLength = length;
        super.setContentLength(length);
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentType(final String type) {
        this.contentType = type;
        super.setContentType(type);
    }

    public String getContentType() {
        return contentType;
    }

    @Override
    public void addHeader(String name, String value) {
        PairObject<Type.HeaderType, Object> values = this.headersMap.get(name);
        if (values == null) {
            values = new PairObject<Type.HeaderType, Object>();
            values.setKey(Type.HeaderType.STRING);
            this.headersMap.put(name, values);
        }
        values.setValue(value);

        super.addHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        PairObject<Type.HeaderType, Object> values = new PairObject<Type.HeaderType, Object>();
        values.setKey(Type.HeaderType.STRING);
        values.setValue(value);
        this.headersMap.put(name, values);
        //
        super.setHeader(name, value);
    }

    @Override
    public void addDateHeader(String name, long date) {
        PairObject<Type.HeaderType, Object> values = this.headersMap.get(name);
        if (values == null) {
            values = new PairObject<Type.HeaderType, Object>();
            values.setKey(Type.HeaderType.DATE);
            this.headersMap.put(name, values);
        }
        values.setValue(date);
        //
        super.addDateHeader(name, date);
    }

    @Override
    public void setDateHeader(String name, long date) {
        PairObject<Type.HeaderType, Object> values = new PairObject<Type.HeaderType, Object>();
        values.setKey(Type.HeaderType.DATE);
        values.setValue(date);
        this.headersMap.put(name, values);
        //
        super.setDateHeader(name, date);
    }

    @Override
    public void addIntHeader(String name, int value) {
        PairObject<Type.HeaderType, Object> values = this.headersMap.get(name);
        if (values == null) {
            values = new PairObject<Type.HeaderType, Object>();
            values.setKey(Type.HeaderType.INT);
            this.headersMap.put(name, values);
        }
        values.setValue(value);
        //
        super.addIntHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        PairObject<Type.HeaderType, Object> values = new PairObject<Type.HeaderType, Object>();
        values.setKey(Type.HeaderType.INT);
        values.setValue(value);
        this.headersMap.put(name, values);
        //
        super.setIntHeader(name, value);
    }

    public Map<String, PairObject<Type.HeaderType, Object>> getHeaders() {
        return headersMap;
    }
}
