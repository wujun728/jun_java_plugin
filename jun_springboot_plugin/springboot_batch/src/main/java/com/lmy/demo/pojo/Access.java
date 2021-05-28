package com.lmy.demo.pojo;

import javax.persistence.*;

@Entity
@Table
public class Access {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String username;
	private String shopName;
	private String categoryName;
	private String brandName;
	private String shopId;
	private String omit;
	private String updateTime;
	private boolean deleteStatus;
	private String createTime;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOmit() {
		return omit;
	}

	public void setOmit(String omit) {
		this.omit = omit;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Access{" + "id=" + id + ", username='" + username + '\''
				+ ", shopName='" + shopName + '\'' + ", categoryName='"
				+ categoryName + '\'' + ", brandName='" + brandName + '\''
				+ ", shopId='" + shopId + '\'' + ", omit='" + omit + '\''
				+ ", updateTime='" + updateTime + '\'' + ", deleteStatus="
				+ deleteStatus + ", createTime='" + createTime + '\''
				+ ", description='" + description + '\'' + '}';
	}
}