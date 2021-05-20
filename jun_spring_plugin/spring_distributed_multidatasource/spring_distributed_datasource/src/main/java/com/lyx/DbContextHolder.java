package com.lyx;

/**
 * @author Wujun
 */
public class DbContextHolder {
    // 利用ThreadLocal解决线程安全问题
    private static final ThreadLocal<DataSourceLookupKey> contextHolder = new ThreadLocal<DataSourceLookupKey>();

    // 设置数据源
    public static void setDbType(DataSourceLookupKey dbType) {
        contextHolder.set(dbType);
    }

    // 获取数据源
    public static DataSourceLookupKey getDbType() {
        return (DataSourceLookupKey) contextHolder.get();
    }

    // 清空
    public static void clearDbType() {
        contextHolder.remove();
    }
}
