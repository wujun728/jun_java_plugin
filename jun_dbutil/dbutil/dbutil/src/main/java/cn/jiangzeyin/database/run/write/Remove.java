package cn.jiangzeyin.database.run.write;

import cn.jiangzeyin.database.base.Base;
import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.config.SystemColumn;
import cn.jiangzeyin.database.util.SqlUtil;
import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.system.SystemExecutorService;
import cn.jiangzeyin.database.EntityInfo;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 移除数据 即更改isDelete 状态
 *
 * @author jiangzeyin
 */
public class Remove<T> extends Base<T> {


    public enum Type {
        /**
         * 物理删除
         */
        delete,
        /**
         * 撤销清除
         */
        recovery,
        /**
         * 清除
         */
        remove
    }

    private String ids;
    private String where;
    private List<Object> parameters;
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    /**
     *
     */
    public Remove(Type type) {
        // TODO Auto-generated constructor stub
        this(type, false);
    }

    public Remove(Type type, boolean isThrows) {
        // TODO Auto-generated constructor stub
        this.type = type;
        setThrows(isThrows);
        if (SystemColumn.Active.NO_ACTIVE == SystemColumn.Active.getActiveValue()) {
            if (type != Type.delete)
                throw new IllegalArgumentException("plase set systemColumn.active");
        }
    }

    public List<Object> getParameters() {
        if (parameters == null)
            return new ArrayList<>();
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public void setIds(int id) {
        this.ids = String.valueOf(id);
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    /**
     * @author jiangzeyin
     */
    public void run() {
        SystemExecutorService.execute(() -> {
            // TODO Auto-generated method stub
            int count = syncRun();
            if (count <= 0) {
                SystemDbLog.getInstance().warn(runSql + " yx " + count);
            }
        });
    }

    /**
     * @return 影响行数
     * @author jiangzeyin
     */
    public int syncRun() {
        try {
            String tag = EntityInfo.getDatabaseName(getTclass());
            String sql = SqlUtil.getRemoveSql(getTclass(), type, getIds(), getWhere());
            SystemDbLog.getInstance().info(sql);
            setRunSql(sql);
            DataSource dataSource = DatabaseContextHolder.getWriteDataSource(tag);
            return JdbcUtils.executeUpdate(dataSource, sql, getParameters());
        } catch (Exception e) {
            // TODO: handle exception
            isThrows(e);
        } finally {
            recycling();
            parameters = null;
            ids = null;
            where = null;
            runEnd();
        }
        return 0;
    }

}
