package cn.jiangzeyin.system;

/**
 * Created by jiangzeyin on 2017/8/14.
 */
public class SystemDbLog implements DbLogInterface {
    private static final SystemDbLog SYSTEM_DB_LOG = new SystemDbLog();
    private static DbLogInterface dbLogInterface;

    public static void setDbLogInterface(DbLogInterface dbLogInterface) {
        SystemDbLog.dbLogInterface = dbLogInterface;
    }

    public static SystemDbLog getInstance() {
        return SYSTEM_DB_LOG;
    }

    @Override
    public void info(Object object) {
        if (dbLogInterface == null) {
            System.err.println("please set dbLogInterface");
            System.err.println(object);
            return;
        }
        dbLogInterface.info(object);
    }

    @Override
    public void error(String msg, Throwable t) {
        if (dbLogInterface == null) {
            System.err.println("please set dbLogInterface");
            System.err.println(msg);
            if (t != null)
                t.printStackTrace();
            return;
        }
        dbLogInterface.error(msg, t);
    }

    @Override
    public void warn(Object msg) {
        if (dbLogInterface == null) {
            System.err.println("please set dbLogInterface");
            System.err.println(msg);
            return;
        }
        dbLogInterface.warn(msg);
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (dbLogInterface == null) {
            System.err.println("please set dbLogInterface ");
            System.err.println(msg);
            if (t != null)
                t.printStackTrace();
            return;
        }
        dbLogInterface.warn(msg, t);
    }
}


