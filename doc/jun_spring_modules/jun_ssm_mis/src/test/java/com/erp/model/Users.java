package com.erp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Users entity. @author Wujun
 */
@Entity
@Table(name = "SYS_USERS")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Users implements java.io.Serializable
{
	private static final long serialVersionUID = 3091722681204768199L;
	private Integer userId;
	private String myid;
	private String account;
	private String name;
	private Integer organizeId;
	private String organizeName;
	private Integer dutyId;
	private Integer titleId;
	private String password;
	private String email;
	private String lang;
	private String theme;
	private Date firstVisit;
	private Date previousVisit;
	private Date lastVisits;
	private Integer loginCount;
	private Integer isemployee;
	private String status;
	private String ip;
	private String description;
	private Integer questionId;
	private String answer;
	private Integer isonline;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;
	private String tel;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	// Constructors

	/** default constructor */
	public Users()
	{
	}

	/** full constructor */
	public Users(String myid, String account, String name, Integer organizeId, String organizeName,
			Integer dutyId, Integer titleId, String password, String email, String lang,
			String theme, Date firstVisit, Date previousVisit, Date lastVisits, Integer loginCount,
			Integer isemployee, String status, String ip, String description, Integer questionId,
			String answer, Integer isonline, Date created, Date lastmod, Integer creater,
			Integer modifyer, String tel, Set<UserRole> userRoles)
	{
		this.myid = myid;
		this.account = account;
		this.name = name;
		this.organizeId = organizeId;
		this.organizeName = organizeName;
		this.dutyId = dutyId;
		this.titleId = titleId;
		this.password = password;
		this.email = email;
		this.lang = lang;
		this.theme = theme;
		this.firstVisit = firstVisit;
		this.previousVisit = previousVisit;
		this.lastVisits = lastVisits;
		this.loginCount = loginCount;
		this.isemployee = isemployee;
		this.status = status;
		this.ip = ip;
		this.description = description;
		this.questionId = questionId;
		this.answer = answer;
		this.isonline = isonline;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
		this.tel = tel;
		this.userRoles = userRoles;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "USER_ID", unique = true, nullable = false)
	public Integer getUserId()
	{
		return this.userId;
	}

	public void setUserId(Integer userId )
	{
		this.userId = userId;
	}

	@Column(name = "MYID", length = 50)
	public String getMyid()
	{
		return this.myid;
	}

	public void setMyid(String myid )
	{
		this.myid = myid;
	}

	@Column(name = "ACCOUNT", length = 50)
	public String getAccount()
	{
		return this.account;
	}

	public void setAccount(String account )
	{
		this.account = account;
	}

	@Column(name = "NAME", length = 50)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Column(name = "ORGANIZE_ID")
	public Integer getOrganizeId()
	{
		return this.organizeId;
	}

	public void setOrganizeId(Integer organizeId )
	{
		this.organizeId = organizeId;
	}

	@Column(name = "ORGANIZE_NAME")
	public String getOrganizeName()
	{
		return this.organizeName;
	}

	public void setOrganizeName(String organizeName )
	{
		this.organizeName = organizeName;
	}

	@Column(name = "DUTY_ID")
	public Integer getDutyId()
	{
		return this.dutyId;
	}

	public void setDutyId(Integer dutyId )
	{
		this.dutyId = dutyId;
	}

	@Column(name = "TITLE_ID")
	public Integer getTitleId()
	{
		return this.titleId;
	}

	public void setTitleId(Integer titleId )
	{
		this.titleId = titleId;
	}

	@Column(name = "PASSWORD", length = 128)
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password )
	{
		this.password = password;
	}

	@Column(name = "EMAIL", length = 200)
	public String getEmail()
	{
		return this.email;
	}

	public void setEmail(String email )
	{
		this.email = email;
	}

	@Column(name = "LANG", length = 20)
	public String getLang()
	{
		return this.lang;
	}

	public void setLang(String lang )
	{
		this.lang = lang;
	}

	@Column(name = "THEME", length = 20)
	public String getTheme()
	{
		return this.theme;
	}

	public void setTheme(String theme )
	{
		this.theme = theme;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_VISIT", length = 10)
	public Date getFirstVisit()
	{
		return this.firstVisit;
	}

	public void setFirstVisit(Date firstVisit )
	{
		this.firstVisit = firstVisit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PREVIOUS_VISIT", length = 10)
	public Date getPreviousVisit()
	{
		return this.previousVisit;
	}

	public void setPreviousVisit(Date previousVisit )
	{
		this.previousVisit = previousVisit;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_VISITS", length = 10)
	public Date getLastVisits()
	{
		return this.lastVisits;
	}

	public void setLastVisits(Date lastVisits )
	{
		this.lastVisits = lastVisits;
	}

	@Column(name = "LOGIN_COUNT")
	public Integer getLoginCount()
	{
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount )
	{
		this.loginCount = loginCount;
	}

	@Column(name = "ISEMPLOYEE")
	public Integer getIsemployee()
	{
		return this.isemployee;
	}

	public void setIsemployee(Integer isemployee )
	{
		this.isemployee = isemployee;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status )
	{
		this.status = status;
	}

	@Column(name = "IP", length = 20)
	public String getIp()
	{
		return this.ip;
	}

	public void setIp(String ip )
	{
		this.ip = ip;
	}

	@Column(name = "DESCRIPTION", length = 2000)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description )
	{
		this.description = description;
	}

	@Column(name = "QUESTION_ID")
	public Integer getQuestionId()
	{
		return this.questionId;
	}

	public void setQuestionId(Integer questionId )
	{
		this.questionId = questionId;
	}

	@Column(name = "ANSWER", length = 100)
	public String getAnswer()
	{
		return this.answer;
	}

	public void setAnswer(String answer )
	{
		this.answer = answer;
	}

	@Column(name = "ISONLINE")
	public Integer getIsonline()
	{
		return this.isonline;
	}

	public void setIsonline(Integer isonline )
	{
		this.isonline = isonline;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", length = 10)
	public Date getCreated()
	{
		return this.created;
	}

	public void setCreated(Date created )
	{
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTMOD", length = 10)
	public Date getLastmod()
	{
		return this.lastmod;
	}

	public void setLastmod(Date lastmod )
	{
		this.lastmod = lastmod;
	}

	@Column(name = "CREATER")
	public Integer getCreater()
	{
		return this.creater;
	}

	public void setCreater(Integer creater )
	{
		this.creater = creater;
	}

	@Column(name = "MODIFYER")
	public Integer getModifyer()
	{
		return this.modifyer;
	}

	public void setModifyer(Integer modifyer )
	{
		this.modifyer = modifyer;
	}

	@Column(name = "TEL", length = 30)
	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel )
	{
		this.tel = tel;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	public Set<UserRole> getUserRoles()
	{
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles )
	{
		this.userRoles = userRoles;
	}

}