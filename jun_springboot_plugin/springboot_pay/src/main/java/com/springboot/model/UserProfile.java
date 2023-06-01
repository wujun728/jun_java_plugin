package com.springboot.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler", "JavassistLazyInitializer" })
@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "id")
	private Integer id;
	@Column(name = "userId")
	private String userId;
	@Column(name = "gender")
	private Integer gender;
	@Column(name = "smallImg")
	private String smallImg;
	@Column(name = "bigImg")
	private String bigImg;
	@Column(name = "nickName")
	private String nickName;
	@Column(name = "appSystem")
	private String appSystem;
	@Column(name = "appVersion")
	private String appVersion;
	@Column(name = "appChannel")
	private String appChannel;
	@Column(name = "openPush")
	private Integer openPush;
	@Column(name = "stint")
	private Integer stint;
	@Column(name = "created")
	private Date created;
	@Column(name = "updated")
	private Date updated;
	@Column(name = "qq")
	private String qq;
	@Column(name = "email")
	private String email;
	@Column(name = "intro")
	private String intro;
	@Column(name = "isAuth")
	private Integer isAuth;

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getQq() {
		return qq;
	}

	public String getEmail() {
		return email;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getSmallImg() {
		return smallImg;
	}

	public void setSmallImg(String smallImg) {
		this.smallImg = smallImg;
	}

	public String getBigImg() {
		return bigImg;
	}

	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAppSystem() {
		return appSystem;
	}

	public void setAppSystem(String appSystem) {
		this.appSystem = appSystem;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

	public Integer getOpenPush() {
		return openPush;
	}

	public void setOpenPush(Integer openPush) {
		this.openPush = openPush;
	}

	public Integer getStint() {
		return stint;
	}

	public void setStint(Integer stint) {
		this.stint = stint;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

}
