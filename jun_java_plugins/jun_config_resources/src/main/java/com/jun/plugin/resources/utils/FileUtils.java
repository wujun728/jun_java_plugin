package com.jun.plugin.resources.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By Hong on 2018/8/13
 **/
public final class FileUtils {

    /**
     * 获取文件名
     *
     * @param path 路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        File file = new File(path);
        return file.getName();
    }

    /**
     * 获取文件夹下面全部文件
     *
     * @param file 文件或者文件名
     * @return 全部文件
     */
    public static List<String> listAllFiles(File file) {
        List<String> list = new ArrayList<>();
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    list.add(f.getPath());
                } else {
                    //跳过git和svn文件夹
                    if (f.getName().contains(".git") || f.getName().contains(".svn")) {
                        continue;
                    }
                    list.addAll(listAllFiles(f));
                }
            }
        } else if (file.exists() && file.isFile()) {
            list.add(file.getPath());
        }
        return list;
    }

    public static List<String> listFiles(File file) {
        List<String> list = new ArrayList<>();
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isFile()) {
                    list.add(f.getPath());
                }
            }
        } else if (file.exists() && file.isFile()) {
            list.add(file.getPath());
        }
        return list;
    }

    /**
     * 删除一个非空文件夹
     *
     * @param file 文件夹
     * @return true成功
     */
    public static boolean delNotEmptyDir(File file) {
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file$ : files) {
                if (file$.isDirectory()) {
                    delNotEmptyDir(file$);
                } else {
                    file$.delete();
                }
            }
            return file.delete();
        }
        return false;
    }

    public static boolean delDir(String file) {
        File file$ = new File(file);
        return delDir(file$);
    }

    public static boolean delDir(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean delFile(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean delFile(String file) {
        File file$ = new File(file);
        return delFile(file$);
    }
}
