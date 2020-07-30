package cn.iocoder.algorithm.leetcode.no0524;

import java.util.List;

/**
 * https://leetcode-cn.com/problems/longest-word-in-dictionary-through-deleting/
 */
public class Solution {

    public String findLongestWord(String s, List<String> d) {
        // 排序字符串。
        // 此处的排序，如果优化下，可以放在下面比较的时候，进行。这样，可以节省下排序产生的位置交换。
        // 当然，不好的地方，会多比较字符串。
        // 所以，综合考虑，还是提前排序好。
        d.sort((o1, o2) -> {
            int l1 = o1.length();
            int l2 = o2.length();
            if (l1 == l2) {
                return o1.compareTo(o2);
            }
            return l2 - l1;
        });

        // 逐个比较
        for (String dest : d) {
            boolean match = this.match(s, dest);
            if (match) {
                return dest;
            }
        }

        return "";
    }

    private boolean match(String source, String target) {
        int idx = 0; // 指向 source 的开头
        for (char ch : target.toCharArray()) {
            int foundIndex = source.indexOf(ch, idx);
            if (foundIndex == -1) {
                return false;
            }
            idx = foundIndex + 1;
        }

        return true;
    }

}
