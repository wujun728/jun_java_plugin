package com.rann.sort;

import org.junit.Test;

public class HalfSearchTest {
    @Test
    public void testHalfSearch() {
        int[] a = {1, 3, 4, 4, 4, 8, 9, 10, 11};
        int index = new HalfSearch().halfSearch(a, 0, a.length - 1, 4);
        System.out.println("======折半查找测试======");
        System.out.println("该数的索引是" + index);
    }
}
