package com.rann.sort;

/**
 * 基数排序 稳定
 * 复杂度： O(d(n+r)) r为基数d为位数 空间复杂度O(n+r)
 */
public class RadixSort {
    // 基数排序
    public void radixSort(int[] a, int begin, int end, int digit) {
        // 基数
        final int radix = 10;
        // 桶中的数据统计
        int[] count = new int[radix];
        int[] bucket = new int[end - begin + 1];

        // 按照从低位到高位的顺序执行排序过程
        for (int i = 1; i <= digit; i++) {
            // 清空桶中的数据统计
            for (int j = 0; j < radix; j++) {
                count[j] = 0;
            }

            // 统计各个桶将要装入的数据个数
            for (int j = begin; j <= end; j++) {
                int index = getDigit(a[j], i);
                count[index]++;
            }

            // count[i]表示第i个桶的右边界索引
            for (int j = 1; j < radix; j++) {
                count[j] = count[j] + count[j - 1];
            }

            // 将数据依次装入桶中
            // 这里要从右向左扫描，保证排序稳定性 
            for (int j = end; j >= begin; j--) {
                int index = getDigit(a[j], i);
                bucket[count[index] - 1] = a[j];
                count[index]--;
            }

            // 取出，此时已是对应当前位数有序的表
            for (int j = 0; j < bucket.length; j++) {
                a[j] = bucket[j];
            }
        }
    }

    // 获取x的第d位的数字，其中最低位d=1
    private int getDigit(int x, int d) {
        String div = "1";
        while (d >= 2) {
            div += "0";
            d--;
        }
        return x / Integer.parseInt(div) % 10;
    }

//	// Test getDigit
//	public static void main(String[] args) {
//		System.out.println(new RadixSort().getDigit(123, 1));
//		System.out.println(new RadixSort().getDigit(123, 2));
//		System.out.println(new RadixSort().getDigit(123, 3));
//		System.out.println(new RadixSort().getDigit(1211, 1));
//		System.out.println(new RadixSort().getDigit(1211, 2));
//		System.out.println(new RadixSort().getDigit(1211, 3));
//		System.out.println(new RadixSort().getDigit(1211, 4));
//	}
}
