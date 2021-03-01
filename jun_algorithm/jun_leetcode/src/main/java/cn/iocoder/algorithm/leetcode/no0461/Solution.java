package cn.iocoder.algorithm.leetcode.no0461;

/**
 * https://leetcode-cn.com/problems/hamming-distance/
 *
 * 位操作
 */
public class Solution {

    public int hammingDistance(int x, int y) {
        int distance = 0;
        while (x != 0 || y != 0) {
            // 取最后一位
            if ((x & 1) != (y & 1)) {
                distance++;
            }
            // 去掉最后一位
            x = x >> 1;
            y = y >> 1;
        }
        return distance;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.hammingDistance(0, 4));
//        System.out.println(solution.hammingDistance(0, 3));
    }

}
