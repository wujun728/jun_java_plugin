package cn.iocoder.algorithm.leetcode.no0205;

/**
 * https://leetcode-cn.com/problems/isomorphic-strings/
 */
public class Solution {

//    错误理解
//    public boolean isIsomorphic(String s, String t) {
//        char[] mappings = new char[128];
//        for (int i = 0; i < s.length(); i++) {
//            char chS = s.charAt(i);
//            char chT = t.charAt(i);
//            char convertS = mappings[chS];
//            char convertT = mappings[chT];
//            if (convertS == 0 && convertT == 0) {
//                mappings[chS] = chT;
//                mappings[chT] = chS;
//            } else {
//                if (convertS == chT) {
//                    continue;
//                }
//                return false;
//            }
//        }
//        return true;
//    }

    // 题目：所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
    // 解析：有一点会让人很误解，“两个字符不能映射到同一个字符上”，指的是 s 或 t 的每一个字符串，一个字符不能映射到两个字符。所以，我们这里声明了 preIndexOfS、preIndexOfT 两个字符。
    // 并且，preIndexOfS 和 preIndexOfT 比较巧妙，指的是该字符，映射到指定位置上的字符。
    // 例如说，ab 和 ca 的例子
    // s 的 "a" ，和 t 的 "c" ，映射到位置 1 上的字符
    // s 的 "b" ，和 t 的 "a" ，映射到位置 2 上的字符。
    // 因此，两者是同构字符。
    // 假设此时，在每个字符后面加个 "a" ，就会不是同构字符
    public boolean isIsomorphic(String s, String t) {
        // 因为都是 ASCII 码，所以 128 空间足够
        int[] preIndexOfS = new int[128];
        int[] preIndexOfT = new int[128];

        // 遍历字符串
        for (int i = 0; i < s.length(); i++) {
            char sc = s.charAt(i), tc = t.charAt(i);
            // 对应的位置不同，则返回 false
            if (preIndexOfS[sc] != preIndexOfT[tc]) {
                return false;
            }
            // 对应的位置相同，则返回 true
            preIndexOfS[sc] = preIndexOfT[tc] = i + 1;
        }

        return true;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.isIsomorphic("aa", "ab"));
        System.out.println(solution.isIsomorphic("ab", "ca"));
//        System.out.println(solution.isIsomorphic("egg", "add"));
    }

}
