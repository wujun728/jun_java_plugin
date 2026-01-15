package io.github.wujun728.online.ddl;

import cn.hutool.core.util.StrUtil;
import io.github.wujun728.online.enums.FieldTypeEnum;
import io.github.wujun728.online.vo.OnlineTableColumnVO;
import io.github.wujun728.online.vo.OnlineTableVO;

import java.util.ArrayList;
import java.util.List;

/**
 * MySQL数据库表操作实现类
 */
public class MySQLTable extends AbstractTable {

    /**
     * 获取删除列的SQL语句
     */
    @Override
    public String getDropColumnSQL(String tableName, String columnName) {
        return String.format("ALTER TABLE `%s` DROP COLUMN `%s`", tableName, columnName);
    }

    /**
     * 获取更新列的SQL语句
     */
    @Override
    public List<String> getUpdateColumnSQL(String tableName, OnlineTableColumnVO newColumn, OnlineTableColumnVO oldColumn) {
        List<String> sqlList = new ArrayList<>();
        // 检查列名、类型、注释或默认值是否发生变化
        boolean needUpdate = !StrUtil.equals(newColumn.getName(), oldColumn.getName()) ||
                !StrUtil.equals(newColumn.getColumnType(), oldColumn.getColumnType()) ||
                !StrUtil.equals(newColumn.getComments(), oldColumn.getComments()) ||
                !StrUtil.equals(newColumn.getDefaultValue(), oldColumn.getDefaultValue());

        if (needUpdate) {
            String sql = String.format(
                    "ALTER TABLE `%s` CHANGE COLUMN `%s` `%s` %s COMMENT '%s'",
                    tableName,
                    oldColumn.getName(),
                    newColumn.getName(),
                    getColumnType(newColumn),
                    newColumn.getComments()
            );
            sqlList.add(sql);
        }
        return sqlList;
    }

    /**
     * 获取添加列的SQL语句
     */
    @Override
    public List<String> getInsertColumnSQL(String tableName, OnlineTableColumnVO column) {
        List<String> sqlList = new ArrayList<>();
        String nullConstraint = column.isColumnNull() ? "NULL" : "NOT NULL";
        String defaultValue = "";
        
        if (StrUtil.isNotBlank(column.getDefaultValue())) {
            defaultValue = String.format(" DEFAULT '%s'", column.getDefaultValue());
        }
        
        String sql = String.format(
                "ALTER TABLE `%s` ADD COLUMN `%s` %s %s %s COMMENT '%s'",
                tableName,
                column.getName(),
                getColumnType(column),
                nullConstraint,
                defaultValue,
                column.getComments()
        );
        sqlList.add(sql);
        return sqlList;
    }

    /**
     * 获取创建表的SQL语句
     */
    @Override
    public String getTableSQL(OnlineTableVO tableVO) {
        StringBuilder columnsBuilder = new StringBuilder();
        
        // 遍历所有列，构建列定义SQL
        for (OnlineTableColumnVO column : tableVO.getColumnList()) {
            String nullConstraint = column.isColumnNull() ? "NULL" : "NOT NULL";
            String defaultValue = "";
            
            if (StrUtil.isNotBlank(column.getDefaultValue())) {
                defaultValue = String.format(" DEFAULT '%s'", column.getDefaultValue());
            }
            
            columnsBuilder.append(String.format(
                    "  `%s` %s %s %s COMMENT '%s',\n",
                    column.getName(),
                    getColumnType(column),
                    nullConstraint,
                    defaultValue,
                    column.getComments()
            ));
        }
        
        // 添加主键
        columnsBuilder.append("  PRIMARY KEY (`id`)");
        
        // 构建完整的CREATE TABLE语句
        return String.format(
                "CREATE TABLE `%s` (\n%s\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='%s'",
                tableVO.getName(),
                columnsBuilder.toString(),
                tableVO.getComments()
        );
    }

    /**
     * 获取重命名表的SQL语句
     */
    @Override
    public String getRenameTableSQL(String tableName) {
        String newTableName = tableName + "_" + System.currentTimeMillis();
        return String.format("ALTER TABLE `%s` RENAME TO `%s`", tableName, newTableName);
    }

    /**
     * 获取更新表的SQL语句
     */
    @Override
    public String getUpdateTableSQL(String tableName, String comments) {
        return String.format("ALTER TABLE `%s` COMMENT='%s'", tableName, comments);
    }

    /**
     * 根据字段类型获取MySQL数据类型
     */
    private String getColumnType(OnlineTableColumnVO column) {
        switch (FieldTypeEnum.valueOf(column.getColumnType())) {
            case String:
                return String.format("VARCHAR(%d)", column.getLength());
            case Long:
                return "BIGINT";
            case Integer:
                return "INT";
            case Double:
                return String.format("DOUBLE(%d,%d)", column.getLength(), column.getPointLength());
            case Date:
                return "DATE";
            case DateTime:
                return "DATETIME";
            case BigDecimal:
                return String.format("DECIMAL(%d,%d)", column.getLength(), column.getPointLength());
            case Bit:
                return "BIT";
            case Text:
                return "TEXT";
            default:
                return "VARCHAR(255)";
        }
    }
}

