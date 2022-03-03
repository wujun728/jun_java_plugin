package com.jun.plugin.blog.entity;

import java.io.Serializable;

/**
 * ��������ʵ��
 * @author Wujun
 *
 */
public class BlogType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;  // ���
	private String typeName; // ������������
	private Integer blogCount; // ����
	private Integer orderNo; // ����  ��С����������ʾ
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	

	
	
}
