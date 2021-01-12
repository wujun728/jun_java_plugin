package com.leetcode.primary.string;

/**
 * 最长公共前缀
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        //String[] s = {"flower", "flow", "flight"};
        String[] s = {"aa", "a", "aaa"};
        System.out.println(longestCommonPrefix(s));
    }



    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        } else if (strs.length == 1) {
            return strs[0];
        }

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if(strs[i].length() == 0){
                return "";
            }
            for (int j = 0; j < Math.min(prefix.length(), strs[i].length()); j++) {
                if (prefix.charAt(j) != strs[i].charAt(j)) {
                    prefix = strs[i].substring(0, j);
                    break;
                }
                if(prefix.charAt(j) == strs[i].charAt(j) && j == Math.min(prefix.length(), strs[i].length()) - 1)
                {
                    prefix = strs[i].substring(0, j+1);
                    break;
                }
            }
        }
        return prefix;
    }
}
