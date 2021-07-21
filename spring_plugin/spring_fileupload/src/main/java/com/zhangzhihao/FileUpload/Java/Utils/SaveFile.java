package com.zhangzhihao.FileUpload.Java.Utils;

import com.zhangzhihao.FileUpload.Java.Controller.FileUploadController;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class SaveFile {
    private static final File uploadDirectory = new File(getRealPath());
    /**
     * @param savePath
     * @param fileFullName
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean saveFile(@NotNull final String savePath,
                                   @NotNull final String fileFullName,
                                   @NotNull final MultipartFile file)
            throws Exception {
        byte[] data = readInputStream(file.getInputStream());
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File uploadFile = new File(savePath + fileFullName);
        //判断文件夹是否存在，不存在就创建一个
        File fileDirectory = new File(savePath);
        synchronized (uploadDirectory){
            if(!uploadDirectory.exists()){
                if(!uploadDirectory.mkdir()){
                    throw new Exception("保存文件的父文件夹创建失败！路径为：" + savePath);
                }
            }
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new Exception("文件夹创建失败！路径为：" + savePath);
                }
            }
        }

        //创建输出流
        try (FileOutputStream outStream = new FileOutputStream(uploadFile)) {//写入数据
            outStream.write(data);
            outStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return uploadFile.exists();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    /**
     * 获得绝对路径（不带文件名）
     *
     * @return
     */
    public static String getRealPath() {
        String realPath;
        String path = FileUploadController.class.getResource("/").getFile();
        int index = path.indexOf("build");
        realPath = path.substring(0, index) + "/src/main/webapp/upload/";
        realPath = realPath.replaceFirst("/", "");
        return realPath;
    }


    /**
     * 根据文件路径获取File
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static java.io.File getFileByPath(String filePath) throws IOException {
        Path path = Paths.get(getRealPath() + filePath);
        if (Files.exists(path)) {
            return new java.io.File(path.toUri());
        }
        return null;
    }

    /**
     * 压缩文件
     *
     * @param srcFileList
     * @param zipFile
     * @throws IOException
     */
    public static void zipFiles(List<File> srcFileList, java.io.File zipFile) throws IOException {
        byte[] buf = new byte[1024];
        //ZipOutputStream类：完成文件或文件夹的压缩
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        for (java.io.File aSrcFileList : srcFileList) {
            FileInputStream in = new FileInputStream(aSrcFileList);
            out.putNextEntry(new ZipEntry(aSrcFileList.getName()));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        out.close();
    }
}
