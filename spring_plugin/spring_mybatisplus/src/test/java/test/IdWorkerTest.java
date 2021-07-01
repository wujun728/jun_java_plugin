package test;

import com.baomidou.mybatisplus.toolkit.IdWorker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author: D.Yang
 * Email: koyangslash@gmail.com
 * Date: 16/10/9
 * Time: 下午12:22
 * Describe:
 */
public class IdWorkerTest {
    public static void main(String[] args) {
        int count = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executorService.execute(new IdWorkerTest().new Task());
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public class Task implements Runnable {
        public void run() {
            try {
                long id = IdWorker.getId();
                System.err.println(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
