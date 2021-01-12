package com.offer;


import org.junit.jupiter.api.Test;

/**
 * 替换空格
 * @author: BaoZhou
 * @date : 2020/5/28 9:59 上午
 */
public class Q2 {
    @Test
    public void result() {
        StringBuffer stringBuffer = new StringBuffer("sd as as as");
        System.out.println(replaceSpace(stringBuffer));
    }

    public String replaceSpace(StringBuffer str) {
        return  str.toString().replace(" ", "%20");
    }
}
