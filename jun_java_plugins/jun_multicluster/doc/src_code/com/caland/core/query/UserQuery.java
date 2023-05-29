package com.caland.core.query;

import java.util.*;

/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午05:58:00]
 */
public class UserQuery extends BaseQuery {
	/**
	 * ==============================批量查询、更新、删除时的Where条件设置======================
	 * ============
	 **/
	private Integer id;
	public Integer getId() {
		return id;
	}
	public UserQuery setId(Integer id) {
		this.id = id;
		return this;
	}
	private String username;
	public String getUsername() {
		return username;
	}
	public UserQuery setUsername(String username) {
		this.username = username;
		return this;
	}
	private boolean usernameLike;
	public UserQuery setUsernameLike(boolean isLike) {
		this.usernameLike = isLike;
		return this;
	}
	private Integer age;
	public Integer getAge() {
		return age;
	}
	public UserQuery setAge(Integer age) {
		this.age = age;
		return this;
	}
	private Integer phone;
	public Integer getPhone() {
		return phone;
	}
	public UserQuery setPhone(Integer phone) {
		this.phone = phone;
		return this;
	}
	private String email;
	public String getEmail() {
		return email;
	}
	public UserQuery setEmail(String email) {
		this.email = email;
		return this;
	}
	private boolean emailLike;
	public UserQuery setEmailLike(boolean isLike) {
		this.emailLike = isLike;
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
	public UserQuery orderbyId(boolean isAsc) {
		orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：username
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public UserQuery orderbyUsername(boolean isAsc) {
		orderFields.add(new OrderField("username", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：age
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public UserQuery orderbyAge(boolean isAsc) {
		orderFields.add(new OrderField("age", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：phone
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public UserQuery orderbyPhone(boolean isAsc) {
		orderFields.add(new OrderField("phone", isAsc ? "ASC" : "DESC"));
		return this;
	}
	/**
	 * 设置排序按属性：email
	 * 
	 * @param isAsc
	 *            是否升序，否则为降序
	 */
	public UserQuery orderbyEmail(boolean isAsc) {
		orderFields.add(new OrderField("email", isAsc ? "ASC" : "DESC"));
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
			fieldMap.put("username", "username");
			fieldMap.put("age", "age");
			fieldMap.put("phone", "phone");
			fieldMap.put("email", "email");
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
