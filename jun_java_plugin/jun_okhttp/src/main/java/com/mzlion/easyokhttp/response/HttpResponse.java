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
package com.mzlion.easyokhttp.response;

import com.mzlion.core.io.IOUtils;
import com.mzlion.core.json.TypeRef;
import com.mzlion.core.lang.Assert;
import com.mzlion.easyokhttp.exception.HttpClientException;
import com.mzlion.easyokhttp.exception.HttpStatusCodeException;
import com.mzlion.easyokhttp.response.handle.DataHandler;
import com.mzlion.easyokhttp.response.handle.FileDataHandler;
import com.mzlion.easyokhttp.response.handle.JsonDataHandler;
import com.mzlion.easyokhttp.response.handle.StringDataHandler;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * <p>
 * Http响应结果处理类，通过{@linkplain #isSuccess()}可以得知服务器是否是200返回。
 * 并且该类提供了将结果转为其它对象的便捷方法，该类不再支持重复调用，重复会跑出异常。
 * </p>
 * 当响应结果状态码不是200时，而强制调用某个数据转换方法则会跑出异常{@linkplain HttpStatusCodeException}，
 * 如果项目需要判断Http Status Code来做一些特殊处理，可以捕获这个异常实现自己的逻辑。
 *
 * @author mzlion on 2016/4/17
 * @see DataHandler
 * @see HttpStatusCodeException
 */
public class HttpResponse implements Serializable {

    private transient final Response rawResponse;

    /**
     * 请求是否成功
     */
    private boolean isSuccess;

    /**
     * 请求失败时错误消息
     */
    private String errorMessage;

    /**
     * HTTP Status Code
     */
    private int httpCode;

    private byte[] byteData;//cache data

    public HttpResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
        try {
            this.byteData = this.rawResponse.body().bytes();
        } catch (IOException e) {
            throw new HttpClientException(e);
        } finally {
            IOUtils.closeQuietly(this.rawResponse);
        }
    }

    /**
     * 判断请求是否成功
     *
     * @return 成功则[@code true}否则为{@code false}
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    /**
     * 请求失败时错误消息
     *
     * @return 失败消息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 请求失败时的HTTP Status Code
     *
     * @return Http错误码
     */
    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Response getRawResponse() {
        if (rawResponse == null) {
            return rawResponse;
        }
        return rawResponse.newBuilder().body(ResponseBody.create(rawResponse.body().contentType(), byteData)).build();
    }

    /**
     * 将响应结果转为字符串
     *
     * @return 响应结果字符串
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    public String asString() {
        return this.custom(StringDataHandler.create());
    }

    /**
     * 将响应结果转为JavaBean对象
     *
     * @param targetClass 目标类型
     * @param <E>         泛型类型
     * @return JavaBean对象
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    public <E> E asBean(Class<E> targetClass) {
        return this.custom(new JsonDataHandler<>(targetClass));
    }

    /**
     * 将响应结果转为JavaBean对象
     * <p>
     * 用法如下：Map&lt;String,String&gt; data = httpResponse.asBean(new TypeRef&lt;Map&lt;String,String&gt;&gt;);
     * </p>
     *
     * @param typeRef 带有泛型类的封装类
     * @param <E>     泛型类型
     * @return JavaBean对象
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    public <E> E asBean(TypeRef<E> typeRef) {
        return this.custom(new JsonDataHandler<>(typeRef));
    }

    /**
     * 将响应结果转为字节数组
     *
     * @return 字节数组
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    public byte[] asByteData() {
        this.assertSuccess();
        return byteData;
    }

    /**
     * 将响应结果输出到文件中
     *
     * @param saveFile 目标保存文件,非空
     */
    public void asFile(File saveFile) {
        Assert.notNull(saveFile, "SaveFile may noy be null.");
        this.custom(new FileDataHandler(saveFile.getParent(), saveFile.getName()));
    }

    /**
     * 将响应结果输出到输出流,并不会主动关闭输出流{@code out}
     *
     * @param out 输出流,非空
     */
    public void asStream(OutputStream out) {
        Assert.notNull(out, "OutputStream is null.");
        this.assertSuccess();
        try {
            IOUtils.copy(this.rawResponse.body().byteStream(), out);
        } finally {
            IOUtils.closeQuietly(this.rawResponse);
        }
    }

    /**
     * 响应结果转换
     *
     * @param dataHandler 数据转换接口
     * @param <T>         期望转换的类型
     * @return 抓好结果
     */
    public <T> T custom(DataHandler<T> dataHandler) {
        return custom(dataHandler, true);
    }

    /**
     * 响应结果转换
     *
     * @param dataHandler   数据转换接口
     * @param <T>           期望转换的类型
     * @param checkHttpCode 是否检查状态码
     * @return 抓好结果
     */
    public <T> T custom(DataHandler<T> dataHandler, boolean checkHttpCode) {
        if (checkHttpCode) {
            this.assertSuccess();
        }
        try {
            return dataHandler.handle(getRawResponse());
        } catch (IOException e) {
            throw new HttpClientException(e);
        } finally {
            IOUtils.closeQuietly(this.rawResponse);
        }
    }

    private void assertSuccess() {
        if (!this.isSuccess) {
            throw new HttpStatusCodeException(this.rawResponse.request().url().toString(),
                    this.httpCode, this.errorMessage);
        }
    }
}
