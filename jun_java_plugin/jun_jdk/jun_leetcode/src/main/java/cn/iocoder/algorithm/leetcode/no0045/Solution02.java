package cn.iocoder.algorithm.leetcode.no0045;

/**
 * 贪心算法
 *
 * 不断到达边界，如果到达边界后，最大步数加一。
 *
 * 相当于说，走一步，看看这一步能走到的最大边界是哪里。
 *
 * 例如说，2，3，1，1，4，6
 *
 * 第一轮，
 * 初始在第 0 的位置，跳到最远是第 2 个位置，此时使用了一步，step = 1 。
 *
 * 第二轮，
 * 在第 1 的位置，跳到最远是第 5 个位置。
 * 在第 2 的位置，跳到最远是滴 3 个位置。
 *  - 相比第 5 个位置，更近，所以此时最远的还是第 5 个位置。
 *  - 此时，第 2 个位置，是第一轮的边界，所以 step = 2 ，最远是第 5 个位置。
 *
 * 第三轮，
 * 在第 3 的位置，跳到 4 个位置。相比最远的还是第 5 个位置，还是小。
 * 在第 4 的位置，跳到 8 个位置。已经到达终点。
 *  - 此时，第 5 个位置是第二轮的边界，所以 step = 3 ，最远是滴 8 个位置。
 *
 * 至此，就基本结束了。😈 胖友在对着代码，瞅一瞅。其实，有点 BFS 的感觉，一轮一轮的。
 */
public class Solution02 {

    public int jump(int[] nums) {
        int end = 0; // 边界
        int maxPosition = 0; // 最大位置
        int step = 0;
        for (int i = 0; i < nums.length - 1; i++) { // 最后一个位置，不用再往下
            // 找到最远可以跳到的位置
            maxPosition = Math.max(maxPosition, i + nums[i]);
            // 判断是否走到边界
            if (i == end) {
                end = maxPosition;
                step++;
            }
        }
        return step;
    }

}
