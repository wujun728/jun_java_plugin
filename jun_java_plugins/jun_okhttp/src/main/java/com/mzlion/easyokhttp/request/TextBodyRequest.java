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

import com.mzlion.core.json.gson.JsonUtil;
import com.mzlion.core.lang.Assert;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 提交文本字符串，服务端应该从Request请求体获取文本字符串。
 * </p>
 *
 * @author mzlion
 */
public class TextBodyRequest extends BaseBodyHttpRequest<TextBodyRequest> {

    private String content;
    private String _type;
    private Charset charset;

    /**
     * 默认构造器
     *
     * @param url 请求地址
     */
    public TextBodyRequest(String url) {
        super(url);
        this.charset = StandardCharsets.UTF_8;
    }

    /**
     * POST提交一段j文本内容
     *
     * @param text 文本字符串
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest text(String text) {
        this.content = text == null ? "" : text;
        this._type = "text/plain";
        return this;
    }

    /**
     * POST提交一段json字符串
     *
     * @param json json字符串
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest json(String json) {
        Assert.hasLength(json, "Json may not be null.");
        this.content = json;
        this._type = "application/json";
        return this;
    }

    /**
     * POST提交一段json字符串
     *
     * @param value Java对象
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest json(Object value) {
        Assert.notNull(value, "Value may not be null.");
        this.content = JsonUtil.toJson(value);
        this._type = "application/json";
        return this;
    }

    /**
     * POST提交一段xml代码
     *
     * @param xml xml字符串
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest xml(String xml) {
        Assert.hasLength(xml, "Xml may not be null.");
        this.content = xml;
        this._type = "application/xml";
        return this;
    }

    /**
     * POST提交一段html代码
     *
     * @param html html字符串
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest html(String html) {
        Assert.hasLength(html, "Html may not be null.");
        this.content = html;
        this._type = "application/html";
        return this;
    }

    /**
     * POST提交一段javascript代码
     *
     * @param javascript 字符串
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest javascript(String javascript) {
        Assert.hasLength(javascript, "Javascript may not be null.");
        this.content = javascript;
        this._type = "application/javascript";
        return this;
    }

    /**
     * 设置字符集
     *
     * @param charset 字符编码
     * @return {@link TextBodyRequest}
     */
    public TextBodyRequest charset(String charset) {
        Assert.hasLength(charset, "Charset may not be null.");
        this.charset = Charset.forName(charset);
        return this;
    }

    /**
     * 获取{@linkplain RequestBody}对象
     */
    @Override
    protected RequestBody generateRequestBody() {
        MediaType contentType = MediaType.parse(String.format("%s; charset=%s", this._type, this.charset));
        return RequestBody.create(contentType, this.content);
    }

}
