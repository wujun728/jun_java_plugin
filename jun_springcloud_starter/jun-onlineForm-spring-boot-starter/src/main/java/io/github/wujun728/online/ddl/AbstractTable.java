package io.github.wujun728.online.ddl;

import io.github.wujun728.online.vo.OnlineTableColumnVO;
import io.github.wujun728.online.vo.OnlineTableVO;

import java.util.List;

/**
 * 数据库表操作抽象类
 */
public abstract class AbstractTable {

    /**
     * 获取删除表的SQL语句
     */
    public String getDropTableSQL(String tableName) {
        return String.format("DROP TABLE IF EXISTS `%s`", tableName);
    }

    /**
     * 获取添加列的SQL语句
     */
    public abstract List<String> getInsertColumnSQL(String tableName, OnlineTableColumnVO column);

    /**
     * 获取更新列的SQL语句
     */
    public abstract List<String> getUpdateColumnSQL(String tableName, OnlineTableColumnVO newColumn, OnlineTableColumnVO oldColumn);

    /**
     * 获取重命名表的SQL语句
     */
    public abstract String getRenameTableSQL(String tableName);

    /**
     * 获取更新表的SQL语句
     */
    public abstract String getUpdateTableSQL(String tableName, String comments);

    /**
     * 获取创建表的SQL语句
     */
    public abstract String getTableSQL(OnlineTableVO tableVO);

    /**
     * 获取删除列的SQL语句
     */
    public abstract String getDropColumnSQL(String tableName, String columnName);
}

