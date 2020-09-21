package org.itkk.udf.qrcode.utils;

import lombok.extern.slf4j.Slf4j;
import org.itkk.udf.core.exception.SystemRuntimeException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpUtil
 */
@Slf4j
public class HttpUtil {

    /**
     * 私有化构造函数
     */
    private HttpUtil() {

    }

    /**
     * 加载网络文件
     *
     * @param path   路径
     * @param method 请求方法
     * @return 流
     */
    public static InputStream load(String path, String method) {
        InputStream inputStream = null;
        HttpURLConnection httpUrlConnection;
        try {
            URL url = new URL(path);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            final int connectTimeout = 3000;
            httpUrlConnection.setConnectTimeout(connectTimeout);
            // 设置应用程序要从网络连接读取数据
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestMethod(method);
            int responseCode = httpUrlConnection.getResponseCode();
            if (responseCode == HttpStatus.OK.value()) {
                inputStream = httpUrlConnection.getInputStream();
            }
        } catch (IOException e) {
            throw new SystemRuntimeException(e);
        }
        return inputStream;
    }
}
