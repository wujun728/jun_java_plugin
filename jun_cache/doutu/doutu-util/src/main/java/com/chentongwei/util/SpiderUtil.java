package com.chentongwei.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 爬虫工具类
 *
 * @author TongWei.Chen 2017-06-21 13:00:33
 */
public final class SpiderUtil {
    private static final Logger THREAD_LOG = LoggerFactory.getLogger(SpiderUtil.class);

    public static final byte[] getImageFromNetByUrl(final String strUrl) {
        try {
            URL url = new URL("http:" + strUrl);
            if(strUrl.startsWith("http:")) {
                url = new URL(strUrl);
            }
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            THREAD_LOG.info("爬虫的getImageFromNetByUrl方法报错：", e.getMessage());
        }
        return null;
    }

    public static final byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}
