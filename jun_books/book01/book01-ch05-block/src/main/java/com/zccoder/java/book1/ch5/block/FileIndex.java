package com.zccoder.java.book1.ch5.block;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 标题：清单5.9 开始桌面搜索<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class FileIndex {

    public static void main(String[] args) {
        startIndexing(new File[]{new File("D:\\Study\\AllSrc\\ByStudy\\zk-client\\bb")});
    }

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingDeque<>();
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return true;
            }
        };

        for (File root : roots) {
            new Thread(new FileCrawler(queue, filter, root)).start();
        }

        int consumerCount = 10;
        for (int i = 0; i < consumerCount; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }

}
