package cn.jiangzeyin.database.util;


import cn.jiangzeyin.database.EntityInfo;
import cn.jiangzeyin.database.Page;
import cn.jiangzeyin.database.base.WriteBase;
import cn.jiangzeyin.database.config.ModifyUser;
import cn.jiangzeyin.database.config.SystemColumn;
import cn.jiangzeyin.database.run.read.Select;
import cn.jiangzeyin.database.run.read.SelectPage;
import cn.jiangzeyin.database.run.write.Insert;
import cn.jiangzeyin.database.run.write.Remove;
import cn.jiangzeyin.database.run.write.Update;
import cn.jiangzeyin.util.Assert;
import cn.jiangzeyin.util.StringUtil;
import cn.jiangzeyin.util.ref.ReflectCache;
import cn.jiangzeyin.util.ref.ReflectUtil;
import com.alibaba.druid.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * sql 工具
 *
 * @author jiangzeyin
 */
public class SqlUtil {

    /**
     * 判断是否写
     *
     * @param field 字段
     * @return boolean
     * @author jiangzeyin
     */
    private static boolean isWrite(Field field) {
        return field.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL) && field.getModifiers() != (Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL) && !field.getName().startsWith("_");
    }

    /**
     * @param write write
     * @param data  数据
     * @return 结果对象
     * @throws IllegalArgumentException yi
     * @throws IllegalAccessException   yic
     * @author jiangzeyin
     */
    private static SqlAndParameters getWriteSql(WriteBase<?> write, Object data) throws Exception {
        if (data == null)
            data = write.getData();
        Assert.notNull(data, String.format("%s", write.getTclass(false)));

        List<String> cloums = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        HashMap<String, String> systemMap = new HashMap<>();

        List<String> remove = write.getRemove();
        HashMap<String, Class<?>> refMap = write.getRefMap();

        for (Class<?> calzz = data.getClass(); calzz != Object.class; calzz = calzz.getSuperclass()) {
            Field[] fields = ReflectCache.getDeclaredFields(calzz);
            for (Field field : fields) {
                if (!isWrite(field))
                    continue;
                String name = field.getName();
                // 判断排除字段
                if (remove != null && remove.contains(name.toLowerCase())) {
                    continue;
                }
                // 系统默认不可以操作
                if (SystemColumn.isWriteRemove(name))
                    continue;
                cloums.add(name);
                // 判断是否为系统字段
                String value1 = SystemColumn.getDefaultValue(name);// getSystemValue(field.getName());
                if (value1 == null) {
                    Object va = ReflectUtil.getFieldValue(data, name);
                    // 密码字段
                    if (SystemColumn.getPwdColumn().equalsIgnoreCase(name)) {
                        systemMap.put(name, "PASSWORD(?)");
                        values.add(va);
                    } else {
                        // 读取外键
                        if (refMap != null && refMap.containsKey(name.toLowerCase())) {
                            Object refData = ReflectUtil.getFieldValue(data, field.getName());
                            if (refData == null)
                                throw new RuntimeException(name + " 为null");
                            va = ReflectUtil.getFieldValue(refData, write.getRefKey());
                        }
                        values.add(va);
                    }
                } else {
                    systemMap.put(name, value1);
                }
            }
        }
        SqlAndParameters sqlAndParameters = new SqlAndParameters();
        sqlAndParameters.setParameters(values);
        sqlAndParameters.setCloums(cloums);
        sqlAndParameters.setSystemMap(systemMap);
        return sqlAndParameters;
    }

    /**
     * 获取操作 信息
     *
     * @param write write
     * @return 结果对象
     * @throws IllegalArgumentException y
     * @throws IllegalAccessException   y
     * @author jiangzeyin
     */
    private static SqlAndParameters getWriteSql(WriteBase<?> write) throws Exception {
        return getWriteSql(write, null);
    }

    /**
     * 获取添加对象信息
     *
     * @param insert 对象
     * @return 结果
     * @throws IllegalArgumentException y
     * @throws IllegalAccessException   y
     * @author jiangzeyin
     */
    public static SqlAndParameters getInsertSql(WriteBase<?> insert) throws Exception {
        SqlAndParameters sqlAndParameters = getWriteSql(insert);
        int isDelete = SystemColumn.Active.NO_ACTIVE;
        if (!StringUtils.isEmpty(SystemColumn.Active.getColumn())) {
            Object isDeleteF = ReflectUtil.getFieldValue(insert.getData(), SystemColumn.Active.getColumn());
            isDelete = isDeleteF == null ? SystemColumn.Active.getActiveValue() : Integer.parseInt(isDeleteF.toString());
        }
        sqlAndParameters.setSql(makeInsertToTableSql(insert.getData().getClass(), insert.getOptUserId(), sqlAndParameters.getCloums(), sqlAndParameters.getSystemMap(), isDelete));
        return sqlAndParameters;
    }

    /**
     * @param insert 对象
     * @return 结果数组
     * @throws IllegalArgumentException y
     * @throws IllegalAccessException   y
     * @author jiangzeyin
     */
    public static SqlAndParameters[] getInsertSql(Insert<?> insert) throws Exception {
        List<?> list = insert.getList();
        SqlAndParameters[] andParameters = new SqlAndParameters[list.size()];
        for (int i = 0; i < andParameters.length; i++) {
            Object object = list.get(i);
            if (object == null)
                continue;
            SqlAndParameters sqlAndParameters = getWriteSql(insert, object);
            int isDelete = SystemColumn.Active.NO_ACTIVE;
            if (!StringUtils.isEmpty(SystemColumn.Active.getColumn())) {
                Object isDeleteF = ReflectUtil.getFieldValue(object, SystemColumn.Active.getColumn());
                isDelete = isDeleteF == null ? SystemColumn.Active.getActiveValue() : Integer.parseInt(isDeleteF.toString());
            }
            sqlAndParameters.setSql(makeInsertToTableSql(object.getClass(), insert.getOptUserId(), sqlAndParameters.getCloums(), sqlAndParameters.getSystemMap(), isDelete));
            andParameters[i] = sqlAndParameters;
        }
        return andParameters;
    }

    /**
     * 获取修改对象信息
     *
     * @param update 更新
     * @return 结果
     * @throws IllegalArgumentException y
     * @throws IllegalAccessException   y
     * @author jiangzeyin
     */
    public static SqlAndParameters getUpdateSql(Update<?> update) throws Exception {
        SqlAndParameters sqlAndParameters;
        StringBuffer sbSql;
        // 更新部分列
        if (update.getUpdate() != null) {
            sqlAndParameters = new SqlAndParameters();
            String sql = makeUpdateToTableSql(getTableName(update.getTclass(), false), update.getUpdate());
            sbSql = new StringBuffer(sql);
        } else {
            // 按照实体更新
            sqlAndParameters = getWriteSql(update);
            String sql = makeUpdateToTableSql(getTableName(update.getData().getClass(), false), sqlAndParameters.getCloums(), sqlAndParameters.getSystemMap());
            sbSql = new StringBuffer(sql);
        }
        // 获取修改数据的操作人
        if (update.getOptUserId() != -1) {
            Class<?> class1 = update.getTclass(false);
            if (class1 == null)
                class1 = update.getData().getClass();
            if (ModifyUser.Modify.isModifyClass(class1)) {
                sbSql.append(",").append(ModifyUser.Modify.getColumnUser()).append("=").append(update.getOptUserId());
                sbSql.append(",").append(ModifyUser.Modify.getColumnTime()).append("=").append(ModifyUser.Modify.getModifyTime()).append("");
            }
        }
        // 处理where 条件
        boolean isAppendWhere = false;
        boolean isWhere = false;
        // 获取主键 数据
        if (!StringUtils.isEmpty(StringUtil.convertNULL(update.getKeyValue()))) {
            sbSql.append(" where ");
            sbSql.append(update.getKeyColumn());
            sbSql.append("=")//
                    .append("'")//
                    .append(update.getKeyValue())//
                    .append("'");
            isAppendWhere = true;
            isWhere = true;
        }
        // 添加where 条件
        if (!StringUtils.isEmpty(update.getWhere())) {
            sbSql.append(isAppendWhere ? " and " : " where ");
            sbSql.append(update.getWhere());
            isWhere = true;
        }
        // 没有任何更新条件
        if (!isWhere) {
            if (update.getData() != null) {
                Object objId = ReflectUtil.getFieldValue(update.getData(), SystemColumn.getDefaultKeyName());
                Assert.notNull(objId, "没有找到任何更新条件");
                sbSql.append(" where id=");
                sbSql.append(Long.parseLong(objId.toString()));
            } else {
                sbSql.append(" where ")//
                        .append(update.getKeyColumn())//
                        .append("=")//
                        .append(update.getKeyValue());
            }
        }

        sqlAndParameters.setSql(sbSql);
        // 追加where 的参数
        List<Object> parameters;
        if (update.getUpdate() == null)
            parameters = sqlAndParameters.getParameters();
        else {
            List<Object> paList = new LinkedList<>();
            Collections.addAll(paList, update.getUpdate().values().toArray());
            parameters = paList;
        }
        if (parameters == null)
            parameters = update.getWhereParameters();
        else if (update.getWhereParameters() != null)
            parameters.addAll(update.getWhereParameters());
        sqlAndParameters.setParameters(parameters);
        return sqlAndParameters;
    }

    /**
     * 获取分页操作信息
     *
     * @param select 查询对象
     * @return 数组
     * @author jiangzeyin
     */
    public static String[] getSelectPageSql(SelectPage<?> select) {
        StringBuffer sql = new StringBuffer("select ");
        sql.append(select.getColumns())//
                .append(" from ")//
                .append(getTableName(select.getTclass(), true, select.getIndex(), false))//
                .append(" ");//
        String[] countSql = new String[2];
        countSql[0] = getCountSql(sql.toString(), select.getPage());
        countSql[1] = getMysqlPageSql(select.getPage(), sql);//sql.toString();
        return countSql;
    }

    public static String[] getSelectPageSql(Page<?> page) {
        StringBuffer sql = new StringBuffer(page.getSql());
        String[] countSql = new String[2];
        countSql[0] = getCountSql(sql.toString(), page);
        countSql[1] = getMysqlPageSql(page, sql);
        return countSql;
    }

    /**
     * 获取读取外键的sql 语句
     *
     * @param ref       类
     * @param keyColumn 列
     * @return sql
     * @author jiangzeyin
     */
    static String getRefSql(Class<?> ref, String keyColumn, String where) {
        StringBuilder sql = new StringBuilder("select ")//
                .append(" * from ")//
                .append(getTableName(ref))//
                .append(" where ")//
                .append(keyColumn)//
                .append("=?");//
        if (!StringUtils.isEmpty(where)) {
            sql.append(" and ").append(where);
        }
        return sql.toString();
    }

    /**
     * @param clas      类
     * @param keyColumn 列
     * @param where     条件
     * @return sql
     * @author jiangzeyin
     */
    public static String getIsExistsSql(Class<?> clas, String keyColumn, String where, String column, int limit) {
        StringBuilder sql = new StringBuilder("select ");//
        if (StringUtils.isEmpty(column)) {
            sql.append(" count(1) as countSum from ");//
        } else {
            sql.append(" ").append(column).append(" from ");//
        }
        sql.append(getTableName(clas))//
                .append(" where ")//
                .append(keyColumn)//
                .append("=?");//
        if (!StringUtils.isEmpty(where)) {
            // 判断or 条件
            String tempWhere = where.toLowerCase().trim();
            sql.append(tempWhere.startsWith("or") ? "" : " and ").append(where);
        }
        if (limit <= 0)
            limit = 1;
        sql.append(" limit ").append(limit);
        return sql.toString();
    }

    /**
     * 获取移除sql 语句
     *
     * @param cls   类
     * @param ids   ids
     * @param where 条件
     * @return sql
     * @author jiangzeyin
     */
    public static String getRemoveSql(Class<?> cls, Remove.Type type, String ids, String where) {
        StringBuilder sql = new StringBuilder();
        if (type == Remove.Type.delete) {
            sql.append("delete from ")//
                    .append(getTableName(cls, false));
        } else {
            int status = type == Remove.Type.remove ? SystemColumn.Active.getInActiveValue() : SystemColumn.Active.getActiveValue();
            sql.append("update ")//
                    .append(getTableName(cls, false))//
                    .append(String.format(" set " + SystemColumn.Active.getColumn() + "=%d", status));
            if (SystemColumn.Modify.isStatus()) {
                //,modifyTime=UNIX_TIMESTAMP(NOW())
                sql.append(",").append(SystemColumn.Modify.getColumn()).append("=").append(SystemColumn.Modify.getTime());
            }
        }
        boolean isWhere = false;
        if (!StringUtils.isEmpty(ids)) {
            sql.append(" where id in(").append(ids).append(")");
            isWhere = true;
        }
        if (!StringUtils.isEmpty(where)) {
            sql.append(isWhere ? " and " : " where ").append(where);
        }
        return sql.toString();
    }

    /**
     * 获取查询信息
     *
     * @param select 对象
     * @return sql
     * @throws IllegalArgumentException y
     * @throws IllegalAccessException   y
     * @author jiangzeyin
     */
    public static String getSelectSql(Select<?> select) throws IllegalArgumentException, IllegalAccessException {
        StringBuilder sql = new StringBuilder("select ");
        sql.append(select.getColumns())//
                .append(" from ")//
                .append(getTableName(select.getTclass(), true, select.getIndex(), false))//
                .append(" ");//
        boolean isWhere = false;
        // datakey
        if (!StringUtils.isEmpty(StringUtil.convertNULL(select.getKeyValue()))) {
            isWhere = true;
            sql.append("where ")//
                    .append(select.getKeyColumn())//
                    .append("=")//
                    .append("'").append(select.getKeyValue()).append("'");
        }
        // 条件
        if (!StringUtils.isEmpty(select.getWhere())) {
            sql.append(isWhere ? " and " : " where ")//
                    .append(select.getWhere());
            if (!isWhere)
                isWhere = true;
        }
        // 查询数据状态
        if (select.getIsDelete() != SystemColumn.Active.NO_ACTIVE) {
            //
            sql.append(isWhere ? " and " : " where ").
                    append(SystemColumn.Active.getColumn()).append("=").append(select.getIsDelete());
        }
        // 排序
        if (!StringUtils.isEmpty(select.getOrderBy())) {
            sql.append(" order by ")//
                    .append(select.getOrderBy());
        }
        // limit
        {
            if (select.getLimitStart() == 0 && select.getLimitCount() != 0)
                sql.append(" limit ")//
                        .append(select.getLimitCount());
            else if (select.getLimitStart() > 0)
                sql.append(" limit ")//
                        .append(select.getLimitStart())//
                        .append(",")//
                        .append(select.getLimitCount());
        }
        return sql.toString();
    }

    /**
     * mysql 分页
     *
     * @param page      page
     * @param sqlBuffer sql
     * @return sql
     * @author jiangzeyin
     */
    private static String getMysqlPageSql(Page<?> page, StringBuffer sqlBuffer) {
        // 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        long offset = (page.getPageNo() - 1) * page.getPageSize();
        doWhere(sqlBuffer, page);
        // 判断是否需要排序
        if (page.getOrderBy() != null && !"".equals(page.getOrderBy())) {
            sqlBuffer.append(" order by ").append(page.getOrderBy());
        }
        sqlBuffer.append(" limit ").append(offset).append(",").append(page.getPageSize());
        return sqlBuffer.toString();
    }

    private static void doWhere(StringBuffer sqlBuffer, Page page) {
        if (!StringUtils.isEmpty(page.getWhereWord())) {
            if (sqlBuffer.indexOf("where") == -1) {
                sqlBuffer.append(" where ");
            } else {
                sqlBuffer.append(" and ");
            }
            sqlBuffer.append(page.getWhereWord());
        }
    }

    /**
     * 获取分页总数sql
     *
     * @param sql  sql
     * @param page page
     * @return sql
     * @author jiangzeyin
     */
    private static String getCountSql(String sql, Page<?> page) {
        StringBuffer sqlBuffer = new StringBuffer(sql);
        doWhere(sqlBuffer, page);
        return "select count(1)  as count from (" + sqlBuffer + ") as total";
    }

    /**
     * 获取表明 默认添加索引
     *
     * @param class1 类
     * @return 表名
     * @author jiangzeyin
     */
    private static String getTableName(Class<?> class1) {
        return getTableName(class1, true, null, false);
    }

    private static String getTableName(Class<?> class1, boolean isIndex) {
        return getTableName(class1, isIndex, null, false);
    }

    /**
     * 获取表明 和 自动加主键索引
     *
     * @param class1         类
     * @param isIndex        索引
     * @param index          索引
     * @param isDatabaseName 数据库名
     * @return 表名
     * @author jiangzeyin
     */
    private static String getTableName(Class<?> class1, boolean isIndex, String index, boolean isDatabaseName) {
        return EntityInfo.getTableName(class1, isIndex, index, isDatabaseName);
    }

    /**
     * 获取运行sql function
     *
     * @param functionName 名称
     * @param parameters   参数
     * @return 结果
     * @author jiangzeyin
     */
    public static String function(String functionName, List<Object> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ").append(functionName).append("(");
        if (parameters != null && parameters.size() > 0)
            for (int i = 0; i < parameters.size(); i++) {
                if (i > 0)
                    sb.append(",");
                sb.append("?");
            }
        sb.append(")");
        return sb.toString();
    }

    /**
     * @param class1     类
     * @param createUser 创建者
     * @param names      名称
     * @param systemMap  map
     * @return j结果
     * @author jiangzeyin
     */
    private static String makeInsertToTableSql(Class<?> class1, int createUser, Collection<String> names, HashMap<String, String> systemMap, int isDeleteValue) {
        String tableName = getTableName(class1, false);
        StringBuilder sql = new StringBuilder() //
                .append("insert into ") //
                .append(tableName) //
                .append("("); //

        int nameCount = 0;
        StringBuilder value = new StringBuilder();
        boolean isDelete = false;
        //
        for (String name : names) {
            if (nameCount > 0) {
                sql.append(",");
            }
            sql.append(name);
            String va = systemMap.get(name);
            if (nameCount > 0) {
                value.append(",");
            }
            if (va == null)
                value.append("?");
            else {
                if (isDeleteValue != SystemColumn.Active.NO_ACTIVE && SystemColumn.Active.getColumn().equals(name)) {
                    value.append(isDeleteValue);
                    isDelete = true;
                } else {
                    value.append(va);
                }
            }
            nameCount++;
        }

        // 获取修改数据的操作人
        if (createUser != -1 && ModifyUser.Create.isCreateClass(class1)) {
            sql.append(",").append(ModifyUser.Create.getColumnUser());
            value.append(",").append(createUser);
        }
        // 处理插入默认状态值
        if (isDeleteValue != SystemColumn.Active.NO_ACTIVE && !isDelete) {
            sql.append(",").append(SystemColumn.Active.getColumn());
            value.append(",").append(isDeleteValue);
        }
        sql.append(") values (");
        sql.append(value);
        sql.append(")");
        return sql.toString();
    }

    /**
     * 获取更新sql 语句
     *
     * @param tableName 表名
     * @param names     列名
     * @param systemMap 值
     * @return 结果
     * @author jiangzeyin
     */
    private static String makeUpdateToTableSql(String tableName, Collection<String> names, HashMap<String, String> systemMap) {
        StringBuilder sql = new StringBuilder() //
                .append("update ") //
                .append(tableName) //
                .append(" set "); //

        int nameCount = 0;
        for (String name : names) {
            if (nameCount > 0) {
                sql.append(",");
            }
            sql.append(name);
            sql.append("=");
            String va = systemMap.get(name);
            if (va == null)
                sql.append("?");
            else
                sql.append(va);
            nameCount++;
        }
        if (SystemColumn.Modify.isStatus()) {
            String time = SystemColumn.Modify.getColumn() + "=" + SystemColumn.Modify.getTime();
            if (sql.indexOf(time) == -1)
                sql.append(",").append(time);
        }
        return sql.toString();
    }

    /**
     * 获取修改指定字段的sql
     *
     * @param tableName 表名
     * @param columns   列名
     * @return 结果
     * @author jiangzeyin
     */
    private static String makeUpdateToTableSql(String tableName, HashMap<String, Object> columns) {
        StringBuilder sql = new StringBuilder() //
                .append("update ") //
                .append(tableName) //
                .append(" set "); //

        int nameCount = 0;
        Iterator<Map.Entry<String, Object>> iterator = columns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String name = entry.getKey();
            Object obj_value = entry.getValue();
            if (nameCount > 0) {
                sql.append(",");
            }
            sql.append(name);
            sql.append("=");
            { // 判断是否为系统字段
                String va = SystemColumn.getDefaultValue(name);//  getSystemValue(name);
                if (va == null) {
                    // 密码字段处理
                    if (SystemColumn.getPwdColumn().equalsIgnoreCase(name)) {
                        sql.append("PASSWORD(?)");
                    } else {
                        // sql 函数处理
                        String value = StringUtil.convertNULL(obj_value);
                        if (value.startsWith("#{") && value.endsWith("}")) {
                            value = value.substring(value.indexOf("#{") + 2, value.indexOf("}"));
                            sql.append(value);
                            iterator.remove();
                        } else {
                            sql.append("?");
                        }
                    }
                } else
                    sql.append(va);
            }
            nameCount++;
        }
        if (SystemColumn.Modify.isStatus()) {
            sql.append(",").append(SystemColumn.Modify.getColumn()).append("=").append(SystemColumn.Modify.getTime());
        }
        return sql.toString();
    }
}
