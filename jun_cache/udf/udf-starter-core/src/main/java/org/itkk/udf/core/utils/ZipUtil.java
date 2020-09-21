/**
 * ZipFile.java
 * Created at 2016-03-01
 * Created by Administrator
 * Copyright (C) 2016 LLSFW, All rights reserved.
 */
package org.itkk.udf.core.utils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.*;

/**
 * <p>
 * ClassName: ZipFile
 * </p>
 * <p>
 * Description: 压缩&解压文件
 * </p>
 * <p>
 * Author: wangkang
 * </p>
 * <p>
 * Date: 2014年1月4日
 * </p>
 */
public class ZipUtil {

    /**
     * <p>
     * Field byteSize: 字节数组长度
     * </p>
     */
    private static final int BYTE_SIZE = 1024;

    /**
     * <p>
     * Field encoding: 编码格式
     * </p>
     */
    private String encoding;

    /**
     * <p>
     * Description: 构造方法
     * </p>
     *
     * @param encoding 编码
     */
    public ZipUtil(String encoding) {
        this.encoding = encoding;
    }

    /**
     * <p>
     * Description: 把zip文件解压到指定的文件夹
     * </p>
     *
     * @param zipFilePath zip文件路径
     * @param saveFileDir 解压后的文件存放路径
     * @throws IOException 异常
     */
    public void decompressZip(String zipFilePath, String saveFileDir) throws IOException {
        File file = new File(zipFilePath);
        if (file.exists()) {
            this.decompressZip(file, saveFileDir);
        } else {
            throw new IOException("文件不存在");
        }
    }

    /**
     * <p>
     * Description: 解压
     * </p>
     *
     * @param file        压缩包文件
     * @param saveFileDir 保存路径
     * @throws IOException 异常
     */
    private void decompressZip(File file, String saveFileDir) throws IOException {
        InputStream is = null;
        ZipArchiveInputStream zais = null;
        try { //NOSONAR
            is = new FileInputStream(file);
            zais = new ZipArchiveInputStream(is, this.encoding);
            ArchiveEntry archiveEntry;

            // 把zip包中的每个文件读取出来
            // 然后把文件写到指定的文件夹
            while ((archiveEntry = zais.getNextEntry()) != null) {

                // 如果是目录,则自动创建
                if (archiveEntry.isDirectory()) {
                    File f = new File(saveFileDir + archiveEntry.getName());
                    boolean rs = f.mkdir();
                    if (rs) {
                        continue;
                    } else {
                        throw new IOException("目录创建失败");
                    }
                }

                // 构造解压出来的文件存放路径
                this.decompressZip(saveFileDir, archiveEntry, zais);

            }
        } finally {
            if (zais != null) {
                zais.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * <p>
     * Description: 解压
     * </p>
     *
     * @param saveFileDir  存储路径
     * @param archiveEntry 压缩相
     * @param zais         压缩流
     * @throws IOException 异常
     */
    private void decompressZip(String saveFileDir, ArchiveEntry archiveEntry, ZipArchiveInputStream zais)
            throws IOException {
        String entryFilePath = saveFileDir + archiveEntry.getName();
        byte[] content = new byte[BYTE_SIZE];
        OutputStream os = null;
        try {  //NOSONAR
            // 把解压出来的文件写到指定路径
            File entryFile = new File(entryFilePath);
            os = new FileOutputStream(entryFile);
            int r;
            while ((r = zais.read(content)) > 0) {
                os.write(content, 0, r);
            }
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * <p>
     * Description: 压缩文件
     * </p>
     *
     * @param files       需要压缩的文件
     * @param zipFilePath zip文件
     * @throws IOException 异常
     */
    public void compressFiles2Zip(File[] files, String zipFilePath) throws IOException {
        if (files != null && files.length > 0) {
            ZipArchiveOutputStream zaos = null;
            try {  //NOSONAR
                File zipFile = new File(zipFilePath);
                zaos = new ZipArchiveOutputStream(zipFile);

                // 将每个文件用ZipArchiveEntry封装
                // 再用ZipArchiveOutputStream写到压缩文件中
                for (File file : files) {
                    this.compressFiles2Zip(file, zaos);
                }
                zaos.finish();
            } finally {
                if (zaos != null) {
                    zaos.close();
                }
            }

        }

    }

    /**
     * <p>
     * Description: 压缩
     * </p>
     *
     * @param file 文件
     * @param zaos 压缩流
     * @throws IOException 异常
     */
    private void compressFiles2Zip(File file, ZipArchiveOutputStream zaos) throws IOException {
        final int ioBuffered = 1024;
        if (file != null) {
            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
            zaos.putArchiveEntry(zipArchiveEntry);
            InputStream is = null;
            try {  //NOSONAR
                is = new BufferedInputStream(new FileInputStream(file), ioBuffered);
                byte[] buffer = new byte[BYTE_SIZE];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    zaos.write(buffer, 0, len);
                }
                zaos.closeArchiveEntry();
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    }
}
