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
package com.mzlion.easyokhttp.response.handle;

import com.mzlion.core.json.TypeRef;
import com.mzlion.core.json.gson.JsonUtil;
import com.mzlion.core.lang.StringUtils;
import okhttp3.Response;

import java.io.IOException;

/**
 * JSON处理器,将得到的JSON字符串转为JavaBean.主要必须指定{@code delegateClass}和{@code typeRef}其中一个值，否则返回结果为{@code null}
 *
 * @param <T> 泛型类型
 * @author mzlion on 2016/12/14.
 */
public class JsonDataHandler<T> implements DataHandler<T> {

    /**
     * 指定类型
     */
    private Class<T> delegateClass;

    /**
     * 携带泛型信息的类型
     */
    private TypeRef<T> typeRef;

    public JsonDataHandler() {
    }

    public JsonDataHandler(Class<T> delegateClass) {
        this.delegateClass = delegateClass;
    }

    public JsonDataHandler(TypeRef<T> typeRef) {
        this.typeRef = typeRef;
    }

    public JsonDataHandler(Class<T> delegateClass, TypeRef<T> typeRef) {
        this.delegateClass = delegateClass;
        this.typeRef = typeRef;
    }

    public Class<T> getDelegateClass() {
        return delegateClass;
    }

    public void setDelegateClass(Class<T> delegateClass) {
        this.delegateClass = delegateClass;
    }

    public TypeRef<T> getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(TypeRef<T> typeRef) {
        this.typeRef = typeRef;
    }

    /**
     * 得到相应结果后,将相应数据转为需要的数据格式
     *
     * @param response 需要转换的对象
     * @return 转换结果
     * @throws IOException 出现异常
     */
    @Override
    public T handle(final Response response) throws IOException {
        StringDataHandler stringDataHandler = StringDataHandler.create();
        String valueContent = stringDataHandler.handle(response);
        if (StringUtils.hasLength(valueContent)) {
            if (null != this.delegateClass) {
                return JsonUtil.fromJson(valueContent, delegateClass);
            } else if (null != this.typeRef) {
                return JsonUtil.fromJson(valueContent, this.typeRef);
            } else {
                return null;
            }
        }
        return null;
    }
}
