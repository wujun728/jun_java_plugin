package net.chenlin.dp.common.entity;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

/**
 * Mybatis分页参数及查询结果封装. 注意所有序号从1开始
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月8日 上午11:41:07
 */
public class Page<T> extends RowBounds {
    
	/**
     * 页编号 : 第几页
     */
    protected int pageNo = 1;
    /**
     * 页大小 : 每页的数量
     */
    protected int pageSize = 10;

    /**
     * 偏移量 : 第一条数据在表中的位置
     */
    protected int offset;

    /**
     * 限定数 : 每页的数量
     */
    protected int limit;

    // --结果 --//
    /**
     * 查询结果
     */
    protected List<T> rows = new ArrayList<T>();

    /**
     * 总条数
     */
    protected int total;

    /**
     * 总页数
     */
    protected int totalPages;

    /**
     * 计算偏移量
     */
    private void calcOffset() {
        this.offset = ((pageNo - 1) * pageSize);
    }

    /**
     * 计算限定数
     */
    private void calcLimit() {
        this.limit = pageSize;
    }

    public Page() {
        this.calcOffset();
        this.calcLimit();
    }

    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.calcOffset();
        this.calcLimit();
    }
    
    public Page(Query search) {
        this.pageNo = search.getAsInt("pageNumber");
        this.pageSize = search.getAsInt("pageSize");
        this.calcOffset();
        this.calcLimit();
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
     */
    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * 取得页内的记录列表.
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setRows(final List<T> rows) {
        this.rows = rows;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总记录数.
     */
    public void setTotal(final int total) {
        this.total = total;
        this.totalPages = this.getTotalPages();
    }

    /**
     * 根据pageSize与total计算总页数, 默认值为-1.
     */
    public int getTotalPages() {
        if (total < 0) {
            return -1;
        }
        int pages = total / pageSize;
        return total % pageSize > 0 ? ++pages : pages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
