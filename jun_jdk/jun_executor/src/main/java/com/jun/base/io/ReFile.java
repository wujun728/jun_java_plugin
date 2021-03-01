package com.jun.base.io;

import java.io.File;
/*

https://www.cnblogs.com/lm970585581/p/6884094.html

 */
public class ReFile {

    public static void main(String[] args) {

        File file = new File("ReadMe.txt");

        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.lastModified());
        System.out.println(file.toPath());
        System.out.println(file.length());
    }


}
