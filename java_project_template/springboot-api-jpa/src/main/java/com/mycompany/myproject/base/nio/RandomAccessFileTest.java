package com.mycompany.myproject.base.nio;

import java.io.RandomAccessFile;

public class RandomAccessFileTest {

    public static void main(String[] args){

        try {

            RandomAccessFile rf = new RandomAccessFile("C:\\barry\\project\\EMS\\env.txt", "rws");

            long fileLength = rf.length();
            long start = rf.getFilePointer();
            long readIndex = start + fileLength - 1;
            String line;
            rf.seek(readIndex - 10 + 1);
            line = rf.readLine();
            System.out.println("The last line:" + line);

            RandomAccessFile rf2 = new RandomAccessFile("C:\\barry\\project\\EMS\\env.txt", "rws");
            long fileLength2 = rf2.length();

        }catch (Exception e){

            System.out.println(e.getMessage());
        }


    }

}
