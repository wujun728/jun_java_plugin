package com.ssm.dubbo.util;

/**
 * @author 13
 * @project_name ssm-dubbo
 * @date 2019-01-02
 */
public class PageBean {

    private int page; // 页码
    private int pageSize; // 单页数据量
    private int start;


    public PageBean(int page, int pageSize) {
        super();
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page - 1) * pageSize;
    }


}
