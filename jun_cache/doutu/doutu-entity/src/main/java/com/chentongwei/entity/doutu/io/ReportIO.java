package com.chentongwei.entity.doutu.io;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 图片举报IO
 * 
 * @author TongWei.Chen 2017-5-22 19:50:55
 */
public class ReportIO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 举报id */
	private Long id;
	/**图片id*/
	@NotNull
	private Long pictureId;
	/** 举报者id */
	@NotNull
	private Long userId;
	/** 举报原因 */
	@NotEmpty
	@NotNull
	@NotBlank
	private String remark;

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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
