package com.leetcode.middle.backtracking;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成括号
 *
 * @author BaoZhou
 * @date 2019/1/3
 */
public class GenerateParenthesis {

    @Test
    public void result() {
        List<String> strings = generateParenthesis(3);
        System.out.println(strings);
    }

    public List<String> generateParenthesis(int n) {
        List<String> resultList = new ArrayList<>();
        if (n == 0) {
            return resultList;
        }
        dfs(n, n,"", resultList);
        return resultList;
    }


    void dfs(int left, int right, String out, List<String> res) {
        if (left < 0 || right < 0 || left > right) return;
        if (left == 0 && right == 0) {
            res.add(out);
            return;
        }
        dfs(left - 1, right, out + "(", res);
        dfs(left, right - 1, out + ")", res);
    }

}



