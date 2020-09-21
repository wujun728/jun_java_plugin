package cn.jiangzeyin.database.run.read;

import cn.jiangzeyin.database.base.ReadBase;
import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.config.SystemColumn;
import cn.jiangzeyin.database.util.SqlUtil;
import cn.jiangzeyin.database.util.Util;
import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.database.EntityInfo;
import cn.jiangzeyin.util.StringUtil;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 查询数据库操作
 *
 * @author jiangzeyin
 */
public class Select<T> extends ReadBase<T> {

    private String where;
    private String orderBy;
    private String sql;

    // 查询数据重多少开始
    private int limitStart;
    // 查询数据个数
    private int limitCount;
    // 主键值
    private Object keyValue;
    // 主键列
    private String keyColumn;

    /**
     * 获取主键列
     *
     * @return key
     * @author jiangzeyin
     */
    public String getKeyColumn() {
        if (StringUtils.isEmpty(keyColumn))
            return SystemColumn.getDefaultKeyName();
        return keyColumn;
    }

    /**
     * 设置主键列名
     * <p>
     * 默认为 id
     *
     * @param keyColumn 名称
     * @author jiangzeyin
     */
    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    /**
     * 获取主键值
     *
     * @return 键值
     * @author jiangzeyin
     */
    public Object getKeyValue() {
        return keyValue;
    }

    /**
     * 设置查询主键值
     *
     * @param keyValue 键值
     * @author jiangzeyin
     */
    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public int getLimitStart() {
        return limitStart;
    }

    /**
     * 设置查询开始位置
     *
     * @param limitStart 开始行
     * @author jiangzeyin
     */
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitCount() {
        return limitCount;
    }

    /**
     * 设置查询数量
     *
     * @param limitCount 共几行
     * @author jiangzeyin
     */
    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public Select() {
        // setTclass(ReflectUtil.getTClass(this));
    }

    public Select(int isDelete) {
        setIsDelete(isDelete);
    }

    public Select(String tag) {
        super.setTag(tag);
    }

    public String getWhere() {
        return where;
    }

    /**
     * 查询条件
     *
     * @param where 条件
     * @author jiangzeyin
     */
    public void setWhere(String where) {
        this.where = where;
    }

    public void appendWhere(String where) {
        String temp = StringUtil.convertNULL(this.where);
        where = StringUtil.convertNULL(where);
        this.where = String.format("%s %s", temp, where);
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setSql(String sql, int resultType) {
        this.sql = sql;
        setResultType(resultType);
    }

    public <T> T run(int resultType) {
        setResultType(resultType);
        return run();
    }

    /**
     * 查询
     *
     * @return 结果
     * @author jiangzeyin
     */
    public <T> T run() {
        try {
            if (getResultType() == Result.JsonObject)
                setLimitCount(1);

            String tag = getTag() == null ? EntityInfo.getDatabaseName(getTclass()) : getTag();
            setTag(tag);
            DataSource dataSource = DatabaseContextHolder.getReadDataSource(tag);
            //setConnection(connection);

            String runSql = getSql();
            if (StringUtils.isEmpty(runSql)) {
                runSql = SqlUtil.getSelectSql(this);
            }
            setRunSql(runSql);
            SystemDbLog.getInstance().info(runSql);
            List<Map<String, Object>> result = JdbcUtils.executeQuery(dataSource, runSql, getParameters());
            switch (getResultType()) {
                case Result.JsonArray:
                    return (T) JSON.parseArray(JSON.toJSONString(result));// new JSONArray(result);
                case Result.JsonObject: {
                    if (result == null || result.size() < 1)
                        return null;
                    Map<String, Object> map = result.get(0);
                    return (T) new JSONObject(map);
                }
                case Result.Entity:
                    return (T) Util.convertList(this, result);
                case Result.ListMap:
                    return (T) result;
                case Result.String:
                case Result.Integer: {
                    if (result == null || result.size() < 1)
                        return null;
                    Map<String, Object> map = result.get(0);
                    if (map == null)
                        return null;
                    //Object object = null;
                    String column = getColumns();
                    if (SystemColumn.getDefaultSelectColumns().equals(column)) {
                        // 默认取第一行一列数据
                        return (T) map.values().toArray()[0];
                    }
                    // 取指定列
                    String[] columns = StringUtil.stringToArray(column);
                    if (columns.length == 1) {
                        return (T) map.get(columns[0]);
                    }
                    return (T) map;
                }
                default:
                    break;
            }
        } catch (Exception e) {
            // TODO: handle exception
            isThrows(e);
        } finally {
            recycling();
            //JdbcUtils.close(getConnection());
            runEnd();
        }
        return null;
    }

    /**
     * 查询一条数据 返回实体 会自动追加 limit 1
     *
     * @return 结果
     * @author jiangzeyin
     */
    @SuppressWarnings("hiding")
    public <T> T runOne() {
        setLimitCount(1);
        List<T> list = run();
        if (list == null)
            return null;
        if (list.size() > 0)
            return list.get(0);
        return null;
    }
}
