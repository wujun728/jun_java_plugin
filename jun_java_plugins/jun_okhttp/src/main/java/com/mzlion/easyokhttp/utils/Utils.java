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
package com.mzlion.easyokhttp.utils;

import com.mzlion.core.digest.MD5;
import com.mzlion.core.lang.StringUtils;
import com.mzlion.easyokhttp.http.Header;
import okhttp3.MediaType;
import okhttp3.Response;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * 项目的工具类
 *
 * @author mzlion on 2016/12/9.
 */
public class Utils {

    private static String acceptLanguage;
    private static String userAgent;

    /**
     * 自定义客户端语言
     *
     * @param acceptLanguage 客户端语言
     */
    public static void setAcceptLanguage(String acceptLanguage) {
        Utils.acceptLanguage = acceptLanguage;
    }

    /**
     * 客户端语言
     *
     * @return 客户端语言
     */
    public static String getAcceptLanguage() {
        if (StringUtils.isEmpty(acceptLanguage)) {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            String country = locale.getCountry();
            StringBuffer sb = new StringBuffer(language);
            if (StringUtils.hasLength(country)) {
                sb.append('-').append(country).append(',').append(language).append(";q=0.8");
            }
            acceptLanguage = sb.toString();
        }
        return acceptLanguage;
    }

    /**
     * 获取UA
     *
     * @return UA
     */
    public static String getUserAgent() {
        if (StringUtils.isEmpty(userAgent)) {
            userAgent = "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36";
        }
        return userAgent;
    }

    /**
     * 对URL中的请求参数UTF-8编码
     *
     * @param value 参数值
     * @return 编码的值
     */
    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return value;
        }
    }

    /**
     * 猜测文件的MIME类型
     *
     * @param filename 文件名
     * @return {@link MediaType}
     */
    public static MediaType guessMediaType(String filename) {
        if (filename == null) {
            return MediaType.parse("application/octet-stream");
        }
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String pathname = filename.replace("#", "");
        String contentType = fileNameMap.getContentTypeFor(pathname);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }

    /**
     * 根据响应头或url获取文件名，当都无法获取时则根据url生成MD5值
     *
     * @param response HTTP响应对象
     * @return 文件名
     */
    public static String getFilename(Response response) {
        //首先从请求头里获取
        String headerValue = response.header(Header.CONTENT_DISPOSITION);
        if (headerValue != null) {
            String split = "filename=";
            int index = headerValue.indexOf(split);
            if (index != -1) {
                String pathname = headerValue.substring(index + split.length());
                index = pathname.lastIndexOf("/");
                if (index != -1) {
                    return pathname.substring(index + 1);
                }
                return pathname;
            }
        }

        //然后从url里获取
        String url = response.request().url().toString();
        int index = url.indexOf("?");
        if (index > 0) {
            String urlSnippet = url.substring(0, index);
            String filename = urlSnippet.substring(url.lastIndexOf("/") + 1);
            if (filename.contains(".")) {
                return filename;
            }
        }

        //通过URL生成一个随机文件名
        return MD5.digestHex(url);
    }
}
