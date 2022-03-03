package com.jun.plugin.blog.entity;

/**
 * ��������ʵ��
 * @author Wujun
 *
 */
public class Link {

	private Integer id; // ���
	private String linkName; // ��������
	private String linkUrl; // ���ӵ�ַ
	private Integer orderNo; // ������� ��С��������
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
