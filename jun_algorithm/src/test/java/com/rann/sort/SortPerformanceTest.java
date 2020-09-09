package com.rann.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 改进的希尔排序效果显著
 *
 * @author 哓哓
 */
public class SortPerformanceTest {
    private static final int MAX_SIZE = 10000;

    public static void main(String[] args) {
        // 启用下行代码随机生成指定MAX_SIZE规模的文本数据并存入数组
        new SortPerformanceTest().createBenchMark();
        System.out.println("正在读取" + MAX_SIZE + "组测试数据并执行不同排序方法，请等待...");
        System.out.println("======开始分析======");

        Long insertionSortTime = new SortPerformanceTest().insertionSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据插入排序耗时：" + insertionSortTime + "ms");

        Long shellSortTime = new SortPerformanceTest().shellSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据希尔排序(改进)耗时：" + shellSortTime + "ms");

        Long bubbleSortTime = new SortPerformanceTest().bubbleSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据冒泡排序耗时：" + bubbleSortTime + "ms");

        Long selectSortTime = new SortPerformanceTest().selectSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据选择排序耗时：" + selectSortTime + "ms");

        Long mergeSortTime = new SortPerformanceTest().mergeSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据归并排序耗时：" + mergeSortTime + "ms");

        Long qucikSortTime = new SortPerformanceTest().qucikSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据快速排序耗时：" + qucikSortTime + "ms");

        Long heapSortTime = new SortPerformanceTest().heapSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据堆排序耗时：" + heapSortTime + "ms");

        Long radixSortTime = new SortPerformanceTest().radixSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据基数排序耗时：" + radixSortTime + "ms");

        Long apiArraysSortTime = new SortPerformanceTest().apiArraysSortPerformance();
        System.out.println(MAX_SIZE + "组随机数据java.util.Arrays.sort(改进的快排)耗时：" + apiArraysSortTime + "ms");

        System.out.println("======结束分析======");
    }

    // 1.插入时间测试
    private Long insertionSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new InsertionSort().insertionSort(a);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 2.希尔(改进)时间测试
    private Long shellSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new ShellSort().shellSort(a);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 3.冒泡时间测试
    private Long bubbleSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new BubbleSort().bubbleSort(a);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 4.选择排序
    private Long selectSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new SelectSort().selectSort(a);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 5.归并时间测试
    private Long mergeSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new MergeSort().mergeSort(a, 0, a.length - 1);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 6.快排时间测试
    private Long qucikSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new QuickSort().quickSort(a, 0, a.length - 1);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 7.堆排时间测试
    private Long heapSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new HeapSort().heapSort(a);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 8.基数时间测试
    private Long radixSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        new RadixSort().radixSort(a, 0, a.length - 1, 4);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    // 9.Java.util.Arrays调优的快速排序时间测试
    private Long apiArraysSortPerformance() {
        int[] a = readBenchMark();
        Long start = System.currentTimeMillis();
        Arrays.sort(a);
        Long end = System.currentTimeMillis();

        return end - start;
    }

    private void createBenchMark() {
        // 随机产生MAX_SIZE个5000范围的随机数测试数据
        Random rand = new Random(System.currentTimeMillis());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < MAX_SIZE; i++) {
            list.add(String.valueOf(rand.nextInt(5000)));
        }
        // 文件IO
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("src/test/resources/benchmark.txt"), "UTF-8"));
            for (String val : list) {
                bw.write(val);
                bw.write(',');
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int[] readBenchMark() {
        BufferedReader br;
        int[] a = new int[MAX_SIZE];
        int count = 0;
        try {
            br = new BufferedReader(
                    new InputStreamReader(new FileInputStream("src/test/resources/benchmark.txt"), "UTF-8"));
            String line = null;
            while (null != (line = br.readLine())) {
                String[] strArr = line.split(",");
                for (String s : strArr) {
                    a[count++] = Integer.parseInt(s);
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }
}
