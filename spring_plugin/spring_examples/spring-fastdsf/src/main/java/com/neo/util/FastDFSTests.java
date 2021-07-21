package com.neo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FastDFSTests {
    private static final int poolSize = 8;

    Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 并发测试
     *
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    private static void latchsTest() throws InterruptedException {
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(poolSize);

        ExecutorService exce = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < poolSize; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        start.await();
                        testLoad();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            exce.submit(run);
        }
        start.countDown();
        end.await();
        exce.shutdown();
    }

    public static void testLoad() {
        String filePath="C:\\Users\\neo\\Pictures\\xz.jpg";
        File file=new File(filePath);
        String serverUrl="http://localhost:8080/uploadSign";
        for (int i=0;i<10000;i++){
            HttpClientUtils.uploadFile(file,serverUrl);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        latchsTest();
    }


}
