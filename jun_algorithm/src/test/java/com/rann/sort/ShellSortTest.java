package com.rann.sort;

import org.junit.Test;

public class ShellSortTest {
    @Test
    public void testShellSort() {
        int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
        new ShellSort().shellSort(a);
        System.out.println("======希尔排序测试======");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
