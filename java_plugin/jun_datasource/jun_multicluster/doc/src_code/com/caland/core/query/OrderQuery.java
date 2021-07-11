package com.caland.core.query;

import java.util.*;

/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午05:58:00]
 */
public class OrderQuery extends BaseQuery {
	/**
	 * ==============================批量查询、更新、删除时的Where条件设置======================
	 * ============
	 **/
	private Integer id;
	public Integer getId() {
		return id;
	}
	public OrderQuery setId(Integer id) {
		this.id = id;
		return this;
	}
	private String no;
	public String getNo() {
		return no;
	}
	public OrderQuery setNo(String no) {
		this.no = no;
		return this;
	}
	private boolean noLike;
	public OrderQuery setNoLike(boolean isLike) {
		this.noLike = isLike;
		return this;
	}
	private String name;
	public String getName() {
		return name;
	}
	public OrderQuery setName(String name) {
		this.name = name;
		return this;
	}
	private boolean nameLike;
	public OrderQuery setNameLike(boolean isLike) {
		this.nameLike = isLike;
		return this;
	}
	private Double price;
	public Double getPrice() {
		return price;
	}
	public OrderQuery setPrice(Double price) {
		this.price = price;
		return this;
	}
	private Integer userId;
	public Integer getUserId() {
		return userId;
	}
	public OrderQuery setUserId(Integer userId) {
		this.userId = userId;
		return this;
	}
	/**
	 * ==============================批量查询时的Order条件顺序设置==========================
	 * ========
	 **/
	 	public class OrderField {
		public OrderField(String fieldName, String order) {
			super();
			this.fieldName = fieldName;
			this.order = order;
		}
		private String fieldName;
		private String order;

		public String getFieldName() {
			return fieldName;
		}
		public OrderField setFieldName(String fieldName) {
			this.fieldName = fieldName;
			return this;
		}
		public String getOrder() {
			return order;
		}
		public OrderField setOrder(String order) {
			this.order = order;
			return this;
		}
	}
		/**
	 * ==============================批量查询时的Order条件顺序设置==========================
	 * ========
	 **/
	/** 排序列表字段 **/
	private List<OrderField> orderFields = new ArrayList<OrderField>();
	/**
	 * 设置排序按属性：id
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public OrderQuery orderbyId(boolean isAsc) {
		orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：no
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public OrderQuery orderbyNo(boolean isAsc) {
		orderFields.add(new OrderField("no", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：name
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public OrderQuery orderbyName(boolean isAsc) {
		orderFields.add(new OrderField("name", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：price
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public OrderQuery orderbyPrice(boolean isAsc) {
		orderFields.add(new OrderField("price", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：user_id
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public OrderQuery orderbyUserId(boolean isAsc) {
		orderFields.add(new OrderField("user_id", isAsc ? "ASC" : "DESC"));
		return this;
	}
	private String fields;
	/**
	 * 提供自定义字段使用
	 */
	private static Map<String, String> fieldMap;

	private static Map<String, String> getFieldSet() {
		if (fieldMap == null) {
			fieldMap = new HashMap<String, String>();
			fieldMap.put("id", "id");
			fieldMap.put("no", "no");
			fieldMap.put("name", "name");
			fieldMap.put("price", "price");
			fieldMap.put("userId", "user_id");
		}
		return fieldMap;
	}

	public String getFields() {
		return this.fields;
	}
	public void setFields(String fields) {
		if (fields == null)
			return;
		String[] array = fields.split(",");
		StringBuffer buffer = new StringBuffer();
		for (String field : array) {
			if (getFieldSet().containsKey(field)) {
				buffer.append(getFieldSet().get(field)).append(" as ")
						.append(field).append(" ,");
			}
			if (getFieldSet().containsKey("`" + field + "`")) {
				buffer.append("`" + getFieldSet().get(field) + "`").append(" as ")
						.append(field).append(" ,");
			}
		}
		if (buffer.length() != 0) {
			this.fields = buffer.substring(0, buffer.length() - 1);
		} else {
			this.fields = " 1 ";// 没有一个参数可能会报错
		}
	}
}
