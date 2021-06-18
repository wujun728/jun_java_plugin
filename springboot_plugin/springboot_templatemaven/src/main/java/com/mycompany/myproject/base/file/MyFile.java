package com.mycompany.myproject.base.file;

import java.io.File;

public class MyFile {
    
    public static void main(String[] args){

        File file = new File("C:\\Users\\barry.wang\\Downloads");
        if(file.isDirectory()){
            for(String s : file.list()){
                System.out.println(s);
            }
        }
    }
}
