package cn.springmvc.mybatis.common.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 基类对象
 */
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 329430609755626516L;

    /** 主键 */
    private String id;

    /** 删除标识：0=未删除 ;1=已删除 */
    private Integer isDel;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
