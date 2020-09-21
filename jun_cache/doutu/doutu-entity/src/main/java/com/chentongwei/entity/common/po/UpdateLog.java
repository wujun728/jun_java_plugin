package com.chentongwei.entity.common.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目更新日志
 *
 * @author TongWei.Chen 2017-07-11 14:07:52
 */
public class UpdateLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Integer id;
    /** 更新内容 */
    private String content;
    /** 更新时间 */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
