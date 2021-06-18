package cn.springmvc.mybatis.common.utils.db;

/**
 * @author Wujun
 *
 */
public class ContextHolder {

    public static final String DATA_SOURCE_BUS = "bus";
    public static final String DATA_SOURCE_M = "m";
    public static final String DATA_SOURCE_S = "s";

    public enum Key {
        bus, m, s;
    }

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDataSource(Key key) {
        switch (key) {
        case m:
            contextHolder.set("m");
            break;
        case s:
            contextHolder.set("s");
            break;
        default:
            contextHolder.set("bus");
            break;
        }
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
