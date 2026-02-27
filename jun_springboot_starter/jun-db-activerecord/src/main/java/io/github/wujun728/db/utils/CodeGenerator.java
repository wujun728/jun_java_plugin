package io.github.wujun728.db.utils;

import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.jfinal.template.Engine;
import com.jfinal.template.Template;
import com.jfinal.template.source.ClassPathSourceFactory;

import javax.sql.DataSource;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代码生成器 - 基于Hutool MetaUtil表元数据 + JFinal Enjoy模板引擎
 * <p>
 * 支持5种代码生成模式：
 * 1. SQL模式 - 生成基于Db.execute/queryList的Service和Controller
 * 2. Record模式 - 生成基于Db.save/find/update/delete的Service和Controller
 * 3. Bean JPA模式 - 生成JPA注解Entity + Bean模式Service和Controller
 * 4. Bean MyBatis模式 - 生成MyBatis-Plus注解Entity + Bean模式Service和Controller
 * 5. Model模式 - 生成Model子类 + Model模式Service和Controller
 * <p>
 * 使用方式：
 * <pre>
 * Map&lt;String, String&gt; codes = Db.generatorCodeRecord("user_info", "com.example");
 * codes.forEach((fileName, content) -> System.out.println(fileName + "\n" + content));
 * </pre>
 */
public class CodeGenerator {

    private static volatile Engine engine;
    private static final Object ENGINE_LOCK = new Object();

    /**
     * 列元数据信息
     */
    public static class ColumnInfo {
        private String columnName;       // 数据库列名 user_name
        private String fieldName;        // Java字段名 userName
        private String fieldNameUpper;   // 首字母大写 UserName
        private String columnNameUpper;  // 全大写常量名 USER_NAME
        private String javaType;         // Java类型 String
        private String comment;          // 列注释
        private boolean pk;              // 是否主键
        private boolean nullable;        // 是否允许空
        private boolean autoIncrement;   // 是否自增
        private int size;                // 列大小

        public String getColumnName() { return columnName; }
        public String getFieldName() { return fieldName; }
        public String getFieldNameUpper() { return fieldNameUpper; }
        public String getColumnNameUpper() { return columnNameUpper; }
        public String getJavaType() { return javaType; }
        public String getComment() { return comment; }
        public boolean isPk() { return pk; }
        public boolean getPk() { return pk; }
        public boolean isNullable() { return nullable; }
        public boolean getNullable() { return nullable; }
        public boolean isAutoIncrement() { return autoIncrement; }
        public boolean getAutoIncrement() { return autoIncrement; }
        public int getSize() { return size; }
    }

    /**
     * 表元数据信息
     */
    public static class TableInfo {
        private String tableName;        // 表名 user_info
        private String className;        // 类名 UserInfo
        private String classNameLower;   // 首字母小写 userInfo
        private String packageName;      // 包名
        private String authorName;       // 作者
        private String dateStr;          // 日期
        private List<ColumnInfo> columns;// 所有列
        private ColumnInfo pkColumn;     // 主键列
        private String pkJavaType;       // 主键Java类型
        private String pkFieldName;      // 主键字段名
        private String pkColumnName;     // 主键列名
        private boolean hasDate;         // 是否有Date类型
        private boolean hasBigDecimal;   // 是否有BigDecimal类型

        public String getTableName() { return tableName; }
        public String getClassName() { return className; }
        public String getClassNameLower() { return classNameLower; }
        public String getPackageName() { return packageName; }
        public String getAuthorName() { return authorName; }
        public String getDateStr() { return dateStr; }
        public List<ColumnInfo> getColumns() { return columns; }
        public ColumnInfo getPkColumn() { return pkColumn; }
        public String getPkJavaType() { return pkJavaType; }
        public String getPkFieldName() { return pkFieldName; }
        public String getPkColumnName() { return pkColumnName; }
        public boolean isHasDate() { return hasDate; }
        public boolean getHasDate() { return hasDate; }
        public boolean isHasBigDecimal() { return hasBigDecimal; }
        public boolean getHasBigDecimal() { return hasBigDecimal; }
    }

    // ==================== 获取表元数据 ====================

    /**
     * 通过Hutool MetaUtil获取表的完整元数据信息
     *
     * @param dataSource  数据源
     * @param tableName   表名
     * @param packageName 生成代码的包名
     * @return 表信息（含所有列信息）
     */
    public static TableInfo getTableInfo(DataSource dataSource, String tableName, String packageName) {
        Table table = MetaUtil.getTableMeta(dataSource, tableName);

        TableInfo info = new TableInfo();
        info.tableName = tableName;
        info.className = tableNameToClassName(tableName);
        info.classNameLower = firstLower(info.className);
        info.packageName = packageName;
        info.authorName = "CodeGenerator";
        info.dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        List<ColumnInfo> columns = new ArrayList<>();
        boolean hasDate = false;
        boolean hasBigDecimal = false;

        for (Column column : table.getColumns()) {
            ColumnInfo col = new ColumnInfo();
            col.columnName = column.getName();
            col.fieldName = RecordUtil.toCamelCase(column.getName());
            col.fieldNameUpper = firstUpper(col.fieldName);
            col.columnNameUpper = column.getName().toUpperCase();
            col.javaType = jdbcTypeToJavaType(column.getType());
            col.comment = column.getComment();
            col.pk = column.isPk();
            col.nullable = column.isNullable();
            col.autoIncrement = column.isAutoIncrement();
            col.size = (int) column.getSize();

            if ("Date".equals(col.javaType)) hasDate = true;
            if ("BigDecimal".equals(col.javaType)) hasBigDecimal = true;

            columns.add(col);
        }

        info.columns = columns;
        info.hasDate = hasDate;
        info.hasBigDecimal = hasBigDecimal;

        // 设置主键信息
        ColumnInfo pkCol = null;
        for (ColumnInfo c : columns) {
            if (c.pk) {
                pkCol = c;
                break;
            }
        }
        if (pkCol == null && !columns.isEmpty()) {
            pkCol = columns.get(0); // 默认使用第一列
        }
        info.pkColumn = pkCol;
        info.pkJavaType = pkCol != null ? pkCol.javaType : "Object";
        info.pkFieldName = pkCol != null ? pkCol.fieldName : "id";
        info.pkColumnName = pkCol != null ? pkCol.columnName : "id";

        return info;
    }

    // ==================== 5种代码生成方法 ====================

    /**
     * SQL模式代码生成 - 生成基于Db.execute/queryList/insert的Service和Controller
     *
     * @param ds          数据源
     * @param tableName   表名
     * @param packageName 包名
     * @return Map(文件路径 → 代码内容)
     */
    public static Map<String, String> generatorCodeSQL(DataSource ds, String tableName, String packageName) {
        TableInfo info = getTableInfo(ds, tableName, packageName);
        Map<String, Object> data = tableInfoToMap(info);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("service/" + info.className + "Service.java", render("code-templates/sql_service.enjoy", data));
        result.put("controller/" + info.className + "Controller.java", render("code-templates/sql_controller.enjoy", data));
        return result;
    }

    /**
     * Record模式代码生成 - 生成基于Db.save/find/update/delete的Service和Controller
     */
    public static Map<String, String> generatorCodeRecord(DataSource ds, String tableName, String packageName) {
        TableInfo info = getTableInfo(ds, tableName, packageName);
        Map<String, Object> data = tableInfoToMap(info);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("service/" + info.className + "Service.java", render("code-templates/record_service.enjoy", data));
        result.put("controller/" + info.className + "Controller.java", render("code-templates/record_controller.enjoy", data));
        return result;
    }

    /**
     * Bean JPA模式代码生成 - 生成JPA注解Entity + Bean模式Service和Controller
     */
    public static Map<String, String> generatorCodeSQLBeanJPA(DataSource ds, String tableName, String packageName) {
        TableInfo info = getTableInfo(ds, tableName, packageName);
        Map<String, Object> data = tableInfoToMap(info);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("entity/" + info.className + ".java", render("code-templates/bean_entity_jpa.enjoy", data));
        result.put("service/" + info.className + "Service.java", render("code-templates/bean_service.enjoy", data));
        result.put("controller/" + info.className + "Controller.java", render("code-templates/bean_controller.enjoy", data));
        return result;
    }

    /**
     * Bean MyBatis-Plus模式代码生成 - 生成MyBatis-Plus注解Entity + Bean模式Service和Controller
     */
    public static Map<String, String> generatorCodeSQLBeanMybatis(DataSource ds, String tableName, String packageName) {
        TableInfo info = getTableInfo(ds, tableName, packageName);
        Map<String, Object> data = tableInfoToMap(info);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("entity/" + info.className + ".java", render("code-templates/bean_entity_mybatis.enjoy", data));
        result.put("service/" + info.className + "Service.java", render("code-templates/bean_service.enjoy", data));
        result.put("controller/" + info.className + "Controller.java", render("code-templates/bean_controller.enjoy", data));
        return result;
    }

    /**
     * Model模式代码生成 - 生成Model子类 + Model模式Service和Controller
     */
    public static Map<String, String> generatorCodeModel(DataSource ds, String tableName, String packageName) {
        TableInfo info = getTableInfo(ds, tableName, packageName);
        Map<String, Object> data = tableInfoToMap(info);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("model/" + info.className + ".java", render("code-templates/model_class.enjoy", data));
        result.put("service/" + info.className + "Service.java", render("code-templates/model_service.enjoy", data));
        result.put("controller/" + info.className + "Controller.java", render("code-templates/model_controller.enjoy", data));
        return result;
    }

    // ==================== 内部工具方法 ====================

    private static Engine getEngine() {
        if (engine == null) {
            synchronized (ENGINE_LOCK) {
                if (engine == null) {
                    Engine e = Engine.create("codeGen");
                    e.setSourceFactory(new ClassPathSourceFactory());
                    engine = e;
                }
            }
        }
        return engine;
    }

    private static String render(String templatePath, Map<String, Object> data) {
        Template template = getEngine().getTemplate(templatePath);
        return template.renderToString(data);
    }

    private static Map<String, Object> tableInfoToMap(TableInfo info) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", info.tableName);
        map.put("className", info.className);
        map.put("classNameLower", info.classNameLower);
        map.put("packageName", info.packageName);
        map.put("authorName", info.authorName);
        map.put("dateStr", info.dateStr);
        map.put("columns", info.columns);
        map.put("pkColumn", info.pkColumn);
        map.put("pkJavaType", info.pkJavaType);
        map.put("pkFieldName", info.pkFieldName);
        map.put("pkColumnName", info.pkColumnName);
        map.put("hasDate", info.hasDate);
        map.put("hasBigDecimal", info.hasBigDecimal);
        return map;
    }

    /**
     * 表名转类名 (user_info → UserInfo)
     */
    public static String tableNameToClassName(String tableName) {
        String camel = RecordUtil.toCamelCase(tableName);
        return firstUpper(camel);
    }

    public static String firstUpper(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String firstLower(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * JDBC类型到Java类型映射
     */
    public static String jdbcTypeToJavaType(int sqlType) {
        switch (sqlType) {
            case Types.BIT:
            case Types.BOOLEAN:
                return "Boolean";
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
                return "Integer";
            case Types.BIGINT:
                return "Long";
            case Types.FLOAT:
            case Types.REAL:
                return "Float";
            case Types.DOUBLE:
                return "Double";
            case Types.DECIMAL:
            case Types.NUMERIC:
                return "BigDecimal";
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
            case Types.CLOB:
            case Types.NCLOB:
                return "String";
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                return "Date";
            case Types.BLOB:
            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                return "byte[]";
            default:
                return "Object";
        }
    }
}
