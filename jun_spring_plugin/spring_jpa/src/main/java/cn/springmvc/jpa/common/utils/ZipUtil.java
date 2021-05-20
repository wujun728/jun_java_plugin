package cn.springmvc.jpa.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipFile;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author Vincent.wang
 *
 */
public class ZipUtil {

    private static final String charset = "GBK";

    public static boolean compress(List<File> files, String zipPath, boolean isDel) {
        if (StringUtils.isBlank(zipPath) || CollectionUtils.isEmpty(files)) {
            return false;
        }
        try {
            // ----压缩文件：
            FileOutputStream stream = new FileOutputStream(zipPath);
            CheckedOutputStream checkedStream = new CheckedOutputStream(stream, new CRC32());// 使用指定校验和创建输出流
            ZipOutputStream zipStream = new ZipOutputStream(checkedStream);
            zipStream.setEncoding(charset);// 支持中文
            BufferedOutputStream out = new BufferedOutputStream(zipStream);
            // zipStream.setComment("上传题附件"); // 设置压缩包注释
            zipStream.setMethod(ZipOutputStream.DEFLATED);// 启用压缩
            zipStream.setLevel(Deflater.BEST_COMPRESSION);// 压缩级别为最强压缩，但时间要花得多一点

            ZipEntry zipEntry;
            BufferedInputStream bi = null;
            for (File file : files) {
                bi = new BufferedInputStream(new FileInputStream(file));
                // 开始写入新的ZIP文件条目并将流定位到条目数据的开始处
                zipEntry = new ZipEntry(file.getName());
                zipStream.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int readCount = bi.read(buffer);

                while (readCount != -1) {
                    out.write(buffer, 0, readCount);
                    readCount = bi.read(buffer);
                }
                // 注，在使用缓冲流写压缩文件时，一个条件完后一定要刷新一把，不
                // 然可能有的内容就会存入到后面条目中去了
                out.flush();
                // 文件读完后关闭
                bi.close();
            }
            out.close();

            if (isDel) {
                for (File file : files) {
                    FileUtils.deleteQuietly(file);
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解压缩
     * 
     * @param zipfile
     *            File 需要解压缩的文件
     * @param descDir
     *            String 解压后的目标目录
     */
    @SuppressWarnings({ "rawtypes", "resource" })
    public static void unZipFiles(File zipfile, String descDir) {
        try {
            ZipFile zf = new ZipFile(zipfile);
            ZipEntry entry = null;
            String zipEntryName = null;
            InputStream in = null;
            OutputStream out = null;
            byte[] buf1 = null;
            int len;
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                entry = ((ZipEntry) entries.nextElement());
                zipEntryName = entry.getName();
                in = zf.getInputStream(entry);
                out = new FileOutputStream(descDir + zipEntryName);
                buf1 = new byte[1024];
                len = 0;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
