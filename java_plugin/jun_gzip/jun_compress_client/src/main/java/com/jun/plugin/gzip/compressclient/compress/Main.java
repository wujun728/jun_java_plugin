package com.jun.plugin.gzip.compressclient.compress;

import java.io.IOException;

import com.jun.plugin.gzip.compressclient.encrypt.AESUtils;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 15:01
 */
public class Main {

    public static void main(String [] args){
        String test="test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890";
        System.out.println("压缩前长度" + test.length());
        try{
            String afterCompressStr = CompressUtils.compress(test);
            System.out.println("压缩后长度" + afterCompressStr.length());

            // 加密
            String afterEncryptStr = AESUtils.encrypt(afterCompressStr);
            System.out.println("先压缩后加密的长度" + afterEncryptStr.length());

            // 加密
            String _afterEncryptStr = AESUtils.encrypt(test);
            System.out.println(_afterEncryptStr);
            String _afterCompressStr = CompressUtils.compress(_afterEncryptStr);
            System.out.println("先加密后压缩的长度" + _afterCompressStr.length());

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
