import java.util.Arrays;

import org.junit.Test;

public class TwoSum {

    /**
     * 已知一个升序数组array和一个数字s，数组不包含重复数字，在数组中查找两个数，使得它们的和正好为s；如果有多对数字的和等于s，则全部输出。
     */

    public void printResult(int a, int b) {
        System.out.println("[" + a + "," + b + "]");
    }

    /**
     * 平方复杂度的算法
     */
    public void twoSum01(int[] array, int s) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i] + array[j] == s) {
                    printResult(array[i], array[j]);
                    break;
                }
            }
        }
    }

    /**
     * 线性复杂度的算法
     */
    public void twoSum02(int[] array, int s) {
        int i = 0;
        int j = array.length - 1;
        int sum = 0;
        while (i < j) {
            sum = array[i] + array[j];
            if (sum == s) {
                printResult(array[i], array[j]);
                i++;
                j--;
            } else if (sum < s) {
                i++;
            } else {
                j--;
            }
        }
    }

    /**
     * NlogN的算法 借助于JDK的二分查找
     */
    public void twoSum03(int[] array, int s) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int another = s - array[i];
            if (Arrays.binarySearch(array, i + 1, n - 1, another) >= i + 1) {
                printResult(array[i], another);
            }
        }
    }

    @Test
    public void test() {
        int[] array = {1, 3, 4, 5, 8, 9, 11};
        int s = 13;
        twoSum01(array, s);
        System.out.println("----");
        twoSum02(array, s);
        System.out.println("----");
        twoSum03(array, s);
    }
}
