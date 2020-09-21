package org.itkk.udf.dal.mybatis.plugin.tablesplit;

/**
 * 描述 : DbContextHolder
 * <p/>
 * 多个登入用户可能需要同时切换数据源，所以这里需要写一个线程安全的ThreadLocal 用户切换数据源只要在线程中使用
 *
 * @author wangkang
 */
public class TableSplitContextHolder {

    /**
     * 描述 : 本地线程
     */
    private static final ThreadLocal<String> CONTEXTHOLDER = new TableSplitThreadLocal();

    /**
     * 描述 : 私有构造函数
     */
    private TableSplitContextHolder() {

    }

    /**
     * 描述 : 设置数据源代码
     *
     * @param code 数据源代码
     */
    public static void setSplitCode(String code) {
        CONTEXTHOLDER.set(code);
    }

    /**
     * 描述 : 获取数据源代码
     *
     * @return 数据源代码
     */
    public static String getSplitCode() {
        return CONTEXTHOLDER.get();
    }

    /**
     * <p>
     * Description: 清理数据源代码
     * </p>
     */
    public static void clearSplitCode() {
        CONTEXTHOLDER.remove();
    }

}
