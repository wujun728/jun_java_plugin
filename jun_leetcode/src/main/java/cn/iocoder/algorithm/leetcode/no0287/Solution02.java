package cn.iocoder.algorithm.leetcode.no0287;

/**
 * 弗洛伊德的乌龟和兔子（循环检测）
 *
 * 时间复杂度是 O(N)
 *
 * 参考博客：https://blog.csdn.net/monkeyduck/article/details/50439840
 */
public class Solution02 {

    public int findDuplicate(int[] nums) {
        int slow = nums[0]; // 慢指针（乌龟），走一步
        int fast = nums[nums[0]]; // 快指向（兔子），走两步
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        // 实际上，也可以重用上面的 slow 和 fast 两个变量，不过这样可能会有歧义，所以新定义了变量。
        int point1 = 0; // 回归原点
        int point2 = slow; // 指向慢指针的位置。
        while (point1 != point2) {
            point1 = nums[point1];
            point2 = nums[point2];
        }

        // 找不到符合的
        return point1;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.findDuplicate(new int[]{1 ,3, 4, 2, 2}));
        System.out.println(solution.findDuplicate(new int[]{3 ,1, 3, 4, 2}));
    }

}
