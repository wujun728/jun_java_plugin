package cn.iocoder.algorithm.leetcode.no0476;

/**
 * https://leetcode-cn.com/problems/number-complement/
 *
 * 位操作
 *
 * 寻找最高位的 1 ，然后获得对应的从该位置到最右都是 1 ，这样在异或操作，就可以实现取反了。
 */
public class Solution02 {

    public int findComplement(int num) {
        if (num == 0) { // 排除 0 ，不然会死循环。
            return 0;
        }
        // 寻找最高位的 1 。
        int mask = 1 << 30; // 不能 << 31 ，不然就是负数了。
        while ((mask & num) == 0) {
            mask = mask >> 1;
        }

        // 求从当前开始，都变成 1 。
        mask = (mask << 1) - 1;

        // 异或操作，取反
        return num ^ mask;
    }

    public static void main(String[] args) {
//        Solution02 solution = new Solution02();
//        System.out.println(solution.findComplement(2));
//        System.out.println(solution.findComplement(5));
//        System.out.println(solution.findComplement(1));
//        System.out.println(solution.findComplement(0));
    }

}
