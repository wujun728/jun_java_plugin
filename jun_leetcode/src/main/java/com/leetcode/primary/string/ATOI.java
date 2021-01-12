package com.leetcode.primary.string;

/**
 * 字符串转换整数
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class ATOI {

    public static void main(String[] args) {
        String[] s = {"-91283472332", "words and 987", "   -42", "42","3.1234","0-1"};
        for (String s1 : s) {
            System.out.println(myAtoi(s1));
        }

    }


    public static int myAtoi(String str) {
        str.replace(" ","");
        char[] ss = str.trim().toCharArray();
        int resultInt = 0;
        String resultString = "";
        boolean flag = false;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ss.length; i++) {
            if ((ss[i] == '-' || ss[i] == '+') && !flag && i != ss.length - 1 && ss[i + 1] >= '0' && ss[i + 1] <= '9' ) {
                result.append(ss[i]);
                flag =true;
                continue;
            }
            if (ss[i] >= '0' && ss[i] <= '9') {
                flag =true;
                result.append(ss[i]);
                if(i == ss.length -1){
                    resultString = result.toString();
                }
            } else
            {
                if(flag) {
                        resultString = result.toString();
                        break;
                }
                else{
                    return 0;
                }
            }
        }
        if (resultString.isEmpty()) {
            return 0;
        } else {
            try {
                resultInt = Integer.parseInt(resultString);
                return resultInt;
            } catch (NumberFormatException e) {
                if (resultString.contains("-")) {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
        }
    }
}
