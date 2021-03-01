package cn.iocoder.algorithm.leetcode.no0096;

/**
 * 参考 https://leetcode-cn.com/problems/unique-binary-search-trees/solution/bu-tong-de-er-cha-sou-suo-shu-by-leetcode/
 *
 * 方法二，数学演绎法。这个就太 666 了，知道就好。
 */
public class Solution03 {

    public int numTrees(int n) {
        long c = 1;
        // 遍历，
        for (int i = 0; i < n; i++) {
            c = c * 2 * (2 * i + 1) / (i + 2);
        }

        // 返回结果
        return (int) c;
    }

}
