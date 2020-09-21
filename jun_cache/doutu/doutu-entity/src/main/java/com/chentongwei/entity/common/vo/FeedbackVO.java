package com.chentongwei.entity.common.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 反馈列表
 *
 * @author TongWei.Chen 2017-06-21 16:51:21
 */
public class FeedbackVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 标题 */
    private String title;
    /** 反馈内容 */
    private String content;
    /** 创建人 */
    private String creator;
    /** ip */
    private String ip;
    /** 处理状态 1:待处理 2::已采纳 3:已放弃 */
    private Integer status;
    /** 创建时间 */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
