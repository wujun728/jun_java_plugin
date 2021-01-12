package com.leetcode.middle.math;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

/**
 * Excel表列序号
 *
 * @author: BaoZhou
 * @date : 2019/5/14 16:52
 */
class TitleToNumber {
    @Test
    public void test() {
        System.out.println(titleToNumber("AB"));
        System.out.println(titleToNumber(""));
    }

    public int titleToNumber(String s) {
        int result = 0;
        char[] chars = s.toCharArray();
        int j = 1;
        for (int i = chars.length - 1; i >= 0; i--) {
            result += (chars[i] - 'A' + 1) * j;
            j*=26;
        }
        return result;
    }
}