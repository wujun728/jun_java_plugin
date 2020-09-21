package com.chentongwei.entity.doutu.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片举报PO
 * 
 * @author TongWei.Chen 2017-5-22 19:41:35
 */
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Long id;
	/** 图片id */
	private Long pictureId;
	/** 举报者id */
	private Long userId;
	/** 举报审核状态：1待审核; 2审核成功; 3审核失败 */
	private Integer status;
	/** 反馈说明 */
	private String feedback;
	/** 举报原因 */
	private String remark;
	/** 举报时间 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPictureId() {
		return pictureId;
	}
	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
