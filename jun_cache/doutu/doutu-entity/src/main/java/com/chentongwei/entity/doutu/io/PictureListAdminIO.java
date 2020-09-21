package com.chentongwei.entity.doutu.io;

import com.chentongwei.common.entity.Page;

/**
 * admin -- 图片列表条件查询
 *
 * @author TongWei.Chen 2017-06-16 09:21:35
 */
public class PictureListAdminIO extends Page {
    private static final long serialVersionUID = 1L;

    /** 开始日期 */
    private String beginDate;
    /** 结束日期 */
    private String endDate;
    /** 分类id */
    private Integer catalogId;
    /** 上传人 */
    private String creator;
    /** 是否显示作废 -1：全部 0：已作废 1：正常 */
    private Integer deleteStatus;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
