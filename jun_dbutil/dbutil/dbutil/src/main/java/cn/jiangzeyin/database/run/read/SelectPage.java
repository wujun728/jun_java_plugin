package cn.jiangzeyin.database.run.read;

import cn.jiangzeyin.database.Page;
import cn.jiangzeyin.database.base.ReadBase;
import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.util.SqlUtil;
import cn.jiangzeyin.database.util.Util;
import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.util.Assert;
import cn.jiangzeyin.database.EntityInfo;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * 分页查询
 *
 * @author jiangzeyin
 */
public class SelectPage<T> extends ReadBase<T> {

    private Page<T> page;

    public static final int PageResultType = -2;

    /**
     *
     */
    public SelectPage(Page<T> page) {
        // TODO Auto-generated constructor stub
        this.page = page;
    }

    /**
     * @param page       page
     * @param resultType 返回类型
     */
    public SelectPage(Page<T> page, int resultType) {
        this.page = page;
        setResultType(resultType);
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    /**
     * @return 结果
     * @author jiangzeyin
     */
    @SuppressWarnings({"hiding", "unchecked"})
    @Override
    public <T> T run() {
        // TODO Auto-generated method stub
        Assert.notNull(page, "page");
        try {
            String tag = getTag();
            if (StringUtils.isEmpty(tag)) {
                tag = EntityInfo.getDatabaseName(getTclass());
                setTag(tag);
            }
            String[] pageSql;
            if (StringUtils.isEmpty(page.getSql()))
                pageSql = SqlUtil.getSelectPageSql(this);
            else
                pageSql = SqlUtil.getSelectPageSql(page);
            DataSource dataSource = DatabaseContextHolder.getReadDataSource(tag);
            List<Map<String, Object>> list;
            { // 查询数据总数
                list = JdbcUtils.executeQuery(dataSource, pageSql[0], getParameters());
                if (list == null || list.size() < 1)
                    return null;
                Map<String, Object> count_map = list.get(0);
                if (count_map == null)
                    return null;
                long count = (Long) count_map.values().toArray()[0];
                page.setTotalRecord(count);
            }
            { // 查询数据
                setRunSql(pageSql[1]);
                SystemDbLog.getInstance().info(pageSql[1]);
                list = JdbcUtils.executeQuery(dataSource, pageSql[1], getParameters());
                page.setMapList(list);
//                if (list == null || list.size() < 1)  此处为更改注释
//                    return null;
                if (getResultType() == Result.JsonArray) {
                    return (T) JSON.parseArray(JSON.toJSONString(list));// new JSONArray(list);
                }
                // 新增部分----------------
                if (getResultType() == Result.JsonArray) {
                    if (list == null)
                        return null;
                    return (T) JSONArray.toJSON(list);// JSON.parseArray(JSON.toJSONString(list));// new JSONArray(list);
                }
                if (getResultType() == PageResultType) {
                    JSONObject data = new JSONObject();
                    data.put("results", list);
                    data.put("pageNo", page.getPageNo());
                    data.put("pageSize", page.getPageSize());
                    data.put("totalPage", page.getTotalPage());
                    return (T) data;
                }
                // 新增部分结束
                List<?> result_list = Util.convertList(this, list);
                page.setResultsT(result_list);
                return (T) result_list;
            }
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
