package io.github.wujun728.db.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.wujun728.db.record.Page;
import io.github.wujun728.db.record.Record;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * ORM工具类 - 合并了原RecordUtil、MapKit、FieldUtils的全部功能
 * <p>
 * 职责：
 * 1. 字段名驼峰/下划线互转 (原FieldUtils)
 * 2. 表名/列名解析，支持JPA(@Table/@Column)和MyBatis-Plus(@TableName/@TableField)注解 (原MapKit)
 * 3. Record/Map/Bean之间的互相转换 (原RecordUtil+MapKit)
 * 4. ResultSet到Record的构建 (原RecordUtil)
 * 5. SQL调试格式化 (原SqlUtil.getSql)
 */
public class RecordUtil {

    private static final char UNDERLINE = '_';

    // ==================== 反射元数据缓存 ====================

    /** 缓存: Class -> (Field, ColumnName) 列表，避免重复反射 */
    private static final ConcurrentHashMap<Class<?>, List<FieldMeta>> FIELD_META_CACHE = new ConcurrentHashMap<>();

    /** 字段元数据：持有 Field 引用和预解析的列名 */
    public static class FieldMeta {
        public final Field field;
        public final String columnName; // null 表示该字段应跳过（@Transient等）

        FieldMeta(Field field, String columnName) {
            this.field = field;
            this.columnName = columnName;
        }
    }

    /**
     * 获取类的字段元数据（含缓存），首次调用后缓存结果
     */
    public static List<FieldMeta> getFieldMetas(Class<?> clazz) {
        return FIELD_META_CACHE.computeIfAbsent(clazz, c -> {
            List<Field> fields = allFields(c);
            List<FieldMeta> metas = new ArrayList<>(fields.size());
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                String columnName = getColumnName(field);
                metas.add(new FieldMeta(field, columnName));
            }
            return Collections.unmodifiableList(metas);
        });
    }

    // ==================== 字段名转换 (原FieldUtils) ====================

    /**
     * 驼峰转下划线 (e.g. userName -> user_name)
     */
    public static String toUnderlineCase(String param) {
        if (param == null) {
            return null;
        }
        int length = param.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                Character preChar = i > 0 ? param.charAt(i - 1) : null;
                Character nextChar = i < length - 1 ? param.charAt(i + 1) : null;
                if (null != preChar) {
                    if (UNDERLINE == preChar) {
                        if (null == nextChar || Character.isLowerCase(nextChar)) {
                            c = Character.toLowerCase(c);
                        }
                    } else if (Character.isLowerCase(preChar)) {
                        sb.append(UNDERLINE);
                        if (null == nextChar || Character.isLowerCase(nextChar) || (nextChar >= '0' && nextChar <= '9')) {
                            c = Character.toLowerCase(c);
                        }
                    } else if (null != nextChar && Character.isLowerCase(nextChar)) {
                        sb.append(UNDERLINE);
                        c = Character.toLowerCase(c);
                    }
                } else if (null == nextChar || Character.isLowerCase(nextChar)) {
                    c = Character.toLowerCase(c);
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰 (e.g. user_name -> userName)
     */
    public static String toCamelCase(String name) {
        if (null == name) {
            return null;
        }
        if (name.indexOf(UNDERLINE) > -1) {
            int length = name.length();
            StringBuilder sb = new StringBuilder(length);
            boolean upperCase = false;
            for (int i = 0; i < length; i++) {
                char c = name.charAt(i);
                if (c == UNDERLINE) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
            return sb.toString();
        } else {
            return name;
        }
    }

    /**
     * 获取类的所有字段（遍历完整继承链，不含 Object）
     */
    public static List<Field> allFields(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields;
    }

    // ==================== 表名/列名解析 (原MapKit) ====================

    /**
     * 获取实体对应的表名，优先级：@Table(JPA) > @TableName(MyBatis-Plus) > 类名转下划线
     *
     * @param bean 实体对象或Class
     * @return 表名
     */
    public static String getTableName(Object bean) {
        String tableName;
        if (bean instanceof Class) {
            Class<?> clazz = (Class<?>) bean;
            tableName = toUnderlineCase(clazz.getSimpleName());
            if (AnnotationUtil.hasAnnotation(clazz, Table.class)) {
                tableName = AnnotationUtil.getAnnotationValue(clazz, Table.class, "name");
            } else if (AnnotationUtil.hasAnnotation(clazz, TableName.class)) {
                tableName = AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value");
            }
        } else {
            Class<?> clazz = bean.getClass();
            tableName = toUnderlineCase(clazz.getSimpleName());
            if (AnnotationUtil.hasAnnotation(clazz, Table.class)) {
                tableName = AnnotationUtil.getAnnotationValue(clazz, Table.class, "name");
            } else if (AnnotationUtil.hasAnnotation(clazz, TableName.class)) {
                tableName = AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value");
            }
        }
        return tableName;
    }

    /**
     * 获取字段对应的数据库列名，优先级：
     * - @Transient / transient修饰符 → 返回null（跳过）
     * - @TableField(MyBatis-Plus, exist=false) → 返回null（跳过）
     * - @TableField(MyBatis-Plus) → 使用value值
     * - @Column(JPA) → 使用name值
     * - 默认 → 字段名转下划线
     *
     * @param field 字段
     * @return 列名，null表示该字段应跳过
     */
    public static String getColumnName(Field field) {
        if (Modifier.isTransient(field.getModifiers())) {
            return null;
        }
        String columnName = toUnderlineCase(field.getName());
        if (AnnotationUtil.hasAnnotation(field, Transient.class)) {
            return null;
        }
        if (AnnotationUtil.hasAnnotation(field, TableField.class)) {
            columnName = AnnotationUtil.getAnnotationValue(field, TableField.class, "value");
            Boolean existFlag = AnnotationUtil.getAnnotationValue(field, TableField.class, "exist");
            if (existFlag != null && !existFlag) {
                return null;
            }
        } else if (AnnotationUtil.hasAnnotation(field, Column.class)) {
            String name = AnnotationUtil.getAnnotationValue(field, Column.class, "name");
            if (name != null && !name.isEmpty()) {
                columnName = name;
            }
        }
        return columnName;
    }

    // ==================== Map <-> Bean 转换 (原MapKit) ====================

    /**
     * Map列表转Bean列表
     */
    public static <T> List<T> mapToBeans(List<Map<String, Object>> lists, Class<T> clazz) {
        List<T> datas = new ArrayList<>();
        if (lists != null && !lists.isEmpty()) {
            lists.forEach(map -> datas.add(mapToBean(map, clazz)));
        }
        return datas;
    }

    /**
     * Map转Bean（通过FastJSON2序列化/反序列化实现）
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        try {
            return JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Map转Bean失败: " + e.getMessage(), e);
        }
    }

    /**
     * Bean转Map（字段名转驼峰作为key，使用缓存元数据）
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        try {
            for (FieldMeta meta : getFieldMetas(bean.getClass())) {
                if (meta.columnName == null) continue;
                Object val = meta.field.get(bean);
                if (val != null) {
                    map.put(toCamelCase(meta.columnName), val);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    /**
     * Bean列表转Map列表
     */
    public static <T> List<Map<String, Object>> beanToMaps(List<T> lists) {
        List<Map<String, Object>> datas = new ArrayList<>();
        if (lists != null && !lists.isEmpty()) {
            lists.forEach(obj -> datas.add(beanToMap(obj)));
        }
        return datas;
    }

    // ==================== Record <-> Map 转换 ====================

    /**
     * Record列表转Map列表
     */
    public static List<Map<String, Object>> recordToMaps(List<Record> recordList) {
        if (recordList == null || recordList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (Record record : recordList) {
            list.add(recordToMap(record));
        }
        return list;
    }

    /**
     * Record转Map
     */
    public static Map<String, Object> recordToMap(Record record) {
        if (record == null) {
            return new HashMap<>();
        }
        return new HashMap<>(record.getColumns());
    }

    /**
     * Record分页结果转Map分页结果
     */
    public static Page<Map<String, Object>> pageRecordToPageMap(Page<Record> pageList) {
        if (pageList == null) {
            return null;
        }
        Page<Map<String, Object>> page = new Page<>();
        page.setList(recordToMaps(pageList.getList()));
        page.setPageNumber(pageList.getPageNumber());
        page.setPageSize(pageList.getPageSize());
        page.setTotalPage(pageList.getTotalPage());
        page.setTotalRow(pageList.getTotalRow());
        return page;
    }

    /**
     * Map转Record（key保持原样）
     */
    public static Record mapToRecord(Map<String, Object> map) {
        Record record = new Record();
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                record.set(entry.getKey(), entry.getValue());
            }
        }
        return record;
    }

    /**
     * Map列表转Record列表
     */
    public static List<Record> mapToRecords(List<Map<String, Object>> maps) {
        List<Record> records = new ArrayList<>();
        if (maps != null && !maps.isEmpty()) {
            for (Map<String, Object> map : maps) {
                records.add(mapToRecord(map));
            }
        }
        return records;
    }

    // ==================== Record <-> Bean 转换 ====================

    /**
     * Record列表转Bean列表
     */
    public static <T> List<T> recordToBeans(List<Record> recordList, Class<T> clazz) {
        if (recordList == null || recordList.isEmpty()) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (Record record : recordList) {
            list.add(recordToBean(record, clazz));
        }
        return list;
    }

    /**
     * Record转Bean
     */
    public static <T> T recordToBean(Record record, Class<T> clazz) {
        if (record == null) {
            return null;
        }
        return mapToBean(record.getColumns(), clazz);
    }

    /**
     * Bean转Record（字段名根据注解解析为数据库列名，使用缓存元数据）
     */
    public static Record beanToRecord(Object bean) {
        Record record = new Record();
        try {
            for (FieldMeta meta : getFieldMetas(bean.getClass())) {
                if (meta.columnName == null) continue;
                Object val = meta.field.get(bean);
                if (val != null) {
                    record.put(meta.columnName, val);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return record;
    }

    /**
     * Bean列表转Record列表
     */
    public static <T> List<Record> beanToRecords(List<T> lists) {
        List<Record> datas = new ArrayList<>();
        if (lists != null && !lists.isEmpty()) {
            lists.forEach(obj -> datas.add(beanToRecord(obj)));
        }
        return datas;
    }

    // ==================== ResultSet处理 ====================

    /**
     * ResultSet构建Record列表
     */
    public static List<Record> build(ResultSet rs) throws SQLException {
        return build(rs, null);
    }

    /**
     * ResultSet构建Record列表（支持回调函数逐条处理）
     */
    public static List<Record> build(ResultSet rs, Function<Record, Boolean> func) throws SQLException {
        List<Record> result = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] labelNames = new String[columnCount + 1];
        int[] types = new int[columnCount + 1];
        buildLabelNamesAndTypes(rsmd, labelNames, types);
        while (rs.next()) {
            Record record = new Record();
            Map<String, Object> columns = record.getColumns();
            for (int i = 1; i <= columnCount; i++) {
                Object value;
                if (types[i] < Types.BLOB) {
                    value = rs.getObject(i);
                } else {
                    if (types[i] == Types.CLOB) {
                        value = handleClob(rs.getClob(i));
                    } else if (types[i] == Types.NCLOB) {
                        value = handleClob(rs.getNClob(i));
                    } else if (types[i] == Types.BLOB) {
                        value = handleBlob(rs.getBlob(i));
                    } else {
                        value = rs.getObject(i);
                    }
                }
                columns.put(labelNames[i], value);
            }
            if (func == null) {
                result.add(record);
            } else {
                if (!func.apply(record)) {
                    break;
                }
            }
        }
        return result;
    }

    public static void buildLabelNamesAndTypes(ResultSetMetaData rsmd, String[] labelNames, int[] types) throws SQLException {
        for (int i = 1; i < labelNames.length; i++) {
            labelNames[i] = rsmd.getColumnLabel(i);
            types[i] = rsmd.getColumnType(i);
        }
    }

    public static byte[] handleBlob(Blob blob) throws SQLException {
        if (blob == null) return null;
        InputStream is = null;
        try {
            is = blob.getBinaryStream();
            if (is == null) return null;
            int total = (int) blob.length();
            if (total == 0) return null;
            byte[] data = new byte[total];
            int offset = 0;
            while (offset < total) {
                int read = is.read(data, offset, total - offset);
                if (read == -1) break;
                offset += read;
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) try { is.close(); } catch (IOException e) { /* ignore close error */ }
        }
    }

    public static String handleClob(Clob clob) throws SQLException {
        if (clob == null) return null;
        Reader reader = null;
        try {
            reader = clob.getCharacterStream();
            if (reader == null) return null;
            int total = (int) clob.length();
            if (total == 0) return null;
            char[] buffer = new char[total];
            int offset = 0;
            while (offset < total) {
                int read = reader.read(buffer, offset, total - offset);
                if (read == -1) break;
                offset += read;
            }
            return new String(buffer, 0, offset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException e) { /* ignore close error */ }
        }
    }

    // ==================== SQL调试工具 (原SqlUtil.getSql) ====================

    /**
     * 将SQL语句中的?占位符替换为实际参数值，用于调试日志输出
     */
    public static String formatSql(String sql, Object[] params) {
        if (params == null || params.length == 0) {
            return sql;
        }
        StringTokenizer st = new StringTokenizer(sql, "?", true);
        StringBuilder bf = new StringBuilder();
        int i = 0;
        while (st.hasMoreTokens()) {
            String temp = st.nextToken();
            if (temp.equals("?") && i < params.length) {
                bf.append("'").append(params[i]).append("'");
                i++;
            } else {
                bf.append(temp);
            }
        }
        return bf.toString();
    }
}
