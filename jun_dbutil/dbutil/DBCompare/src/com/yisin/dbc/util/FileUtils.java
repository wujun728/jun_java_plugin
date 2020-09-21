package com.yisin.dbc.util;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 * 简述：文件操作常用工具方法类 详述：主要提供以下几个方法(可自定义扩展):
 * <li>创建文件夹</li>
 * <li>从文件全名中获取后缀名 ,并转换成小写</li>
 * </p>
 */
public class FileUtils {

    /**
     * <p>
     * 创建文件夹,判断目录是否存在，不存在则创建
     * </p>
     * 
     * @param dirPath
     *            文件夹完整路径
     * @return boolean true:创建目录成功 false:创建目录失败
     */
    public static boolean createDir(String dirPath) {
        boolean fileExists = false;
        // 判断文件路径是否为空
        if (CommonUtils.isNotBlank(dirPath)) {
            File file = new File(dirPath);
            // 判断文件不存在
            if (!(fileExists = file.exists())) {
                // 创建目录
                fileExists = file.mkdirs();
            }
        }
        return fileExists;
    }

    /**
     * <p>
     * 创建文件,判断文件是否存在，不存在则创建
     * </p>
     * 
     * @param dirPath
     *            文件完整路径
     * @return boolean true:创建成功 false:创建失败
     * @throws IOException
     */
    public static boolean createFile(String filepath) throws IOException {
        boolean fileExists = false;
        // 判断文件路径是否为空
        if (CommonUtils.isNotBlank(filepath)) {
            File file = new File(filepath);
            // 判断文件不存在
            if (!(fileExists = file.exists())) {
                // 创建文件
                fileExists = file.createNewFile();
            }
        }
        return fileExists;
    }

    /**
     * 从文件路径获取目录路径
     * 
     * @param filepath
     * @return
     */
    public static String getFileDir(String filepath) {
        String dir = null;
        if (CommonUtils.isNotBlank(filepath) && filepath.indexOf("/") != -1) {
            dir = filepath.substring(0, filepath.lastIndexOf("/") + 1);
        }
        return dir;
    }

    /**
     * <p>
     * 从文件全名中获取后缀名 ,并转换成小写
     * </p>
     * 
     * @param fileName
     *            获取文件后缀名
     * @return String 后缀名成
     */
    public static String getFilePrefix(String fileName) {
        String prefix = null;
        // 判断文件名称不为空
        if (CommonUtils.isNotBlank(fileName)) {
            // 判断文件名称是否包含字符.
            if (fileName.lastIndexOf(".") != -1) {
                // 获取文件后缀
                prefix = fileName.substring(fileName.lastIndexOf(".") + Constants.NUM_INT1, fileName.length());
            }
        }
        return CommonUtils.isBlank(prefix) ? "" : prefix.toLowerCase();
    }
}
