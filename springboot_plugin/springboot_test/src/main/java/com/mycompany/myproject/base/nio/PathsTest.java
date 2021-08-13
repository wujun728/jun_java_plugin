package com.mycompany.myproject.base.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

/*
        Paths 提供的 get() 方法用来获取 Path 对象：
            Path get(String first, String … more) : 用于将多个字符串串连成路径。
        Path 常用方法：
            boolean endsWith(String path) : 判断是否以 path 路径结束
            boolean startsWith(String path) : 判断是否以 path 路径开始
            boolean isAbsolute() : 判断是否是绝对路径
            Path getFileName() : 返回与调用 Path 对象关联的文件名
            Path getName(int idx) : 返回的指定索引位置 idx 的路径名称
            int getNameCount() : 返回Path 根目录后面元素的数量
            Path getParent() ：返回Path对象包含整个路径，不包含 Path 对象指定的文件路径
            Path getRoot() ：返回调用 Path 对象的根路径
            Path resolve(Path p) :将相对路径解析为绝对路径
            Path toAbsolutePath() : 作为绝对路径返回调用 Path 对象
            String toString() ： 返回调用 Path 对象的字符串表示形式
     */
public class PathsTest {

    public static void main(String[] args){

        Path path = Paths.get("C:\\Users\\barry.wang\\Downloads\\WGVuRGVza3RvcC5TTVJTQy1EZXNrdG9wICRQMTYz.ica");

        System.out.println(path.endsWith("hello.txt"));
        System.out.println(path.startsWith("e:/"));

        System.out.println(path.isAbsolute());

        System.out.println(path.getParent());
        System.out.println(path.getRoot());

        System.out.println(path.toAbsolutePath());

        System.out.println(path.getFileName());

        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println(path.getName(i));
        }


    }
}
