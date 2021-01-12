package com.java8.newdate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 旧版本日期API DateFormat 线程不安全
 * @author BaoZhou
 * @date 2018/8/1
 */
public class SimpleDataFormatTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Callable<Date> callable = () -> format.parse("20161218");
        ExecutorService executors = Executors.newCachedThreadPool();
        List<Future<Date>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            results.add(executors.submit(callable));
        }

        for (Future<Date> result:results) {
            System.out.println(result.get());
        }
        executors.shutdown();
    }


}
