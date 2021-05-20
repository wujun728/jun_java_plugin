package cn.iocoder.algorithm.leetcode.no0413;

/**
 * https://leetcode-cn.com/problems/arithmetic-slices/
 *
 * 数学
 */
public class Solution {

    public int numberOfArithmeticSlices(int[] A) {
        int sum = 0;
        Integer lastVarianceNumber = null;
        int lastVarianceCount = 0;
        for (int i = 1; i < A.length; i++) {
            // 计算方差
            int varianceNumber = A[i] - A[i - 1];
            // 方法不相等，计算上一个连续的方法，有多少组合
            if (lastVarianceNumber == null || lastVarianceNumber != varianceNumber) {
                // 计算组合
                sum += this.calc(lastVarianceCount);
                // 重新计算新的方法
                lastVarianceNumber = varianceNumber;
                lastVarianceCount = 1;
            } else {
                // 增加方法数
                lastVarianceCount++;
                // 到达数组的结尾，也可以认为方法结束
                if (i == A.length - 1) {
                    sum += this.calc(lastVarianceCount);
                }
            }
        }
        return sum;
    }

    // 传入的是间隔数，例如说间隔是 4 个，说明有 5 个数字。
    // 那么，按照 5 个位一组，有 1 个组合；4 个一组，有 2 个组合；3 个一组，就有 1 组组合。
    // 如此，我们便可以 n * (n - 1) / 2 。
    private int calc(int count) {
        if (count <= 1) {
            return 0;
        }
        return count * (count - 1) / 2;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.numberOfArithmeticSlices(new int[]{1, 2, 3, 4, 5}));
        System.out.println(solution.numberOfArithmeticSlices(new int[]{1, 2, 3, 6, 7, 8, 9}));
    }

}
