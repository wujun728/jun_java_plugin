package cn.kiwipeach.response;

import java.util.List;

/**
 * Create Date: 2017/11/06
 * Description: 分页信息返回实体类
 *
 * @author Wujun
 */
public class PageResponse<T> {
    /***
     * 当前页
     */
    private Integer curNo;
    /**
     * 页码大小
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalNum;
    /**
     * 页面数据
     */
    private List<T> pageData;

    public Integer getCurNo() {
        return curNo;
    }

    public void setCurNo(Integer curNo) {
        this.curNo = curNo;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
