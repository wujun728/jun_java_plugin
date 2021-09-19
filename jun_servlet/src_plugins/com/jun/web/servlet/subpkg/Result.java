package com.jun.web.servlet.subpkg;

/**
 * ��װ�����ͼ
 * <result name="success" type="redirect">/index.jsp</result>
 * @author Jie.Yuan
 *
 */
public class Result {

	// ��ת�Ľ�����
	private String name;
	// ��ת���ͣ�Ĭ��Ϊת���� "redirect"Ϊ�ض���
	private String type;
	// ��ת��ҳ��
	private String page;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	
}
