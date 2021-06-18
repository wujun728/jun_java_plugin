package cn.iocoder.algorithm.leetcode.no0303;

/**
 * https://leetcode-cn.com/problems/range-sum-query-immutable/
 *
 * {@link #sums} 缓存
 *
 * 也算动态规划把。
 *
 * sums[i, j] = sums[0, j] - sums[0, i - 1] 。
 *
 * 进一步优化的话，就是把构造方法里的提前计算缓存，变成后置计算。
 */
public class NumArray02 {

    private int[] sums; // 记录使用从 0 到 i 位置，求和。注意，第 0 位为空。

    public NumArray02(int[] nums) {
        // 计算 sums
        this.sums = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }

    public static void main(String[] args) {
        NumArray02 array = new NumArray02(new int[]{-2,0,3,-5,2,-1});
        System.out.println(array.sumRange(0, 2)); // 1
        System.out.println(array.sumRange(2, 5)); // -1
        System.out.println(array.sumRange(0, 5)); // -3
    }

}
