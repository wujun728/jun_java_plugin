package com.chentongwei.entity.doutu.io;

import com.chentongwei.common.annotation.StatusVerify;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 审核举报接口
 * 
 * @author TongWei.Chen 2017-5-22 20:46:20
 */
public class ReportStatusIO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** id */
	@NotNull
	private Long id;
	/** 审核状态 2：已通过;3:已拒绝 */
	@StatusVerify
	private Integer status;
	/** 反馈说明 */
	@NotEmpty
	@NotNull
	@NotBlank
	private String feedback;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
}
