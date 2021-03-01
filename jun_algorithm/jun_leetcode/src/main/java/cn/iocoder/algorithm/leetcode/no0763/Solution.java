package cn.iocoder.algorithm.leetcode.no0763;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/partition-labels/
 *
 * 贪心算法
 *
 * 计算每个字符的开始位置，和结束位置。然后，排序后，计算分块。
 */
public class Solution {

    public List<Integer> partitionLabels(String S) {
        int[][] counts = new int[26][2]; // [字符][开始位置, 结束位置]
        boolean[] exists = new boolean[26]; // 字符是否存在
        for (int i = 0; i < S.length(); i++) {
            int charIndex = S.charAt(i) - 'a';
            if (!exists[charIndex]) { // 初始化起始位置
                counts[charIndex][0] = i;
                exists[charIndex] = true;
            }
            counts[charIndex][1] = i; // 初始化结束位置
        }

        // 排序，按照开始位置、结束位置，升序。
        Arrays.sort(counts, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                }
                return a[0] - b[0];
            }
        });

        // 计算
        List<Integer> result = new ArrayList<>();
        int[] last = counts[0];
        for (int i = 1; i < counts.length; i++) {
            int[] current = counts[i];
            if (current[0] <= last[1]) {
                last[1] = Math.max(current[1], last[1]);
                continue;
            }
            result.add(last[1] - last[0] + 1);
            last = current;
        }
        result.add(last[1] - last[0] + 1);
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.partitionLabels("ababcbacadefegdehijhklij"));
    }

}
