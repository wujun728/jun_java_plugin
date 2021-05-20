package com.jun.plugin.gzip.springboot.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 14:30
 */
public class CompressUtils {

    private static final String GZIP_ENCODE_UTF_8 = "UTF-8";
    private static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    private static final int BYTE_NUM = 256;

    /**
     *
     * compress:压缩数据方法 <br/>
     * @param str
     *          String
     * @return
     *          String
     * @throws IOException
     *                 IOException
     */
    public static String compress(String str) throws IOException {
        return new String(compressData(str, GZIP_ENCODE_UTF_8), GZIP_ENCODE_ISO_8859_1);
    }
//    public static byte[] compress(String str) throws IOException {
//        return compressData(str, GZIP_ENCODE_UTF_8);
//    }

    /**
     *
     * decompressToStr:解压缩数据方法 <br/>
     * @param byteStr
     *          String
     * @return
     *          String
     */
    public static String decompressToStr(String byteStr) {
        byte[] bytes;
        try {
            bytes = byteStr.getBytes(GZIP_ENCODE_ISO_8859_1);
            return decompressDataToStr(bytes, GZIP_ENCODE_UTF_8);
        } catch (UnsupportedEncodingException e) {
            System.out.println("解压缩数据转码失败：" + e.getMessage());
        }
        return null;
    }

    /**
     *
     * compressData:压缩数据具体处理方法 <br/>
     * @param str
     *          String
     * @param encoding
     *          String
     * @return
     *          byte[]
     */
    public static byte[] compressData(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            System.out.println("压缩数据失败：" + e.getMessage());
        }
        return out.toByteArray();
    }

    /**
     *
     * decompressDataToStr:解压缩数据具体处理方法 <br/>
     * @param bytes
     *          byte[]
     * @param encoding
     *          String
     * @return
     *          String
     */
    public static String decompressDataToStr(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[BYTE_NUM];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            System.out.println("解压缩数据失败：" + e.getMessage());
        }
        return null;
    }

}
