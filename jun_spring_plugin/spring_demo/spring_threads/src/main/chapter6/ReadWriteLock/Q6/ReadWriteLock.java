package ReadWriteLock.Q6;

public final class ReadWriteLock {
    private int readingReaders = 0; // (A)...实际正在读取的执行绪数量
    private int waitingWriters = 0; // (B)...正在等待写入的执行绪数量
    private int writingWriters = 0; // (C)...实际正在写入的执行绪数量
    private boolean preferWriter = true; // 写入优先的话，值为true

    public synchronized void readLock() throws InterruptedException {
        while (writingWriters > 0 || (preferWriter && waitingWriters > 0)) {
            wait();
        }
        readingReaders++;                       //  (A)实际正在读取的线程数量加1
    }

    public synchronized void readUnlock() {
        readingReaders--;                       //  (A)实际正在读取的线程数量减1
        preferWriter = true;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        waitingWriters++;                       // (B)正在等待写入的线程数量加1
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                wait();
            }
        } finally {
          waitingWriters--;                   // (B)正在等待写入的线程数量减1
        }
        writingWriters++;                       //  (C)实际正在写入的线程数量加1
    }

    public synchronized void writeUnlock() {
        writingWriters--;                       // (C)实际正在写入的线程数量减
        preferWriter = false;
        notifyAll();
    }
}
