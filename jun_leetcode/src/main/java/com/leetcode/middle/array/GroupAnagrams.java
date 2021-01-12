package com.leetcode.middle.array;

import com.leetcode.leetcodeutils.PrintUtils;

import java.util.*;

/**
 * 字谜分组
 * 这个是我抄的方法，比我自己的方法要好
 * @author BaoZhou
 * @date 2018/12/22
 */

public class GroupAnagrams {
    public static void main(String[] args) {
        String[] s1 = {"eat", "tea", "tan", "ate", "nat", "bat"};
        String[] s = {"tea","","eat","","tea",""};
        PrintUtils.printStringArrays(groupAnagrams(s));
    }

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) {
            return new ArrayList();
        }
        Map<String, List> map= new HashMap<>();
        for(String str:strs){
            char[] c=str.toCharArray();
            Arrays.sort(c);
            String key = String.valueOf(c);
            if(!map.containsKey(key)){
                map.put(key,new ArrayList());
            }
            map.get(key).add(str);
        }
        return new ArrayList(map.values());
    }
}
