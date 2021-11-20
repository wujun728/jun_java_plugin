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

import com.mzlion.core.lang.CollectionUtils;
import com.mzlion.core.lang.StringUtils;
import com.mzlion.easyokhttp.http.FileWrapper;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 实现表单提交
 *
 * @author mzlion on 2016/12/8.
 */
public class PostRequest extends BaseBodyHttpRequest<PostRequest> {

    private boolean isMultipart;

    private Map<String, List<String>> formParams;
    private Map<String, List<FileWrapper>> fileParams;

    public PostRequest(String url) {
        super(url);
        this.formParams = new LinkedHashMap<>();
        this.fileParams = new LinkedHashMap<>();
    }

    /**
     * 获取{@linkplain RequestBody}对象
     *
     * @return {@linkplain RequestBody}
     */
    @Override
    protected RequestBody generateRequestBody() {
        if (CollectionUtils.isEmpty(this.fileParams) && !this.isMultipart) {
            FormBody.Builder builder = new FormBody.Builder();
            if (CollectionUtils.isNotEmpty(this.formParams)) {
                for (Map.Entry<String, List<String>> entry : this.formParams.entrySet()) {
//                    if (CollectionUtils.isEmpty())
                    for (String value : entry.getValue()) {
                        builder.add(entry.getKey(), value);
                    }
                }
            }
            return builder.build();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (CollectionUtils.isNotEmpty(this.formParams)) {
            for (Map.Entry<String, List<String>> entry : this.formParams.entrySet()) {
                for (String value : entry.getValue()) {
                    builder.addFormDataPart(entry.getKey(), value);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(this.fileParams)) {
            for (Map.Entry<String, List<FileWrapper>> entry : this.fileParams.entrySet()) {
                for (FileWrapper fileWrapper : entry.getValue()) {
                    builder.addFormDataPart(entry.getKey(), fileWrapper.getFilename(), fileWrapper.requestBody());
                }
            }
        }
        return builder.build();
    }

    /**
     * 是否强制开启文件上传(multipart/form-data)，如果框架检测到有文件上传则该方法设置无效
     *
     * @param isMultipart 如果值为{@code true}则开启，否则关闭
     * @return {@link RequestBody}
     */
    public PostRequest isMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
        return this;
    }


    /**
     * 设置提交的请求参数及其值
     *
     * @param name  参数名
     * @param value 参数值
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, String value) {
        return this.param(name, value, false);
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param name    参数名
     * @param value   参数值
     * @param replace 值为[@code true}则替换处理
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, String value, boolean replace) {
        //Assert.hasLength(name, "Name may not be null or empty.");
        if (StringUtils.isEmpty(name)) {
            logger.debug(" ===> The parameter[name] is null or empty.");
            return this;
        }
        if (!replace && value == null) {
            logger.warn(" ===> The value is null,ignore:name={},value=null", name);
            return this;
        }
        List<String> valueList = this.formParams.get(name);
        if (valueList == null) {
            valueList = new LinkedList<>();
            this.formParams.put(name, valueList);
        }
        if (replace) {
            valueList.clear();
        }

        valueList.add(value);
        return this;
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param parameters 键值对列表
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(Map<String, String> parameters) {
        //Assert.notEmpty(parameters, "Parameters may not be null.");
        if (CollectionUtils.isEmpty(parameters)) {
            logger.debug(" ===> The parameter[parameters] is null or empty.");
            return this;
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            this.param(entry.getKey(), entry.getValue(), false);
        }
        return this;
    }

    /**
     * 设置提交的文件
     *
     * @param name       参数名
     * @param uploadFile 上传的文件
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, File uploadFile) {
        return this.param(name, uploadFile, uploadFile.getName());
    }

    /**
     * 设置提交的文件
     *
     * @param name       参数名
     * @param uploadFile 上传的文件
     * @param filename   文件名
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, File uploadFile, String filename) {
        //Assert.hasLength(name, "Name may not be null.");
        //Assert.notNull(uploadFile, "UploadFile may not be null.");
        //Assert.hasLength(filename, "Filename may not be null or empty.");

        if (StringUtils.isEmpty(name)) {
            logger.debug(" ===> The parameter[name] is null or empty.");
            return this;
        }
        if (uploadFile == null) {
            logger.warn(" ===> The parameter[uploadFile] is null,ignore:name={}.", name);
            return this;
        }
        if (StringUtils.isEmpty(filename)) {
            logger.warn(" ===> The parameter[filename] is null,ignore:name={},uploadFile={}.", name, uploadFile);
            return this;
        }
        List<FileWrapper> fileWrapperList = this.fileParams.get(name);
        if (fileWrapperList == null) {
            fileWrapperList = new LinkedList<>();
            this.fileParams.put(name, fileWrapperList);
        }
        fileWrapperList.add(FileWrapper.create().file(uploadFile).filename(filename).build());
        return this;
    }

    /**
     * 设置提交的文件
     *
     * @param name        参数名
     * @param inputStream 上传数据流
     * @param streamName  数据流的标识
     * @return {@linkplain PostRequest}
     */
    public PostRequest param(String name, InputStream inputStream, String streamName) {
        //Assert.hasLength(name, "Name may not be null.");
        //Assert.notNull(inputStream, "InputStream may not be null.");
        //Assert.hasLength(streamName, "StreamName may not be null.");
        if (StringUtils.isEmpty(name)) {
            logger.debug(" ===> The parameter[name] is null or empty.");
            return this;
        }
        if (inputStream == null) {
            logger.warn(" ===> The parameter[inputStream] is null,ignore:name={}.", name);
            return this;
        }
        if (StringUtils.isEmpty(streamName)) {
            logger.warn(" ===> The parameter[streamName] is null,ignore:name={},inputStream={}.", name, inputStream);
            return this;
        }

        List<FileWrapper> fileWrapperList = this.fileParams.get(name);
        if (fileWrapperList == null) {
            fileWrapperList = new LinkedList<>();
            this.fileParams.put(name, fileWrapperList);
        }
        fileWrapperList.add(FileWrapper.create().stream(inputStream).filename(streamName).build());
        return this;
    }

}
