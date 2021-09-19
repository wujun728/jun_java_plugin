package com.jun.base.io.io;

import java.io.*;
import java.util.Arrays;

/*

 字节流
 1：字节输入流
 InputStream ;
        FileInputStream;
        BufferedInputStream;

1）FileInputStream
           文件字节输入流：一切文件在系统中都是以字节的形式保存的，无论你是文档文件、视频文件、音频文件...，需要读取这些文件都可以用FileInputStream去读取其保存在存储介质（磁盘等）上的字节序列。
           FileInputStream在创建时通过把文件名作为构造参数连接到该文件的字节内容，建立起字节流传输通道。
           然后通过 read()、read(byte[])、read(byte[],int begin,int len) 三种方法从字节流中读取 一个字节、一组字节。

2）BufferedInputStream
           带缓冲的字节输入流：上面我们知道文件字节输入流的读取时，是直接同字节流中读取的。由于字节流是与硬件（存储介质）进行的读取，所以速度较慢。而CPU需要使用数据时通过read()、read(byte[])读取数据时就要受到硬件IO的慢速度限制。我们又知道，CPU与内存发生的读写速度比硬件IO快10倍不止，所以优化读写的思路就有了：在内存中建立缓存区，先把存储介质中的字节读取到缓存区中。CPU需要数据时直接从缓冲区读就行了，缓冲区要足够大，在被读完后又触发fill()函数自动从存储介质的文件字节内容中读取字节存储到缓冲区数组。
           BufferedInputStream 内部有一个缓冲区，默认大小为8M，每次调用read方法的时候，它首先尝试从缓冲区里读取数据，若读取失败（缓冲区无可读数据），则选择从物理数据源 （譬如文件）读取新数据（这里会尝试尽可能读取多的字节）放入到缓冲区中，最后再将缓冲区中的内容返回给用户.由于从缓冲区里读取数据远比直接从存储介质读取速度快，所以BufferedInputStream的效率很高。



2, 字节输出流
OutputStream;
        FileOutputStream;
        BufferedOutputStream

 */
public class ReByte {

    public static void main(String[] args) {

        input();




    }


    /**
     * 字节流读取文本的时候，中文容易乱码。还不好处理。
     */
    public static void input(){
        //1、定义要使用的文件
        File file = new File("C:\\windows-version.txt");
        try {
            //文件存在的时候不会执行，不存在的时候会执行
            file.createNewFile();

            //2、定义字节输入流指定为文件输入流
            InputStream input  = new FileInputStream(file);
            //InputStream input  = new FileInputStream("C:\\windows-version.txt");
            byte[] b = new byte[(int) file.length()]; // file.length()获取文件的长度返回long类型
            //System.err.println(input.read());
            int len = input.read(b);
            input.close();

            //3、验证输入结果
            System.out.println("文件的内容长度为 : " + len);
            System.out.println("文件的内容为: " + new String(b));
            System.out.println("=== : " + file.length());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void out() {

        String  str = "阿弥陀佛";

        try {
            FileOutputStream fos = new FileOutputStream("D:\\1.txt");
            fos.write(str.getBytes("UTF-8"));

            fos.close();////注释掉就文件还有内容。因为是操作的是物理磁盘。

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
