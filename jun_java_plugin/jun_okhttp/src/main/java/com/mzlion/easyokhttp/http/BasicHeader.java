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

import com.mzlion.core.lang.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 消息头
 *
 * @author mzlion on 2016/12/8.
 */
public class BasicHeader implements Header, Serializable, Cloneable {

    private static final long serialVersionUID = -6025489654253774764L;
    private final String name;
    private final String value;

    public BasicHeader(String name, String value) {
        Assert.hasLength(name, "name may not be null.");
        Assert.notNull(value, "Value may not be null.");
        this.name = name;
        this.value = value;
    }

    /**
     * Get the name of the Header.
     *
     * @return the name of the Header,  never {@code null}
     */
    @Override

    public String getName() {
        return name;
    }

    /**
     * Get the value of the Header.
     *
     * @return the value of the Header,  may be {@code null}
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Clone it.
     *
     * @see Cloneable
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Represent standard headeroutput
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        int length = this.name.length() + 2;
        if (this.value != null) length += this.value.length();
        StringBuilder result = new StringBuilder(length);
        result.append(this.name).append(" :");
        if (this.value != null) result.append(this.value);
        return result.toString();
    }

    public static StandardBuilder standard() {
        return StandardBuilder.create();
    }

    /**
     * 标准的消息头Builder
     *
     * @author mzlion
     */
    public static class StandardBuilder {

        private final List<String> headerFields;
        private final List<String> headerValues;

        StandardBuilder() {
            this.headerFields = new ArrayList<>();
            this.headerValues = new ArrayList<>();
        }

        public static StandardBuilder create() {
            return new StandardBuilder();
        }

        public StandardBuilder accept(String value) {
            this.tryDo(Header.ACCEPT, value);
            return this;
        }

        public StandardBuilder acceptCharset(String value) {
            this.tryDo(Header.ACCEPT_CHARSET, value);
            return this;
        }

        public StandardBuilder acceptEncoding(String value) {
            this.tryDo(Header.ACCEPT_ENCODING, value);
            return this;
        }

        public StandardBuilder acceptLanguage(String value) {
            this.tryDo(Header.ACCEPT_LANGUAGE, value);
            return this;
        }

        public StandardBuilder acceptRanges(String value) {
            this.tryDo(Header.ACCEPT_RANGES, value);
            return this;
        }

        public StandardBuilder age(String value) {
            this.tryDo(Header.AGE, value);
            return this;
        }

        public StandardBuilder allow(String value) {
            this.tryDo(Header.ALLOW, value);
            return this;
        }

        public StandardBuilder cacheControl(String value) {
            this.tryDo(Header.CACHE_CONTROL, value);
            return this;
        }

        public StandardBuilder connection(String value) {
            this.tryDo(Header.CONNECTION, value);
            return this;
        }

        public StandardBuilder contentEncoding(String value) {
            this.tryDo(Header.CONTENT_ENCODING, value);
            return this;
        }

        public StandardBuilder contentLanguage(String value) {
            this.tryDo(Header.CONTENT_LANGUAGE, value);
            return this;
        }

        public StandardBuilder contentLength(String value) {
            this.tryDo(Header.CONTENT_LENGTH, value);
            return this;
        }

        public StandardBuilder contentLocation(String value) {
            this.tryDo(Header.CONTENT_LOCATION, value);
            return this;
        }

        public StandardBuilder contentMD5(String value) {
            this.tryDo(Header.CONTENT_MD5, value);
            return this;
        }

        public StandardBuilder contentRange(String value) {
            this.tryDo(Header.CONTENT_RANGE, value);
            return this;
        }

        public StandardBuilder contentType(String value) {
            this.tryDo(Header.CONTENT_TYPE, value);
            return this;
        }

        public StandardBuilder contentDisposition(String value) {
            this.tryDo(Header.CONTENT_DISPOSITION, value);
            return this;
        }

        public StandardBuilder userAgent(String value) {
            this.tryDo(Header.USER_AGENT, value);
            return this;
        }

        public List<Header> build() {
            List<Header> headerList = new LinkedList<>();
            Header header;
            for (int i = 0, size = this.headerFields.size(); i < size; i++) {
                header = new BasicHeader(this.headerFields.get(i), this.headerValues.get(i));
                headerList.add(header);
            }
            return headerList;
        }

        private void tryDo(String name, String value) {
            int index = this.headerFields.indexOf(name);
            if (index == -1) {
                this.headerFields.add(name);
                this.headerValues.set(this.headerFields.size() - 1, value);
            } else {
                this.headerValues.set(index, value);
            }
        }

    }
}
