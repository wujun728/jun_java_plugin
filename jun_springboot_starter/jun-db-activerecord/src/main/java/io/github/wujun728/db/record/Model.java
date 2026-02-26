package io.github.wujun728.db.record;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.wujun728.db.utils.RecordUtil;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Model - ActiveRecord 模式基类
 * <p>
 * 使用方式：
 * <pre>
 * // 1. 定义 Model 子类
 * &#064;Table(name = "sys_user")
 * public class User extends Model&lt;User&gt; {
 *     public static final User dao = new User().dao();
 * }
 *
 * // 2. 查询
 * User user = User.dao.findById(1);
 * List&lt;User&gt; users = User.dao.find("SELECT * FROM sys_user WHERE age > ?", 18);
 * User first = User.dao.findFirst("SELECT * FROM sys_user WHERE name = ?", "张三");
 *
 * // 3. 链式保存
 * new User().set("name", "张三").set("age", 20).save();
 *
 * // 4. 更新
 * user.set("name", "李四").update();
 *
 * // 5. 删除
 * user.delete();
 * User.dao.deleteById(1);
 *
 * // 6. 分页
 * Page&lt;User&gt; page = User.dao.paginate(1, 10, "SELECT *", "FROM sys_user WHERE age > ?", 18);
 *
 * // 7. 多数据源
 * User user = User.dao.use("slave").findById(1);
 * </pre>
 *
 * @param <M> Model 子类型，用于链式调用返回正确类型
 */
@SuppressWarnings("unchecked")
public abstract class Model<M extends Model<M>> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性数据，key 为数据库列名
     */
    private Map<String, Object> attrs = new LinkedHashMap<>();

    /**
     * 数据源配置名，null 表示使用主数据源
     */
    private transient String configName;

    /**
     * 缓存: 实际 Model 子类 Class（避免每次调用 _getUsefulClass() 做字符串匹配）
     */
    private transient volatile Class<? extends M> usefulClassCache;

    /**
     * 缓存: 表名（避免每次 CRUD 操作重复解析注解）
     */
    private transient volatile String tableNameCache;

    /**
     * 缓存: 主键名（避免每次 CRUD 操作重复反射）
     */
    private transient volatile String primaryKeyCache;

    // ==================== 配置方法 ====================

    /**
     * 创建 dao 对象，用于静态查询入口
     * <pre>
     * public static final User dao = new User().dao();
     * </pre>
     */
    public M dao() {
        return (M) this;
    }

    /**
     * 指定数据源，返回新实例以保证线程安全
     * <pre>
     * User.dao.use("slave").findById(1);
     * </pre>
     */
    public M use(String configName) {
        try {
            M model = (M) _getUsefulClass().newInstance();
            ((Model<?>) model).configName = configName;
            return model;
        } catch (Exception e) {
            throw new RuntimeException("创建Model实例失败", e);
        }
    }

    /**
     * 获取 DbPro 实例
     */
    protected DbPro _getDbPro() {
        if (StrUtil.isNotEmpty(configName)) {
            return Db.use(configName);
        }
        return Db.use();
    }

    /**
     * 获取实际 Model 子类的 Class（跳过 CGLIB 等代理类，结果缓存）
     */
    @SuppressWarnings("rawtypes")
    protected Class<? extends M> _getUsefulClass() {
        if (usefulClassCache != null) {
            return usefulClassCache;
        }
        Class c = getClass();
        Class<? extends M> result = (c.getName().indexOf("$$EnhancerBy") == -1
                && c.getName().indexOf("$$FastClassBy") == -1
                && c.getName().indexOf("_$$_") == -1) ? c : c.getSuperclass();
        usefulClassCache = result;
        return result;
    }

    // ==================== 表名与主键 ====================

    /**
     * 获取表名，优先级：@Table(JPA) > @TableName(MyBatis-Plus) > 类名转下划线（结果缓存）
     */
    protected String _getTableName() {
        if (tableNameCache != null) {
            return tableNameCache;
        }
        Class<?> clazz = _getUsefulClass();
        String result = null;
        if (AnnotationUtil.hasAnnotation(clazz, Table.class)) {
            result = AnnotationUtil.getAnnotationValue(clazz, Table.class, "name");
        }
        if (StrUtil.isEmpty(result) && AnnotationUtil.hasAnnotation(clazz, TableName.class)) {
            result = AnnotationUtil.getAnnotationValue(clazz, TableName.class, "value");
        }
        if (StrUtil.isEmpty(result)) {
            result = RecordUtil.toUnderlineCase(clazz.getSimpleName());
        }
        tableNameCache = result;
        return result;
    }

    /**
     * 获取主键名，优先级：@Id 注解字段 > TABLE_PK_MAP > 默认 "id"（结果缓存）
     */
    protected String _getPrimaryKey() {
        if (primaryKeyCache != null) {
            return primaryKeyCache;
        }
        Class<?> clazz = _getUsefulClass();
        List<String> pkNames = new ArrayList<>();
        for (RecordUtil.FieldMeta meta : RecordUtil.getFieldMetas(clazz)) {
            if (meta.field.isAnnotationPresent(Id.class) && meta.columnName != null) {
                pkNames.add(meta.columnName);
            }
        }
        String result;
        if (!pkNames.isEmpty()) {
            result = String.join(",", pkNames);
        } else {
            result = DbPro.getPkNames(_getTableName());
        }
        primaryKeyCache = result;
        return result;
    }

    // ==================== 属性操作（链式） ====================

    /**
     * 设置属性值，支持链式调用
     * <pre>
     * new User().set("name", "张三").set("age", 20).save();
     * </pre>
     */
    public M set(String attr, Object value) {
        attrs.put(attr, value);
        return (M) this;
    }

    /**
     * 批量设置属性
     */
    public M put(Map<String, Object> map) {
        attrs.putAll(map);
        return (M) this;
    }

    /**
     * 获取属性值
     */
    public <T> T get(String attr) {
        return (T) attrs.get(attr);
    }

    /**
     * 获取属性值，带默认值
     */
    public <T> T get(String attr, Object defaultValue) {
        Object result = attrs.get(attr);
        return result != null ? (T) result : (T) defaultValue;
    }

    public String getStr(String attr) {
        Object val = attrs.get(attr);
        return val != null ? val.toString() : null;
    }

    public Integer getInt(String attr) {
        Number val = (Number) attrs.get(attr);
        return val != null ? val.intValue() : null;
    }

    public Long getLong(String attr) {
        Number val = (Number) attrs.get(attr);
        return val != null ? val.longValue() : null;
    }

    public BigInteger getBigInteger(String attr) {
        return (BigInteger) attrs.get(attr);
    }

    public Double getDouble(String attr) {
        Number val = (Number) attrs.get(attr);
        return val != null ? val.doubleValue() : null;
    }

    public Float getFloat(String attr) {
        Number val = (Number) attrs.get(attr);
        return val != null ? val.floatValue() : null;
    }

    public Boolean getBoolean(String attr) {
        return (Boolean) attrs.get(attr);
    }

    public BigDecimal getBigDecimal(String attr) {
        return (BigDecimal) attrs.get(attr);
    }

    public Date getDate(String attr) {
        return (Date) attrs.get(attr);
    }

    public Time getTime(String attr) {
        return (Time) attrs.get(attr);
    }

    public Timestamp getTimestamp(String attr) {
        return (Timestamp) attrs.get(attr);
    }

    public byte[] getBytes(String attr) {
        return (byte[]) attrs.get(attr);
    }

    public Number getNumber(String attr) {
        return (Number) attrs.get(attr);
    }

    /**
     * 获取所有属性
     */
    public Map<String, Object> getAttrs() {
        return attrs;
    }

    /**
     * 获取属性名数组
     */
    public String[] getAttrNames() {
        return attrs.keySet().toArray(new String[0]);
    }

    /**
     * 获取属性值数组
     */
    public Object[] getAttrValues() {
        return attrs.values().toArray();
    }

    /**
     * 移除指定属性
     */
    public M remove(String attr) {
        attrs.remove(attr);
        return (M) this;
    }

    /**
     * 移除多个属性
     */
    public M remove(String... attrs) {
        if (attrs != null) {
            for (String attr : attrs) {
                this.attrs.remove(attr);
            }
        }
        return (M) this;
    }

    /**
     * 清除所有属性
     */
    public M clear() {
        attrs.clear();
        return (M) this;
    }

    // ==================== Record 互转 ====================

    /**
     * 转换为 Record
     */
    public Record toRecord() {
        Record record = new Record();
        record.setColumns(attrs);
        return record;
    }

    /**
     * 从 Record 填充属性
     */
    protected M _fromRecord(Record record) {
        if (record != null) {
            this.attrs.putAll(record.getColumns());
        }
        return (M) this;
    }

    /**
     * Record 转 Model 实例
     */
    protected M _recordToModel(Record record) {
        if (record == null) {
            return null;
        }
        try {
            M model = (M) _getUsefulClass().newInstance();
            ((Model<?>) model).configName = this.configName;
            model._fromRecord(record);
            return model;
        } catch (Exception e) {
            throw new RuntimeException("创建Model实例失败", e);
        }
    }

    /**
     * Record 列表转 Model 列表
     */
    protected List<M> _recordsToModels(List<Record> records) {
        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }
        List<M> models = new ArrayList<>(records.size());
        for (Record record : records) {
            models.add(_recordToModel(record));
        }
        return models;
    }

    // ==================== CRUD 操作 ====================

    /**
     * 保存到数据库
     * <pre>
     * new User().set("name", "张三").set("age", 20).save();
     * </pre>
     */
    public boolean save() {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        return _getDbPro().save(tableName, primaryKey, toRecord());
    }

    /**
     * 更新到数据库（根据主键）
     * <pre>
     * user.set("name", "李四").update();
     * </pre>
     */
    public boolean update() {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        return _getDbPro().update(tableName, primaryKey, toRecord());
    }

    /**
     * 从数据库删除（根据当前对象的主键值）
     * <pre>
     * user.delete();
     * </pre>
     */
    public boolean delete() {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        return _getDbPro().delete(tableName, primaryKey, toRecord());
    }

    /**
     * 根据主键删除
     * <pre>
     * User.dao.deleteById(1);
     * </pre>
     */
    public boolean deleteById(Object idValue) {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        return _getDbPro().deleteById(tableName, primaryKey, idValue);
    }

    /**
     * 根据复合主键删除
     */
    public boolean deleteByIds(Object... idValues) {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        return _getDbPro().deleteById(tableName, primaryKey, idValues);
    }

    // ==================== 查询操作 ====================

    /**
     * 根据主键查询
     * <pre>
     * User user = User.dao.findById(1);
     * </pre>
     */
    public M findById(Object idValue) {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        Record record = _getDbPro().findByIds(tableName, primaryKey, idValue);
        return _recordToModel(record);
    }

    /**
     * 根据复合主键查询
     */
    public M findByIds(Object... idValues) {
        String tableName = _getTableName();
        String primaryKey = _getPrimaryKey();
        Record record = _getDbPro().findByIds(tableName, primaryKey, idValues);
        return _recordToModel(record);
    }

    /**
     * 查询所有记录
     */
    public List<M> findAll() {
        String tableName = _getTableName();
        List<Record> records = _getDbPro().findAll(tableName);
        return _recordsToModels(records);
    }

    /**
     * 通过 SQL 查询列表
     * <pre>
     * List&lt;User&gt; users = User.dao.find("SELECT * FROM sys_user WHERE age > ?", 18);
     * </pre>
     */
    public List<M> find(String sql, Object... paras) {
        List<Record> records = _getDbPro().find(sql, paras);
        return _recordsToModels(records);
    }

    /**
     * 通过 SQL 查询列表（无参数）
     */
    public List<M> find(String sql) {
        return find(sql, DbPro.NULL_PARA_ARRAY);
    }

    /**
     * 通过 SQL 查询第一条记录
     * <pre>
     * User user = User.dao.findFirst("SELECT * FROM sys_user WHERE name = ?", "张三");
     * </pre>
     */
    public M findFirst(String sql, Object... paras) {
        List<M> result = find(sql, paras);
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * 通过 SQL 查询第一条记录（无参数）
     */
    public M findFirst(String sql) {
        return findFirst(sql, DbPro.NULL_PARA_ARRAY);
    }

    // ==================== 分页查询 ====================

    /**
     * 分页查询
     * <pre>
     * Page&lt;User&gt; page = User.dao.paginate(1, 10, "SELECT *", "FROM sys_user WHERE age > ?", 18);
     * </pre>
     */
    public Page<M> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) {
        Page<Record> recordPage = _getDbPro().paginate(pageNumber, pageSize, select, sqlExceptSelect, paras);
        return _recordPageToModelPage(recordPage);
    }

    /**
     * 分页查询（无参数）
     */
    public Page<M> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        Page<Record> recordPage = _getDbPro().paginate(pageNumber, pageSize, select, sqlExceptSelect);
        return _recordPageToModelPage(recordPage);
    }

    /**
     * 完整 SQL 分页查询
     */
    public Page<M> paginateByFullSql(int pageNumber, int pageSize, String totalRowSql, String findSql, Object... paras) {
        Page<Record> recordPage = _getDbPro().paginateByFullSql(pageNumber, pageSize, totalRowSql, findSql, paras);
        return _recordPageToModelPage(recordPage);
    }

    /**
     * Record 分页转 Model 分页
     */
    protected Page<M> _recordPageToModelPage(Page<Record> recordPage) {
        List<M> models = _recordsToModels(recordPage.getList());
        return new Page<>(models, recordPage.getPageNumber(), recordPage.getPageSize(),
                recordPage.getTotalPage(), recordPage.getTotalRow());
    }

    // ==================== 事务支持 ====================

    /**
     * 事务执行
     * <pre>
     * User.dao.tx(() -> {
     *     new User().set("name", "A").save();
     *     new User().set("name", "B").save();
     *     return true;
     * });
     * </pre>
     */
    public boolean tx(IAtom atom) {
        return _getDbPro().tx(atom);
    }

    // ==================== Object 方法 ====================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_getUsefulClass().getSimpleName()).append('{');
        boolean first = true;
        for (Map.Entry<String, Object> e : attrs.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            Object value = e.getValue();
            if (value != null) {
                value = value.toString();
            }
            sb.append(e.getKey()).append(':').append(value);
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Model)) return false;
        if (o == this) return true;
        if (_getUsefulClass() != ((Model<?>) o)._getUsefulClass()) return false;
        return this.attrs.equals(((Model<?>) o).attrs);
    }

    @Override
    public int hashCode() {
        return (attrs == null ? 0 : attrs.hashCode()) ^ (_getUsefulClass().hashCode());
    }
}
