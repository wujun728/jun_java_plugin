package com.zccoder.java.book1.ch5.block;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * 标题：清单5.8 桌面搜索应用程序中的消费者<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File take) {
        System.out.println("建立索引：" + take);
    }
}
