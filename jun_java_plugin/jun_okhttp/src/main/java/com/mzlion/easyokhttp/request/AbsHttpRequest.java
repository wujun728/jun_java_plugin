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

import com.mzlion.core.io.IOUtils;
import com.mzlion.core.json.TypeRef;
import com.mzlion.core.lang.Assert;
import com.mzlion.core.lang.CollectionUtils;
import com.mzlion.core.lang.StringUtils;
import com.mzlion.easyokhttp.HttpClient;
import com.mzlion.easyokhttp.exception.HttpClientConfigException;
import com.mzlion.easyokhttp.exception.HttpClientException;
import com.mzlion.easyokhttp.exception.HttpStatusCodeException;
import com.mzlion.easyokhttp.http.Header;
import com.mzlion.easyokhttp.http.ProcessRequestBody;
import com.mzlion.easyokhttp.response.HttpResponse;
import com.mzlion.easyokhttp.response.callback.Callback;
import com.mzlion.easyokhttp.utils.SSLContexts;
import com.mzlion.easyokhttp.utils.Utils;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 2016-04-16 {@linkplain HttpRequest}的抽象实现，实现了大部分方法.
 * </p>
 *
 * @author mzlion on 2016-04-16
 */
@SuppressWarnings("unchecked")
public abstract class AbsHttpRequest<Req extends HttpRequest<Req>> implements HttpRequest<Req> {
    protected Logger logger = LoggerFactory.getLogger(AbsHttpRequest.class);
    protected String url;

    /**
     * 连接超时时间
     */
    private TimeoutHolder connectTimeout;
    /**
     * 流读取超时时间
     */
    private TimeoutHolder readTimeout;
    /**
     * 流写入超时时间
     */
    private TimeoutHolder writeTimeout;

    /**
     * SSL证书文件列表
     */
    private CertificateHolder certificateHolder = null;

    /**
     * 存储请求头信息
     */
    private Map<String, String> headers;

    private Map<String, List<String>> queryParams;

    /**
     * default constructor
     *
     * @param url 请求地址
     */
    AbsHttpRequest(String url) {
        this.url = url;
        this.headers = new LinkedHashMap<>();
        this.queryParams = new LinkedHashMap<>();

        //设置默认的User-Agent和Accept-Language
        this.header(Header.ACCEPT_LANGUAGE, Utils.getAcceptLanguage());
        this.header(Header.USER_AGENT, Utils.getUserAgent());
    }

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req url(String url) {
        this.url = url;
        return (Req) this;
    }


    //region===============为URL后面追加参数===============

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryString(String name, Number value) {
        return this.queryString(name, value == null ? null : value.toString());
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryString(String name, String value) {
        return this.queryString(name, value, false);
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name    参数名
     * @param value   参数值
     * @param replace 值为[@code true}则替换
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryString(String name, String value, boolean replace) {
        //Assert.hasLength(name, "Name must not be null.");
        if (StringUtils.isEmpty(name)) {
            return (Req) this;
        }
        if (!replace && value == null) {
            logger.warn(" ===> The value is null,ignore:name={},value=null", name);
            return (Req) this;
        }
        List<String> valueList = this.queryParams.get(name);
        if (valueList == null) {
            valueList = new LinkedList<>();
            this.queryParams.put(name, valueList);
        }
        if (replace) {
            valueList.clear();
        }
        valueList.add(value);
        return (Req) this;
    }

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param parameters 参数对
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req queryString(Map<String, String> parameters) {
        //Assert.notEmpty(parameters, "Parameters may not be null or empty.");
        if (CollectionUtils.isEmpty(parameters)) {
            return (Req) this;
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            this.queryString(entry.getKey(), entry.getValue());
        }
        return (Req) this;
    }
    //endregion===============为URL后面追加参数===============


    //region===============设置HTTP Header===============

    /**
     * 添加请求头信息
     *
     * @param name  请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req header(String name, String value) {
        //Assert.hasLength(name, "Name may not be null or empty.");
        //Assert.notNull(value, "Value may not be null.");
        if (StringUtils.hasLength(name) && null != value) {
            this.headers.put(name, value);
        }
        return (Req) this;
    }

    /**
     * 从请求头中移除键值
     *
     * @param name 请求头键名
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req removeHeader(String name) {
        //Assert.hasLength(name, "Name may noy be null or empty.");
        if (StringUtils.hasLength(name)) {
            this.headers.remove(name);
        }
        return (Req) this;
    }
    //endregion===============设置HTTP Header===============


    //region===============覆盖配置HttpClient===============

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独连接超时时间。调用此方法会重新创建{@linkplain OkHttpClient}。
     *
     * @param connectTimeout 连接超时时间
     * @param timeUnit       超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req connectTimeout(int connectTimeout, TimeUnit timeUnit) {
        if (connectTimeout < 0) {
            throw new HttpClientConfigException("Connect Timeout may be than 0.");
        }
        if (timeUnit == null) {
            throw new HttpClientConfigException("TimeUnit may not be null.");
        }
        this.connectTimeout = new TimeoutHolder(connectTimeout, timeUnit);
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独连接超时时间。调用此方法会重新创建{@linkplain OkHttpClient}。
     *
     * @param connectTimeout 连接超时时间
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req connectTimeout(int connectTimeout) {
        return connectTimeout(connectTimeout, TimeUnit.SECONDS);
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独读取流超时。
     *
     * @param readTimeout 流读取超时时间
     * @param timeUnit    超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req readTimeout(int readTimeout, TimeUnit timeUnit) {
        if (readTimeout < 0) {
            throw new HttpClientConfigException("Read Timeout may be than 0.");
        }
        if (timeUnit == null) {
            throw new HttpClientConfigException("TimeUnit may not be null.");
        }
        this.readTimeout = new TimeoutHolder(readTimeout, timeUnit);
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独读取流超时。
     *
     * @param readTimeout 流读取超时时间
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req readTimeout(int readTimeout) {
        return readTimeout(readTimeout, TimeUnit.SECONDS);
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独写入流超时。
     *
     * @param writeTimeout 流写入超时时间
     * @param timeUnit     超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req writeTimeout(int writeTimeout, TimeUnit timeUnit) {
        if (writeTimeout < 0) {
            throw new HttpClientConfigException("Write Timeout may be than 0.");
        }
        if (timeUnit == null) {
            throw new HttpClientConfigException("TimeUnit may not be null.");
        }
        this.writeTimeout = new TimeoutHolder(writeTimeout, timeUnit);
        return (Req) this;
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独写入流超时。
     *
     * @param writeTimeout 流写入超时时间
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req writeTimeout(int writeTimeout) {
        return writeTimeout(writeTimeout, TimeUnit.SECONDS);
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独SSL证书
     *
     * @param certificates SSL证书文件
     * @return 返回当前类{@link Req}的对象自己
     */
    @Override
    public Req customSSL(InputStream... certificates) {
        return customSSL(null, null, certificates);
    }

    /**
     * 为构建本次{@linkplain HttpRequest}设置SSL单向认证
     *
     * @param trustManager 证书管理器
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    @Override
    public Req customSSL(X509TrustManager trustManager) {
        return customSSL(null, null, trustManager);
    }

    /**
     * SSL双向认证
     *
     * @param pfxStream    客户端证书，支持P12的证书
     * @param pfxPwd       客户端证书密码
     * @param certificates 含有服务端公钥的证书
     * @return {@link Req}
     */
    @Override
    public Req customSSL(InputStream pfxStream, char[] pfxPwd, InputStream... certificates) {
        this.certificateHolder = new CertificateHolder(certificates, null, pfxStream, pfxPwd);
        return (Req) this;
    }

    /**
     * SSL双向认证
     *
     * @param pfxStream    客户端证书，支持P12的证书
     * @param pfxPwd       客户端证书密码
     * @param trustManager 证书管理器
     * @return {@link Req}
     */
    @Override
    public Req customSSL(InputStream pfxStream, char[] pfxPwd, X509TrustManager trustManager) {
        this.certificateHolder = new CertificateHolder(null, trustManager, pfxStream, pfxPwd);
        return (Req) this;
    }
    //endregion===============覆盖配置HttpClient===============


    //region===============增加了便捷转换方法===============

    /**
     * 将响应结果转为字符串
     *
     * @return 响应结果字符串
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    public String asString() {
        return execute().asString();
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
        return this.execute().asBean(targetClass);
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
        return this.execute().asBean(typeRef);
    }

    /**
     * 将响应结果转为字节数组
     *
     * @return 字节数组
     * @throws HttpClientException 如果服务器返回非200则抛出此异常
     */
    public byte[] asByteData() {
        return this.execute().asByteData();
    }

    /**
     * 将响应结果输出到文件中
     *
     * @param saveFile 目标保存文件,非空
     */
    public void asFile(File saveFile) {
        this.execute().asFile(saveFile);
    }

    /**
     * 将响应结果输出到输出流,并不会主动关闭输出流{@code out}
     *
     * @param out 输出流,非空
     */
    public void asStream(OutputStream out) {
        this.execute().asStream(out);
    }
    //endregion

    /**
     * 执行HTTP请求,获取响应结果
     *
     * @return 将响应结果转为具体的JavaBean
     */
    @Override
    public HttpResponse execute() {
        RequestBody requestBody = this.generateRequestBody();
        Request request = this.generateRequest(requestBody);
        Call call = this.generateCall(request);
        Response response = null;
        try {
            response = call.execute();
            ResponseBody responseBody = response.body();
            byte[] byteData = responseBody.bytes();

            Response newResponse = response.newBuilder()
                    .body(ResponseBody.create(responseBody.contentType(), byteData))
                    .build();

            HttpResponse httpResponse = new HttpResponse(newResponse);
            httpResponse.setErrorMessage(response.message());
            httpResponse.setHttpCode(response.code());
            if (response.code() < 200 || response.code() >= 300) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
                httpResponse.setSuccess(false);
                return httpResponse;
            } else {
                httpResponse.setSuccess(true);
            }
            return httpResponse;
        } catch (IOException e) {
            throw new HttpClientException(e);
        } finally {
            IOUtils.closeQuietly(response);
        }
    }

    /**
     * 异步执行请求
     *
     * @param callback 回调接口
     * @param <T>      数据类型
     */
    @Override
    public <T> void execute(Callback<T> callback) {
        Callback<T> _cb = callback;
        if (_cb == null) {
            _cb = Callback.EMPTY_CALLBACK;
        }
        if (!callback.onBefore(this)) {
            return;
        }
        final Callback<T> finalCb = _cb;

        ProcessRequestBody processRequestBody = new ProcessRequestBody(this.generateRequestBody(), new ProcessRequestBody.Listener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength) {
                finalCb.postProgress(bytesWritten, contentLength, 1.0f * bytesWritten / contentLength);
            }
        });
        Call call = this.generateCall(this.generateRequest(processRequestBody));
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
                finalCb.onError(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finalCb.onComplete(response);
                if (response.code() >= 200 && response.code() < 300) {
                    try {
                        finalCb.onSuccess(finalCb.getDataHandler() == null ? null : finalCb.getDataHandler().handle(response));
                    } finally {
                        response.close();
                    }
                } else {
                    if (!call.isCanceled()) {
                        call.cancel();
                    }
                    finalCb.onError(call, new HttpStatusCodeException(url, response.code(), response.message()));
                }
            }
        });
    }

    /**
     * 获取{@linkplain RequestBody}对象
     *
     * @return {@linkplain RequestBody}
     */
    protected abstract RequestBody generateRequestBody();

    /**
     * 根据不同的请求方式，将RequestBody转换成Request对象
     *
     * @param requestBody 请求体
     * @return {@link Request}
     * @see RequestBody
     */
    protected abstract Request generateRequest(RequestBody requestBody);

    /**
     * 执行请求调用
     *
     * @param request Request对象
     * @return {@linkplain Call}
     */
    private Call generateCall(Request request) {
        if (readTimeout == null && connectTimeout == null &&
                writeTimeout == null && certificateHolder == null) {
            return HttpClient.Instance.getOkHttpClient().newCall(request);
        }
        OkHttpClient.Builder builder = HttpClient.Instance.getOkHttpClientBuilder();
        if (connectTimeout != null) {
            builder.connectTimeout(connectTimeout.timeout, connectTimeout.timeUnit);
        }
        if (readTimeout != null) {
            builder.readTimeout(readTimeout.timeout, readTimeout.timeUnit);
        }
        if (writeTimeout != null) {
            builder.writeTimeout(writeTimeout.timeout, writeTimeout.timeUnit);
        }
        if (certificateHolder != null) {
            SSLContexts.SSLConfig sslConfig = SSLContexts.tryParse(certificateHolder.certificates,
                    certificateHolder.trustManager, certificateHolder.pfxStream, certificateHolder.pfxPwd);
            builder.sslSocketFactory(sslConfig.getSslSocketFactory(), sslConfig.getX509TrustManager());
        }
        return builder.build().newCall(request);
    }

    void collectHeader(Request.Builder builder, HttpUrl httpUrl) {
        //加载默认Header
        Map<String, String> defaultHeaders = HttpClient.Instance.getDefaultHeaders(httpUrl);
        if (CollectionUtils.isNotEmpty(defaultHeaders)) {
            for (Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 构建URL地址
     */
    String buildUrl() {
        Assert.hasLength(url, "Url must not be null.");
        StringBuilder sb = new StringBuilder(url);
        if (url.contains("?")) {
            sb.append("&");
        } else {
            sb.append("?");
        }
        if (CollectionUtils.isNotEmpty(queryParams)) {
            for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                for (String value : entry.getValue()) {
                    sb.append(entry.getKey()).append("=")
                            .append(Utils.urlEncode(value)).append("&");
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    final class CertificateHolder {

        InputStream[] certificates;
        X509TrustManager trustManager;
        InputStream pfxStream;
        char[] pfxPwd;

        CertificateHolder(InputStream[] certificates, X509TrustManager trustManager,
                          InputStream pfxStream, char[] pfxPwd) {
            this.certificates = certificates;
            this.trustManager = trustManager;
            this.pfxStream = pfxStream;
            this.pfxPwd = pfxPwd;
        }
    }

    final class TimeoutHolder {
        int timeout;
        TimeUnit timeUnit;

        TimeoutHolder(int timeout, TimeUnit timeUnit) {
            this.timeout = timeout;
            this.timeUnit = timeUnit;
        }
    }
}
