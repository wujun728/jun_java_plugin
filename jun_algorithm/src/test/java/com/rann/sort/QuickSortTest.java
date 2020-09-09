package com.rann.sort;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class QuickSortTest {
    @Test
    public void testQuickSort() {
        int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
//        new QuickSort().quickSort(a, 0, a.length - 1);
        new QuickSort().quickSortNonRecur(a, 0, a.length - 1);
        String expect = "[2, 5, 12, 16, 28, 32, 60, 72]";
        assertEquals(expect, Arrays.toString(a));
    }
}
