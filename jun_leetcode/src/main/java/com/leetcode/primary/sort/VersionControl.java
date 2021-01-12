package com.leetcode.primary.sort;

/**
 * @author BaoZhou
 * @date 2018/12/17
 */
public class VersionControl {
    static  boolean[] versionList = new boolean[]{false,false,false,false,false,true,true,true};
    public static boolean isBadVersion(int version) {
        return versionList[version-1];
    }
}
