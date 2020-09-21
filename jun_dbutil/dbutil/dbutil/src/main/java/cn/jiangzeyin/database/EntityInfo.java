package cn.jiangzeyin.database;

import cn.jiangzeyin.util.Assert;

/**
 * @author jiangzeyin
 * Created by jiangzeyin on 2017/2/3.
 */
public class EntityInfo {

    private static ConvertEntity convertEntity;

    private EntityInfo() {

    }

    public static void setConvertEntity(ConvertEntity convertDatabaseName) {
        EntityInfo.convertEntity = convertDatabaseName;
    }

    /**
     * @param cls 类
     * @return 库名
     */
    public static String getDatabaseName(Class cls) {
        Assert.notNull(cls);
        Assert.notNull(convertEntity, "please set convertDatabaseName");
        return convertEntity.getDatabaseName(cls);
    }

    /**
     * @param object 实体
     * @return 库名
     */
    public static String getDatabaseName(Object object) {
        Assert.notNull(object);
        return getDatabaseName(object.getClass());
    }

    /**
     * 获取表名
     *
     * @param cls            class
     * @param isIndex        是否索引
     * @param index          索引列
     * @param isDatabaseName 是否获取数据名
     * @return 表名
     */
    public static String getTableName(Class<?> cls, boolean isIndex, String index, boolean isDatabaseName) {
        Assert.notNull(cls);
        Assert.notNull(convertEntity, "please set convertDatabaseName");
        return convertEntity.getTableName(cls, isIndex, index, isDatabaseName);
    }

    /**
     * convert database name
     */
    public interface ConvertEntity {
        String getDatabaseName(Class cls);

        String getTableName(Class<?> class1, boolean isIndex, String index, boolean isDatabaseName);
    }
}
