package com.sky.bluesky.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：
 * CLASSPATH: com.sky.bluesky.model.BaseModel
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public class BaseModel implements Serializable {
    //创建人
    private Long creUserId;
    //创建时间
    private Date creTime;
    //是否有效
    private Integer isEffect;
    //备注
    private String remark;

    public Long getCreUserId() {
        return creUserId;
    }

    public void setCreUserId(Long creUserId) {
        this.creUserId = creUserId;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect) {
        this.isEffect = isEffect;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
