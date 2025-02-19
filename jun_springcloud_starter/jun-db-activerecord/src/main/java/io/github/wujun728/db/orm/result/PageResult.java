package io.github.wujun728.db.orm.result;

/**
 * 页数分页结果
 */
public class PageResult extends BaseResult{
    private int page; //当前页数

    private int totalPage; //总页数

    private int totalCount; //总记录数

    private boolean hasMore;

    public int getPage() {
        return page;
    }

    public PageResult setPage(int page) {
        this.page = page;
        return this;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public PageResult setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public PageResult setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
