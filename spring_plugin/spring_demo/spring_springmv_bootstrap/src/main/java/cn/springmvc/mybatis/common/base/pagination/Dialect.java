package cn.springmvc.mybatis.common.base.pagination;

import cn.springmvc.mybatis.common.base.model.Page;

/**
 * 数据库方言基类
 */
public abstract class Dialect {

    public static enum DialectType {

                                    MYSQL(MySQLDialect.class), ORACLE(OracleDialect.class), SQLITE(SQLiteDialect.class);

        DialectType(Class<? extends Dialect> clazz) {
            this.clazz = clazz;
        }

        /** 方言实现类字节码 */
        private Class<? extends Dialect> clazz;

        public Class<? extends Dialect> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends Dialect> clazz) {
            this.clazz = clazz;
        }
    }

    /**
     * 构造分页SQL
     * 
     * @param sql
     *            原始SQL语句
     * @param page
     *            分页对象
     * @return String 构造后的分页SQL语句
     */
    public abstract String constructPageSql(String sql, Page<?> page);
}
