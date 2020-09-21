package cn.jiangzeyin.database;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 对分页的基本数据进行一个简单的封装
 *
 * @param <T>
 * @author jiangzeyin
 */
public class Page<T> {

    private long pageNo;// 页码，默认是第一页
    private long pageSize;// 每页显示的记录数，默认是15
    private long totalRecord;// 总记录数
    private long totalPage;// 总页数
    private List<T> results;// 对应的当前页记录
    /**
     * sql 后面wher条件 需要自己设定
     */
    private String whereWord;
    private String orderBy;// 排序字段
    private String sql;
    private List<Map<String, Object>> mapList;

    public List<Map<String, Object>> getMapList() {
        return mapList;
    }

    public void setMapList(List<Map<String, Object>> mapList) {
        this.mapList = mapList;
    }

    /**
     *
     */
    public Page() {
        // TODO Auto-generated constructor stub
    }

    public Page(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getWhereWord() {
        return whereWord;
    }

    public void setWhereWord(String whereWord) {
        if (StringUtils.isEmpty(this.whereWord)) {
            this.whereWord = whereWord;
        } else {
            this.whereWord += " and " + whereWord;
        }
    }

    /**
     * 设置分页条件
     *
     * @param pageNo   当前页码
     * @param pageSize 每页记录
     */
    public void setPageNoAndSize(long pageNo, long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
        // 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
        long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    // public void setTotalRecord(long totalRecord) {
    // this.totalRecord = totalRecord;
    // // 在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
    // long totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize :
    // totalRecord / pageSize + 1;
    // this.setTotalPage(totalPage);
    // }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> list) {
        this.results = list;
    }

    @SuppressWarnings("unchecked")
    public void setResultsT(List<?> list) {
        this.results = (List<T>) list;
    }

    public void setDisplayPage(int start, int lenght) {
        int pageNo = 1;
        if (start >= lenght) {
            pageNo += start / lenght;
        }
        this.setPageNo(pageNo);
        this.setPageSize(lenght);
    }

    @Override
    public String toString() {
        return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize + ", results=" + results + ", totalPage=" + totalPage + ", totalRecord=" + totalRecord + "]";
    }

    public JSONObject toJSONObject(JSONArray jsonArray) {
        JSONObject data = new JSONObject();
        data.put("pageNo", getPageNo());
        data.put("pageSize", getPageSize());
        data.put("totalPage", getTotalPage());
        data.put("results", jsonArray);
        return data;
    }

    public JSONObject toJSONObject() {
        JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(getMapList()));
        return toJSONObject(jsonArray);
    }
}