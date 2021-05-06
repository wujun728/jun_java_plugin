package cn.iocoder.algorithm.leetcode.no0605;

/**
 * https://leetcode-cn.com/problems/can-place-flowers/
 *
 * 数组
 */
public class Solution {

    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0) {
            return true;
        }
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) {
                continue;
            }
            if (this.isFlower(flowerbed, i - 1) || this.isFlower(flowerbed, i + 1)) {
                continue;
            }
            n--;
            i++;
            if (n == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isFlower(int[] flowerbed, int i) {
        if (i == -1 || i == flowerbed.length) {
            return false;
        }
        return flowerbed[i] == 1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.canPlaceFlowers(new int[]{1, 0, 0, 0, 0, 0}, 2));
//        System.out.println(solution.canPlaceFlowers(new int[]{1, 0, 0, 0, 0}, 2));
//        System.out.println(solution.canPlaceFlowers(new int[]{0, 1, 0, 1, 0, 1, 0, 0}, 1));
//        System.out.println(solution.canPlaceFlowers(new int[]{1, 0, 0, 0, 0, 0, 1}, 2));
        System.out.println(solution.canPlaceFlowers(new int[]{1, 0, 0, 0, 0, 1}, 2));
    }

}
