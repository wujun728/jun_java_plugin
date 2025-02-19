package io.github.wujun728.db.orm.result;

import java.util.List;

/**
 * 分页结果基类
 */
public class BaseResult<T> {

    private List<T> data;

    private int pageSize;

    public List<T> getData() {
        return data;
    }

    public BaseResult setData(List<T> data) {
        this.data = data;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public BaseResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
