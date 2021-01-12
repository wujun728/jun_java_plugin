package com.offer;


import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;


/**
 * 字符串的排列
 *
 * @author BaoZhou
 * @date 2020-6-9
 */

public class Q27 {
    @Test
    public void result() {
        System.out.println(Arrays.toString(Permutation("a").toArray()));
    }

    private Set<String> result = new TreeSet<>();

    public ArrayList<String> Permutation(String str) {
        perm(0, str.toCharArray(), result);
        return new ArrayList<>(result);
    }

    private void perm(int pos, char[] cStr, Set<String> result) {
        if (pos + 1 == cStr.length) {
            result.add(String.valueOf(cStr));
        } else {
            for (int i = pos; i < cStr.length; i++) {
                swapChar(pos, i, cStr);
                perm(pos + 1, cStr, result);
                swapChar(pos, i, cStr);
            }
        }
    }

    private void swapChar(int i, int j, char[] str) {
        char t = str[i];
        str[i] = str[j];
        str[j] = t;
    }


}
