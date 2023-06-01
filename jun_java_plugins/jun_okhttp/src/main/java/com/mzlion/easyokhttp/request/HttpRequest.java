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

import com.mzlion.easyokhttp.response.HttpResponse;
import com.mzlion.easyokhttp.response.callback.Callback;

import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 2016-04-16 构建HTTP的请求对象,接口中的大部分方法均返回接口本身便于链式写法.
 * </p>
 *
 * @author mzlion
 */
public interface HttpRequest<Req extends HttpRequest<Req>> {

    /**
     * 设置请求地址
     *
     * @param url 请求地址
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req url(String url);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryString(String name, Number value);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name  参数名
     * @param value 参数值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryString(String name, String value);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param name    参数名
     * @param value   参数值
     * @param replace 值为[@code true}则替换
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryString(String name, String value, boolean replace);

    /**
     * 为url地址设置请求参数，即url?username=admin&nbsp;pwd=123
     *
     * @param parameters 参数对
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req queryString(Map<String, String> parameters);

    /**
     * 添加请求头信息
     *
     * @param key   请求头键名
     * @param value 请求头值
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req header(String key, String value);

    /**
     * 从请求头中移除键值
     *
     * @param key 请求头键名
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req removeHeader(String key);

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独连接超时时间。调用此方法会重新创建{@linkplain okhttp3.OkHttpClient}。
     *
     * @param connectTimeout 连接超时时间
     * @param timeUnit       超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req connectTimeout(int connectTimeout, TimeUnit timeUnit);

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独连接超时时间。调用此方法会重新创建{@linkplain okhttp3.OkHttpClient}。
     *
     * @param connectTimeout 连接超时时间,单位秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req connectTimeout(int connectTimeout);

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独读取流超时。
     *
     * @param readTimeout 流读取超时时间
     * @param timeUnit    超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req readTimeout(int readTimeout, TimeUnit timeUnit);

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独读取流超时。
     *
     * @param readTimeout 流读取超时时间,单位秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req readTimeout(int readTimeout);

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独写入流超时。
     *
     * @param writeTimeout 流写入超时时间
     * @param timeUnit     超时时间单位
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req writeTimeout(int writeTimeout, TimeUnit timeUnit);

    /**
     * 为构建本次{@linkplain HttpRequest}设置单独写入流超时。
     *
     * @param writeTimeout 流写入超时时间,单位秒
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req writeTimeout(int writeTimeout);

    /**
     * 为构建本次{@linkplain HttpRequest}设置SSL单向认证
     *
     * @param certificates SSL证书文件
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req customSSL(InputStream... certificates);

    /**
     * 为构建本次{@linkplain HttpRequest}设置SSL单向认证
     *
     * @param trustManager 证书管理器
     * @return 返回当前类{@linkplain Req}的对象自己
     */
    Req customSSL(X509TrustManager trustManager);

    /**
     * SSL双向认证
     *
     * @param pfxStream    客户端证书，支持P12的证书
     * @param pfxPwd       客户端证书密码
     * @param certificates 含有服务端公钥的证书
     * @return {@link Req}
     */
    Req customSSL(InputStream pfxStream, char[] pfxPwd, InputStream... certificates);

    /**
     * SSL双向认证
     *
     * @param pfxStream    客户端证书，支持P12的证书
     * @param pfxPwd       客户端证书密码
     * @param trustManager 证书管理器
     * @return {@link Req}
     */
    Req customSSL(InputStream pfxStream, char[] pfxPwd, X509TrustManager trustManager);

    /**
     * 执行HTTP请求,获取响应结果
     *
     * @return 将响应结果转为具体的JavaBean
     */
    HttpResponse execute();

    /**
     * 异步执行HTTP请求，
     *
     * @param callback 回调接口
     * @param <E>      数据类型
     */
    <E> void execute(Callback<E> callback);

}
