package com.chentongwei.entity.doutu.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * admin -- 图片列表
 *
 * @author TongWei.Chen 2017-06-15 19:31:32
 */
public class PictureListAdminVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 图片id */
    private Integer pictureId;
    /** 图片url */
    private String pictureUrl;
    /** 图片分类名称 */
    private String catalogName;
    /** 上传人 */
    private String loginName;
    /** 图片上传时间 */
    private Date createTime;
    /** 状态 1：正常 0：已删除 */
    private boolean isDelete;

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
