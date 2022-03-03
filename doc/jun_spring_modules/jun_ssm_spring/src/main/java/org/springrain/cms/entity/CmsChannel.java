package org.springrain.cms.entity;

import java.util.List;
import java.util.Map;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-18 11:53:33
 * @see org.springrain.cms.entity.demo.entity.CmsChannel
 */
@Table(name="cms_channel")
public class CmsChannel  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "栏目表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_COMCODE = "comcode";
	public static final String ALIAS_SITEID = "网站ID";
	public static final String ALIAS_POSITIONLEVEL = "0导航,1-10个级别";
	public static final String ALIAS_TITLE = "标题";
	public static final String ALIAS_KEYWORDS = "关键字";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_LOOKCOUNT = "打开次数";
	public static final String ALIAS_SORTNO = "排序";
	public static final String ALIAS_ACTIVE = "状态 0不可用,1可用";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * pid
	 */
	private java.lang.String pid;
	/**
	 * comcode
	 */
	private java.lang.String comcode;
	/**
	 * 网站ID
	 */
	private java.lang.String siteId;
	/**
	 * 0导航,1-10个级别
	 */
	private java.lang.Integer positionLevel;
	/**
	 * 关键字
	 */
	private java.lang.String keywords;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * 排序
	 */
	private java.lang.Integer sortno;
	/**
	 * 状态 0不可用,1可用
	 */
	private java.lang.Integer active;
	
	/**
	 * 是否有内容，是否有内容，0 有内容（微信端显示），1 没有内容（微信端不显示）
	 */
	private java.lang.Integer hasContent;
	//columns END 数据库字段结束
	
	private List<CmsChannel> leaf;
	
	/**
	 * 所属站点
	 */
	private CmsSite cmsSite;
	
	/**
	 * 父级栏目
	 */
	private CmsChannel cmsChannel;
	
	private String link;
	private Integer jumpType;
	/**
	 * list模板路径
	 */
	private String ftlListPath;
	
	/**
	 * look模板路径
	 */
	private String ftlLookPath;
	
	/**
	 * 更新模板路径
	 */
	private String ftlUpdatePath;
	
	private String nodeftlPath;
	private String frontFtl;
	/**
	 * 登陆访问标识
	 */
	private Integer loginuser;
	
	/**
	 * 封面
	 */
	private String cover;
	//concstructor
	private String banner;
	
	private List<CmsProperty> propertyList;
	
	private Map<String,CmsProperty> propertyMap;
	
	
	
	
	

	public CmsChannel(){
	}

	public CmsChannel(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:CmsChannel_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name like :%CmsChannel_name% ")
	public java.lang.String getName() {
		return this.name;
	}
	public void setPid(java.lang.String value) {
	    if(StringUtils.isNotBlank(value)){
	    	value=value.trim();
		}else{
			value=null;
		}
		this.pid = value;
	}
	
     @WhereSQL(sql="pid=:CmsChannel_pid")
	public java.lang.String getPid() {
		return this.pid;
	}
	public void setComcode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.comcode = value;
	}
	
     @WhereSQL(sql="comcode=:CmsChannel_comcode")
	public java.lang.String getComcode() {
		return this.comcode;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsChannel_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setPositionLevel(java.lang.Integer value) {
		this.positionLevel = value;
	}
	
     @WhereSQL(sql="positionLevel=:CmsChannel_positionLevel")
	public java.lang.Integer getPositionLevel() {
		return this.positionLevel;
	}
	public void setKeywords(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.keywords = value;
	}
	
     @WhereSQL(sql="keywords=:CmsChannel_keywords")
	public java.lang.String getKeywords() {
		return this.keywords;
	}
	public void setDescription(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.description = value;
	}
	
     @WhereSQL(sql="description=:CmsChannel_description")
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}
	
     @WhereSQL(sql="lookcount=:CmsChannel_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
     @WhereSQL(sql="sortno=:CmsChannel_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsChannel_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
    
     @WhereSQL(sql="cover=:CmsChannel_cover")
     public String getCover() {
 		return cover;
 	}

 	public void setCover(String cover) {
 		this.cover = cover;
 	}
 	
    @WhereSQL(sql="hasContent=:CmsChannel_hasContent")
 	public java.lang.Integer getHasContent() {
		return hasContent;
	}

	public void setHasContent(java.lang.Integer hasContent) {
		this.hasContent = hasContent;
	}
 	
    @Transient
	public CmsSite getCmsSite() {
		return cmsSite;
	}

	public void setCmsSite(CmsSite cmsSite) {
		this.cmsSite = cmsSite;
	}
	
	@Transient
	public CmsChannel getCmsChannel() {
		return cmsChannel;
	}

	public void setCmsChannel(CmsChannel cmsChannel) {
		this.cmsChannel = cmsChannel;
	}
	@Transient
	public Integer getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(Integer loginuser) {
		this.loginuser = loginuser;
	}
	
	
	
	@Transient
	public String getFtlListPath() {
		return ftlListPath;
	}

	public void setFtlListPath(String ftlListPath) {
		this.ftlListPath = ftlListPath;
	}
	@Transient
	public String getFtlLookPath() {
		return ftlLookPath;
	}

	public void setFtlLookPath(String ftlLookPath) {
		this.ftlLookPath = ftlLookPath;
	}
	@Transient
	public String getFtlUpdatePath() {
		return ftlUpdatePath;
	}

	public void setFtlUpdatePath(String ftlUpdatePath) {
		this.ftlUpdatePath = ftlUpdatePath;
	}

	@Transient
	public String getNodeftlPath() {
		return nodeftlPath;
	}

	public void setNodeftlPath(String nodeftlPath) {
		this.nodeftlPath = nodeftlPath;
	}

	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("pid[").append(getPid()).append("],")
			.append("comcode[").append(getComcode()).append("],")
			.append("网站ID[").append(getSiteId()).append("],")
			.append("0导航,1-10个级别[").append(getPositionLevel()).append("],")
			.append("关键字[").append(getKeywords()).append("],")
			.append("描述[").append(getDescription()).append("],")
			.append("打开次数[").append(getLookcount()).append("],")
			.append("排序[").append(getSortno()).append("],")
			.append("状态 0不可用,1可用[").append(getActive()).append("],")
			.toString();
	}
	
	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj instanceof CmsChannel == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsChannel other = (CmsChannel)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	
	@Transient
	public List<CmsChannel> getLeaf() {
		return leaf;
	}

	public void setLeaf(List<CmsChannel> leaf) {
		this.leaf = leaf;
	}
	
	@Transient
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Transient
	public List<CmsProperty> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<CmsProperty> propertyList) {
		this.propertyList = propertyList;
	}
	
	@Transient
	public Map<String, CmsProperty> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, CmsProperty> propertyMap) {
		this.propertyMap = propertyMap;
	}
	@Transient
	public Integer getJumpType() {
		return jumpType;
	}

	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}
	@Transient
	public String getFrontFtl() {
		return frontFtl;
	}

	public void setFrontFtl(String frontFtl) {
		this.frontFtl = frontFtl;
	}
	@WhereSQL(sql="banner=:CmsChannel_banner")
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	
	
}

	
