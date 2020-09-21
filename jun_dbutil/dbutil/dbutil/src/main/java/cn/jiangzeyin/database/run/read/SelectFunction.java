package cn.jiangzeyin.database.run.read;

import cn.jiangzeyin.database.base.ReadBase;
import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.util.SqlUtil;
import cn.jiangzeyin.system.SystemDbLog;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 查询函数
 *
 * @author jiangzeyin
 */
public class SelectFunction<T> extends ReadBase<T> {

    private String name;

    public SelectFunction(String name, String tag) {
        // TODO Auto-generated constructor stub
        this.name = name;
        super.setTag(tag);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T run() {
        try {
            DataSource dataSource = DatabaseContextHolder.getReadDataSource(getTag());
            String sql = SqlUtil.function(getName(), getParameters());
            setRunSql(sql);
            SystemDbLog.getInstance().info(sql);
            List<Map<String, Object>> list = JdbcUtils.executeQuery(dataSource, sql, getParameters());
            if (list == null || list.size() < 1)
                return null;
            Map<String, Object> map = list.get(0);
            if (map == null)
                return null;
            Collection<Object> collection = map.values();
            return (T) collection.toArray()[0];
        } catch (Exception e) {
            // TODO: handle exception
            isThrows(e);
        } finally {
            recycling();
            runEnd();
        }
        return null;
    }
}
