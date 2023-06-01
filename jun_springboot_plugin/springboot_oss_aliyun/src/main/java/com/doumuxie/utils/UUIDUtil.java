package com.doumuxie.utils;

import java.util.UUID;

/**
 * @author liangliang
 * @version 1.0
 * @date 2019/7/16 14:13
 * @description 生成UUID工具类
 **/
public class UUIDUtil {

    /**
     * 生成UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {

        System.err.println(getUUID());
        }
    }
}
