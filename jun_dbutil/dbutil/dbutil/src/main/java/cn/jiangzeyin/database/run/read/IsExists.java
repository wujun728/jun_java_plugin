package cn.jiangzeyin.database.run.read;

import cn.jiangzeyin.database.base.Base;
import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.util.SqlUtil;
import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.database.EntityInfo;
import cn.jiangzeyin.util.StringUtil;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 判断是否存在
 *
 * @author jiangzeyin
 */
public class IsExists<T> extends Base<T> {
    private String keyColumn;
    private Object keyValue;
    private String where;
    private List<Object> parameters;
    private String column;
    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<Object> getParameters() {
        List<Object> paList = new LinkedList<>();
        paList.add(getKeyValue());
        if (parameters != null)
            paList.addAll(parameters);
        return paList;
    }

    public void setParameters(List<Object> whereParameters) {
        this.parameters = whereParameters;
    }

    public void setParameters(Object... parameters_) {
        if (parameters == null)
            parameters = new LinkedList<>();
        Collections.addAll(parameters, parameters_);
    }

    public IsExists(String keyColumn, String keyValue) {
        this.keyColumn = keyColumn;
        this.keyValue = keyValue;
    }

    public IsExists() {
        setThrows(true);
    }

    public IsExists(boolean isThrows) {
        setThrows(isThrows);
    }

    public IsExists(String tag) {
        setTag(tag);
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public Object getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    /**
     * 判断是否存在
     *
     * @return 是否存在
     * @author jiangzeyin
     */
    public boolean run() {
        Class runClass = getTclass();
        synchronized (runClass) {
            try {
                if (StringUtils.isEmpty(getKeyColumn()))
                    throw new IllegalArgumentException(" keycolumn 不能为null");
                String tag = getTag();
                if (StringUtils.isEmpty(tag))
                    tag = EntityInfo.getDatabaseName(runClass);
                String sql = SqlUtil.getIsExistsSql(runClass, getKeyColumn(), getWhere(), getColumn(), getLimit());
                //SystemLog.SystemLog(LogType.sql, sql);
                setRunSql(sql);
                SystemDbLog.getInstance().info(sql);
                DataSource dataSource = DatabaseContextHolder.getReadDataSource(tag);
                List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource, sql, getParameters());
                if (list == null || list.size() < 1)
                    return false;
                Map<String, Object> map = list.get(0);
                Object object = map.get("countSum");
                if (object == null)
                    throw new RuntimeException("查询结果没有countSum");
                if (object instanceof Long) {
                    Long count = (Long) object;
                    if (count > 0L)
                        return true;
                } else if (object instanceof Integer) {
                    Integer count = (Integer) object;
                    if (count > 0)
                        return true;
                } else {
                    throw new RuntimeException("查询结果类型异常" + object);
                }
                return false;
            } catch (Exception e) {
                // TODO: handle exception
                isThrows(e);
            } finally {
                recycling();
                this.parameters = null;
                runEnd();
            }
            return true;
        }
    }

    /**
     * 查询是否存在
     *
     * @return 结果
     * @author jiangzeyin
     */
    public Object runColumn() {
        Class runClass = getTclass();
        synchronized (runClass) {
            try {
                if (StringUtils.isEmpty(getKeyColumn()))
                    throw new IllegalArgumentException(" keyColumn 不能为null");
                String tag = getTag();
                if (StringUtils.isEmpty(tag))
                    tag = EntityInfo.getDatabaseName(runClass);
                String sql = SqlUtil.getIsExistsSql(runClass, getKeyColumn(), getWhere(), getColumn(), getLimit());
                setRunSql(sql);
                SystemDbLog.getInstance().info(sql);
                DataSource dataSource = DatabaseContextHolder.getReadDataSource(tag);
                List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource, sql, getParameters());
                if (list == null || list.size() < 1)
                    return null;
                Map<String, Object> map = list.get(0);

                String[] keys = StringUtil.stringToArray(getColumn());
                if (keys == null || keys.length <= 0) {
                    return map.get("countSum");
                } else if (keys.length == 1) {
                    return map.get(keys[0]);
                } else
                    return map;
            } catch (Exception e) {
                // TODO: handle exception
                isThrows(e);
            } finally {
                recycling();
                this.parameters = null;
                runEnd();
            }
            return null;
        }
    }
}
