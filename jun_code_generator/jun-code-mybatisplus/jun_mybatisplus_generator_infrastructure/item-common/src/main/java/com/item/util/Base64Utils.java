/**
 * Base64Utils 2019年4月9日
 * @project item-common  V1.0
 * Copyright(c) 2019 flying-cattle Co. Ltd. 
 * All right reserved. 
 */
package com.item.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Copyright: Copyright (c) 2019
 *
 * <p>说明： Base64工具类-javabase64-1.3.1.jar</P>
 *
 * @version: V1.0
 * @author:  flying-cattle
 * <p>
 * Modification History:
 * Date         Author         Version         Description
 * -------------------------------------------------------*
 * 2019/4/9 	flying-cattle  V1.0            initialize
 * </p>
 */
public class Base64Utils {

    /**
     * <p>文件读取缓冲区大小</p>
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     * 
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.getDecoder().decode(base64);
    }

    /**
     * <p>二进制数据编码为BASE64字符串</p>
     * 
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.getEncoder().encode(bytes));
    }

    /**
     * <p>  将文件编码为BASE64字符串 </p>
     * <p> 大文件慎用，可能会导致内存溢出 </p>
     * 
     * @param filePath
     * <p>文件绝对路径</p>
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /**
     * <p>
     * BASE64字符串转回文件
     * </p>
     * 
     * @param filePath
     * <p> 文件绝对路径</p>
     * @param base64
     * <p>编码字符串</p>
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * <p>
     * 文件转换为二进制数组
     * </p>
     * 
     * @param filePath
     *            文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }

    /**
     * <p>
     * 二进制数据写文件
     * </p>
     * 
     * @param bytes
     *            二进制数据
     * @param filePath
     *            文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }

}
