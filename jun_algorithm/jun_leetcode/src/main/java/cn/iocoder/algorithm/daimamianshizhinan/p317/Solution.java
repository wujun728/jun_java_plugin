package cn.iocoder.algorithm.daimamianshizhinan.p317;

/**
 * https://www.nowcoder.com/practice/1827258942284b2abfe65809785ac91a
 *
 * 不用额外变量交换两个整数的值
 */
public class Solution {

    public void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = b ^ a;

        System.out.println("a ：" + a);
        System.out.println("b ：" + b);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.swap(2, 3);
    }

}
