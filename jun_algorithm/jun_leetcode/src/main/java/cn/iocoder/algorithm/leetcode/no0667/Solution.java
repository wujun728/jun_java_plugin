package cn.iocoder.algorithm.leetcode.no0667;

/**
 * https://leetcode-cn.com/problems/beautiful-arrangement-ii/
 *
 * 解决方案：找规律
 *
 * 参考博客：https://leetcode-cn.com/problems/beautiful-arrangement-ii/solution/leetcode-you-mei-de-pai-lie-iizhao-gui-lu-by-he-st/
 */
public class Solution {

    public int[] constructArray(int n, int k) {
        int[] results = new int[n];

        // 偶数，前 k 个，按照 [1, 2, 3 ..] 递增
        for (int i = 0, temp = 1; i <= k; i += 2) {
            results[i] = temp;
            temp++;
        }

        // 奇数，前 k 个，按照 [k + 1, k, ...] 递减
        // 这样，从位置 0 到 k 时，一共有 k 个间隔，想减的结果刚好是 [k, k-1, k-2, ..., 1] 递减
        for (int i = 1, temp = k + 1; i <= k; i += 2) {
            results[i] = temp;
            temp--;
        }

        // 从 k + 1 位置开始，顺序补位
        // 首个元素是，i + 1 = k + 1 + 1 = k + 2 ，避免了和第一个奇数的 k + 1 冲突.
        for (int i = k + 1; i < n; i++) {
            results[i] = i + 1;
        }

        return results;
    }

}
