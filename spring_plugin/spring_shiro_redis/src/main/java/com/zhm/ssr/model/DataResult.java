package com.zhm.ssr.model;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

/**
 * Created by zhm on 2015/7/11.
 */
public class DataResult<T> {
    private long total;
    private List<T> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
