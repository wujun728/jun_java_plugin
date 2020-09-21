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
package net.ymate.platform.webmvc.view.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.view.AbstractView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JSON视图
 *
 * @author 刘镇 (suninformation@163.com) on 2011-10-23 上午11:27:16
 * @version 1.0
 */
public class JsonView extends AbstractView {

    protected Object __jsonObj;
    protected boolean __withContentType;
    protected String __jsonCallback;
    protected boolean __keepNullValue;
    protected boolean __quoteFieldNames;
    protected boolean __useSingleQuotes;

    public static JsonView bind(Object obj) {
        if (obj instanceof String) {
            return new JsonView((String) obj);
        } else {
            return new JsonView(obj);
        }
    }

    /**
     * 构造器
     *
     * @param obj Java对象
     */
    public JsonView(Object obj) {
        __jsonObj = JSON.toJSON(obj);
    }

    /**
     * 构造器
     *
     * @param jsonStr JSON字符串
     */
    public JsonView(String jsonStr) {
        __jsonObj = JSON.parse(jsonStr);
    }

    /**
     * @return 设置ContentType为"application/json"或"text/javascript"，默认为空
     */
    public JsonView withContentType() {
        __withContentType = true;
        return this;
    }

    /**
     * @param callback 回调方法名称
     * @return 设置并采用JSONP方式输出，回调方法名称由参数callback指定，若callback参数无效则不启用
     */
    public JsonView withJsonCallback(String callback) {
        __jsonCallback = StringUtils.defaultIfEmpty(callback, null);
        return this;
    }

    /**
     * @return 设置是否保留空值属性
     */
    public JsonView keepNullValue() {
        __keepNullValue = true;
        return this;
    }

    /**
     * @return 设置JSON属性KEY使用引号
     */
    public JsonView quoteFieldNames() {
        __quoteFieldNames = true;
        return this;
    }

    /**
     * @return 设置JSON属性KEY使用单引号
     */
    public JsonView useSingleQuotes() {
        __useSingleQuotes = true;
        return this;
    }

    /**
     * @return 将视图数据对象转换为JSON字符串
     */
    protected String __doObjectToJsonString() {
        List<SerializerFeature> _features = new ArrayList<SerializerFeature>();
        if (__quoteFieldNames) {
            _features.add(SerializerFeature.QuoteFieldNames);
            if (__useSingleQuotes) {
                _features.add(SerializerFeature.UseSingleQuotes);
            }
        }
        if (__keepNullValue) {
            _features.addAll(Arrays.asList(
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero));
        }
        return JSON.toJSONString(__jsonObj, _features.toArray(new SerializerFeature[_features.size()]));
    }

    protected void __doRenderView() throws Exception {
        HttpServletResponse _response = WebContext.getResponse();
        if (StringUtils.isNotBlank(getContentType())) {
            _response.setContentType(getContentType());
        } else if (this.__withContentType) {
            if (__jsonCallback == null) {
                _response.setContentType(Type.ContentType.JSON.getContentType());
            } else {
                _response.setContentType(Type.ContentType.JAVASCRIPT.getContentType());
            }
        }
        StringBuilder _jsonStr = new StringBuilder(__doObjectToJsonString());
        if (__jsonCallback != null) {
            _jsonStr.insert(0, __jsonCallback + "(").append(");");
        }
        IOUtils.write(_jsonStr.toString(), _response.getOutputStream(), _response.getCharacterEncoding());
    }

    @Override
    public void render(OutputStream output) throws Exception {
        StringBuilder _jsonStr = new StringBuilder(__doObjectToJsonString());
        if (__jsonCallback != null) {
            _jsonStr.insert(0, __jsonCallback + "(").append(");");
        }
        IOUtils.write(_jsonStr, output, WebContext.getResponse().getCharacterEncoding());
    }
}
