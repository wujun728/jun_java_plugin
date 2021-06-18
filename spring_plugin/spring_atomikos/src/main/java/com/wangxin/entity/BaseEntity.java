package com.wangxin.entity;

import java.io.Serializable;
import java.util.Date;

import com.wangxin.common.constants.Constants;

/** 
 * @Description 实体类基类
 * @author Wujun
 * @date Apr 25, 2017 10:39:24 AM  
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -6633426599752108257L;

    /** 主键 */
    private String id;
    
    /** 删除标识：0、未删除 1、已删除 */
    private int isDel;
    
    /** 版本号 */
    private Long versionNum;

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

    public int getIsDel() {
        return isDel;
    }
    
    public String getIsDelString() {
        if (Constants.IS_DEL_MAP.containsKey(isDel))
            return Constants.IS_DEL_MAP.get(isDel);
        return "";
    }
    
    public String getDelString() {
        if (Constants.DEL_MAP.containsKey(isDel))
            return Constants.DEL_MAP.get(isDel);
        return "";
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Long getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Long versionNum) {
        this.versionNum = versionNum;
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
