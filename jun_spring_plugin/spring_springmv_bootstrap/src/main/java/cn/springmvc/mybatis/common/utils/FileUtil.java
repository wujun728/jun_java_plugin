package cn.springmvc.mybatis.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;

import cn.springmvc.mybatis.common.exception.BusinessException;

/**
 * 文件操作工具类
 * 
 * @author Vincent.wang
 */
public class FileUtil {

    /**
     * 获得指定文件的byte数组
     * 
     * @throws IOException
     */
    public static byte[] getBytes(String filePath) throws BusinessException {
        try {
            byte[] buffer = null;
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("FILE_UTIL_00004", e);
        }
    }

    public static void writeTextFile(String filePath, String text) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
            pw.println(text);
            pw.close();
        } catch (Exception e) {
            System.out.println("Error occured when writing file to system !");
            e.printStackTrace();
        }
    }

    public static void writeByteFile(File file, byte[] b) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b);
            fos.close();
        } catch (Exception e) {
            System.out.println("Error occured when writing file to system !");
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否存在
     * 
     * @param filePath
     * @param isCreate
     *            当文件夹不存在时，是否创建，true=创建,false=不创建
     * @return 若存在，则返回true；不存在，则返回false
     */
    public static boolean isFile(String filePath, boolean isCreate) {
        File file = new File(filePath);
        if (!file.isFile()) {
            if (isCreate)
                file.mkdir();
            return false;
        }
        return true;
    }

    /**
     * 判断文件夹是否存在
     * 
     * @param path
     *            文件夹目录
     * @param isCreate
     *            当文件夹不存在时，是否创建，true=创建,false=不创建
     * @return false=文件夹不存在，true=文件夹存在
     */
    public static boolean isDirectory(String path, boolean isCreate) {
        if (StringUtils.isBlank(path)) {
            return false;
        }
        File file = new File(path);
        if (!file.exists()) {
            if (isCreate)
                file.mkdir();
            return false;
        }
        return true;
    }

}
