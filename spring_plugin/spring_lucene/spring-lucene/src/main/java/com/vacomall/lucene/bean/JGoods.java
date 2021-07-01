package com.vacomall.lucene.bean;

import java.util.Date;
import java.util.List;

/**
 * 上架商品
 * @author gaojun.zhou
 * @date 2016年9月1日
 */

public class JGoods{

	/**
	 * 商品ID
	 */
	private String id;
	/**
	 * 商品标题
	 */
	private String title;
	/**
	 * 商品标题2
	 */
	private String title2;
	/**
	 * 热点
	 */
	private String hots;
	/**
	 * 价格
	 */
	private Float price;
	/**
	 * 商品图片
	 */
	private String img;
	
	/**
	 * 销量
	 */
	private long sales;
	/**
	 * 上架日期
	 */
	private Date date;
	
	/**
	 * Skus
	 */
	private List<Sku> skus;

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

	public String getHots() {
		return hots;
	}

	public void setHots(String hots) {
		this.hots = hots;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}

	public long getSales() {
		return sales;
	}

	public void setSales(long sales) {
		this.sales = sales;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getTitle2() {
		return title2;
	}

	public void setTitle2(String title2) {
		this.title2 = title2;
	}

	
	
	public JGoods() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JGoods(String id, String title, String title2, String hots,
			Float price, String img, long sales, Date date, List<Sku> skus) {
		super();
		this.id = id;
		this.title = title;
		this.title2 = title2;
		this.hots = hots;
		this.price = price;
		this.img = img;
		this.sales = sales;
		this.date = date;
		this.skus = skus;
	}

	public JGoods(String id) {
		super();
		this.id = id;
	}

	
	
}
