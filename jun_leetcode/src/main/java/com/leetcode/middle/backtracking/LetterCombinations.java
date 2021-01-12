package com.leetcode.middle.backtracking;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 电话号码的字母组合
 *
 * @author BaoZhou
 * @date 2019/1/3
 */
public class LetterCombinations {

    @Test
    public void result() {
        List<String> strings = letterCombinations("");
        System.out.println(strings);
    }

    public List<String> letterCombinations(String digits) {
        List<String> resultList = new ArrayList<>();
        if (digits.isEmpty()) {
            return resultList;
        }
        dfs(new StringBuilder(), digits, resultList);
        return resultList;
    }


    public void dfs(StringBuilder result, String digits, List<String> resultList) {
        if (result.length() == digits.length()) {
            resultList.add(result.toString());
            return;
        }

        String s = num2String(digits.charAt(result.length()));

        for (int m = 0; m < s.length(); m++) {
            result.append(s.charAt(m));
            dfs(result, digits, resultList);
            result.deleteCharAt(result.length() - 1);
        }
    }

    public String num2String(char c) {
        switch (c) {
            case '2':
                return "abc";
            case '3':
                return "def";
            case '4':
                return "ghi";
            case '5':
                return "jkl";
            case '6':
                return "mno";
            case '7':
                return "pqrs";
            case '8':
                return "tuv";
            case '9':
                return "wxyz";
            default:
                return "";
        }
    }
}



