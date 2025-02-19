package io.github.wujun728.db.orm.result;

import java.io.Serializable;
import java.util.List;

/**
 * 瀑布流分页结果
 */
public class Scroll<T> implements Serializable{

    private static final long serialVersionUID = -8063828692900632543L;

    private boolean hasMore; //是否还有下一页

    private Object lastValue; //返回列表中最后一个参考值

    private List<T> data; //返回数据

    private int pageSize; //每页数量

    public boolean isHasMore() {
        return hasMore;
    }

    public Scroll setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
        return this;
    }

    public Object getLastValue() {
        return lastValue;
    }

    public Scroll setLastValue(Object lastValue) {
        this.lastValue = lastValue;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
