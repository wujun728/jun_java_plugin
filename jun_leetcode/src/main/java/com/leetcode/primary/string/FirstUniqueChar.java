package com.leetcode.primary.string;

/**
 * 字符串中的第一个唯一字符
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class FirstUniqueChar {

    public static void main(String[] args) {
        String s = "cc";
        System.out.println(firstUniqueChar(s));
    }

    public static int firstUniqueChar(String s) {
        int res = -1;
        for(char i ='a';i<='z';i++){
            int index = s.indexOf(i);
            if(index!=-1 && index == s.lastIndexOf(i)){
                res = (res==-1 || res > index) ? index : res;
            }
        }
        return res;
    }

}
