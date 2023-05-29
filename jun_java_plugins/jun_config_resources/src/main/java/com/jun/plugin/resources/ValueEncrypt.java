package com.jun.plugin.resources;

import java.util.Scanner;

import com.jun.plugin.resources.utils.AES;
import com.jun.plugin.resources.utils.StringUtils;

/**
 * Value加密工具
 * Created by Hong on 2019/4/19
 */
public class ValueEncrypt {

    private String key;

    public static void main(String[] args) {
        ValueEncrypt valueEncrypt = new ValueEncrypt();
        System.err.print("请输入密钥：");
        valueEncrypt.setKey(new Scanner(System.in).next());
        while (true) {
            System.err.print("请输入命令：");
            String text = new Scanner(System.in).nextLine();
            if (StringUtils.isEmpty(text)) {
                continue;
            } else if ("exit".equalsIgnoreCase(text)) {
                System.err.println("成功退出!");
                System.exit(0);
            } else if ("sk".equalsIgnoreCase(text)) {
                valueEncrypt.setKey(text);
                System.err.println("重置密钥成功！");
                continue;
            } else if ("dk".equalsIgnoreCase(text)) {
                System.err.println("当前使用密钥：" + valueEncrypt.getKey());
                continue;
            }

            int index;
            if ((index = text.indexOf(" ")) == -1) {
                System.err.println("命令错误！");
                continue;
            }
            String var1 = text.substring(0, index);
            String var2 = text.substring(index + 1);
            if ("e".equalsIgnoreCase(var1)) {
                //开始加密
                System.err.println("加密成功：" + valueEncrypt.encrypt(var2));
            } else if ("d".equalsIgnoreCase(var1)) {
                //开始解密
                System.err.println("解密成功：" + valueEncrypt.decrypt(var2));
            }
            System.err.println();
        }
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String encrypt(String var) {
        return AES.encrypt(var, key);
    }

    public String decrypt(String var) {
        return AES.decrypt(var, key);
    }
}
