package com.vacomall.lucene.bean;

import java.io.Serializable;

/**
 * Sku
 * @author gaojun.zhou
 * @date 2016年9月1日
 */
public class Sku implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sku Id
	 */
	private String id;
	/**
	 * sku标题
	 */
	private String title;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 规格
	 */
	private String spec;
	
	/**
	 * 商品图片
	 */
	private String img;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Sku() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
}
