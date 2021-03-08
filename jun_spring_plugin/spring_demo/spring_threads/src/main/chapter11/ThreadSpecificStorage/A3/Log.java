package ThreadSpecificStorage.A3;

public class Log {
    private static final ThreadLocal tsLogCollection = new ThreadLocal();

    // 加入一个log
    public static void println(String s) {
        getTSLog().println(s);
    }

    // 关闭log
    public static void close() {
        getTSLog().close();
    }

    // 取得线程特有的log
    private static TSLog getTSLog() {
        TSLog tsLog = (TSLog)tsLogCollection.get();
    
        if (tsLog == null) {
            tsLog = new TSLog(Thread.currentThread().getName() + "-log.txt");
            tsLogCollection.set(tsLog);
            startWatcher(tsLog);
        }

        return tsLog;
    }

    private static void startWatcher(final TSLog tsLog) {
        
        final Thread target = Thread.currentThread();
       
        final Thread watcher = new Thread() {
            public void run() {
                System.out.println("startWatcher for " + target.getName() + " BEGIN");
                try {
                    target.join();
                } catch (InterruptedException e) {
                }
                tsLog.close();
                System.out.println("startWatcher for " + target.getName() + " END");
            }
        };
        watcher.start();
    }
}
