package cn.jiangzeyin.database.config;

import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

/**
 * Created by jiangzeyin on 2017/1/6.
 */
public final class DatabaseContextHolder {
    private DatabaseContextHolder() {
    }

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static final Random random = new Random();

    private static Map<String, DataSource>[] MAPS;
    private static String[] tagNames;
    private static DatabaseOptType databaseOptType = DatabaseOptType.One;
    private static Map<String, DataSource> targetDataSourcesMap;

    public enum DatabaseOptType {
        One, Two, More
    }

    public static String getConnectionTagName() {
        return threadLocal.get();
    }


    static void init(Map<String, DataSource>[] maps, String[] tagName) {
        Assert.notNull(maps);
        if (maps.length == 0)
            throw new IllegalArgumentException("数据库连接信息不能为空");
        DatabaseContextHolder.MAPS = maps;
        DatabaseContextHolder.tagNames = tagName;
        if (maps.length == 1)
            DatabaseContextHolder.databaseOptType = DatabaseOptType.One;
        else if (maps.length == 2)
            DatabaseContextHolder.databaseOptType = DatabaseOptType.Two;
        else
            DatabaseContextHolder.databaseOptType = DatabaseOptType.More;
        DatabaseContextHolder.targetDataSourcesMap = MAPS[0];
        SystemDbLog.getInstance().info(" 数据库操作：" + databaseOptType.toString());
    }

    static void init(Map<String, DataSource> map) {
        Assert.notNull(map);
        if (map.size() < 1)
            throw new RuntimeException("数据库连接加载为空");
        DatabaseContextHolder.targetDataSourcesMap = map;
        SystemDbLog.getInstance().info(" 数据库操作：" + databaseOptType.toString());
    }

//    public static void setThreadLocal(String tag) {
//        threadLocal.set(tag);
//    }


//    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
//        //    super.setTargetDataSources(targetDataSources);
//        DatabaseContextHolder.targetDataSourcesMap = targetDataSources;
//    }

//    @Override
//    protected Object determineCurrentLookupKey() {
//        return threadLocal.get();
//    }
//
//    @Override
//    protected DataSource determineTargetDataSource() {
//        return super.determineTargetDataSource();
//    }

    private static Map<String, DataSource> randMap() {
        int index = random.nextInt(MAPS.length);
        threadLocal.set(tagNames[index]);
        return MAPS[index];
    }

    public static DataSource getReadDataSource(String tag) {
        DataSource dataSource = null;
        if (databaseOptType == DatabaseOptType.One)
            dataSource = targetDataSourcesMap.get(tag);
        else if (databaseOptType == DatabaseOptType.Two) {
            dataSource = MAPS[1].get(tag);
            threadLocal.set(tagNames[1]);
        } else if (databaseOptType == DatabaseOptType.More)
            dataSource = randMap().get(tag);
        Assert.notNull(dataSource, "没有找到对应数据源：" + tag);
        return dataSource;
    }

//    public static DataSource getReadDataSource_V(Base base) {
//        String tag = base.getTag();
//        DataSource dataSource = null;
//        if (databaseOptType == DatabaseOptType.One) {
//            dataSource = (DataSource) targetDataSourcesMap.get(tag);
//            threadLocal.set(tagNames[0]);
//        } else if (databaseOptType == DatabaseOptType.Two) {
//            dataSource = (DataSource) MAPS[1].get(tag);
//            threadLocal.set(tagNames[1]);
//        } else if (databaseOptType == DatabaseOptType.More)
//            dataSource = (DataSource) randMap().get(tag);
//        Assert.notNull(dataSource, "没有找到对应数据源：" + tag);
//        base.setTagName(getConnectionTagName());
//        return dataSource;
//    }


    public static DataSource getWriteDataSource(String tag) {
        DataSource dataSource = null;
        if (databaseOptType == DatabaseOptType.One)
            dataSource = targetDataSourcesMap.get(tag);
        else if (databaseOptType == DatabaseOptType.Two) {
            dataSource = targetDataSourcesMap.get(tag);
            threadLocal.set(tagNames[0]);
        } else if (databaseOptType == DatabaseOptType.More)
            dataSource = randMap().get(tag);
        Assert.notNull(dataSource, "没有找到对应数据源：" + tag);
        return dataSource;
    }

//    public static Connection getReadConnection(String tag) throws SQLException {
//        return getReadDataSource(tag).getConnection();
//    }

    public static Connection getWriteConnection(String tag) throws SQLException {
        return getWriteDataSource(tag).getConnection();
    }
}
