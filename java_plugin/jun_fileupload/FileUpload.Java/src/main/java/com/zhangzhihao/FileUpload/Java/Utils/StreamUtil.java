package com.zhangzhihao.FileUpload.Java.Utils;


import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {
    /**
     * 从stream中保存文件
     *
     * @param inputStream inputStream
     * @param filePath    保存路径
     * @throws Exception 异常 抛异常代表失败了
     */
    public static void saveStreamToFile(@NotNull final InputStream inputStream,
                                        @NotNull final String filePath)
            throws Exception {
         /*创建输出流，写入数据，合并分块*/
        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }
}
