package cn.iocoder.algorithm.leetcode.no0455;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/assign-cookies/
 *
 * 贪心算法
 *
 * 从胃口最小的小孩开始发饼干，给能满足它胃口的最小饼干。
 */
public class Solution {

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        // 贪心比较
        int gIndex = 0;
        int sIndex = 0;
        while (sIndex < s.length
            && gIndex < g.length) {
            // 这个小孩子胃口比较大，准备下一块饼干
            if (g[gIndex] > s[sIndex]) {
                sIndex++;
                continue;
            }
            // 把这个饼干给这个小孩，然后轮到下一个小孩。
            gIndex++;
            sIndex++;
        }
        return gIndex;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findContentChildren(new int[]{1, 2},
                new int[]{1, 2, 3}));
    }

}
