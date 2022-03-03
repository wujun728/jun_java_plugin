package org.springrain.weixin.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;

@Table(name="wx_mpconfig")
public class WxMpConfig   extends BaseEntity implements IWxMpConfig {
	private static final long serialVersionUID = 1L;
	 

	//alias
	/*
	public static final String TABLE_ALIAS = "微信号需要的配置信息";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "站点Id";
	public static final String ALIAS_APPID = "开发者Id";
	public static final String ALIAS_SECRET = "应用密钥";
	public static final String ALIAS_TOKEN = "开发者Id";
	public static final String ALIAS_AESKEY = "消息加解密密钥";
	public static final String ALIAS_WXID = "原始ID";
	public static final String ALIAS_ACTIVE = "状态 0不可用,1可用";
	public static final String ALIAS_PARTNERID = "partnerId";
	public static final String ALIAS_PARTNERKEY = "partnerKey";
	public static final String ALIAS_OAUTH2REDIRECTURI = "微信重定向地址";
	public static final String ALIAS_HTTPPROXYHOST = "http代理地址";
	public static final String ALIAS_HTTPPROXYPORT = "http代理端口";
	public static final String ALIAS_HTTPPROXYUSERNAME = "http代理账号";
	public static final String ALIAS_HTTPPROXYPASSWORD = "http代理密码";
	public static final String ALIAS_CERTIFICATEFILE = "证书地址";
    */
	
	//columns START
		/**
		 * id
		 */
		private java.lang.String id;
		/**
		 * 站点Id
		 */
		private java.lang.String siteId;
		/**
		 * 开发者Id
		 */
		private java.lang.String appId;
		/**
		 * 应用密钥
		 */
		private java.lang.String secret;
		/**
		 * 开发者Id
		 */
		private java.lang.String token;
		/**
		 * 消息加解密密钥
		 */
		private java.lang.String aesKey;
		/**
		 * 原始ID
		 */
		private java.lang.String wxId;
		/**
		 * 状态 0不可用,1可用
		 */
		private java.lang.Integer active;
		/**
		 * partnerId
		 */
		private java.lang.String partnerId;
		/**
		 * partnerKey
		 */
		private java.lang.String partnerKey;
		/**
		 * 是否支持微信oauth2.0协议,0是不支持,1是支持
		 */
		private java.lang.Integer oauth2;
		/**
		 * http代理地址
		 */
		private java.lang.String httpProxyHost;
		/**
		 * http代理端口
		 */
		private java.lang.Integer httpProxyPort;
		/**
		 * http代理账号
		 */
		private java.lang.String httpProxyUsername;
		/**
		 * http代理密码
		 */
		private java.lang.String httpProxyPassword;
		/**
		 * 证书地址
		 */
		private java.lang.String certificateFile;
		//columns END 数据库字段结束
		
		
		
		
		
		
	  
	  private  String tmpDirFile;

	  private  String accessToken;
	  private  Long accessTokenExpiresTime=0L;
	  
	  private  String jsApiTicket;
	  private  Long jsApiTicketExpiresTime=0L;
	  
	  private  String cardApiTicket;
	  private  Long cardApiTicketExpiresTime=0L;
	  
	  
	  
	  
	  
	//concstructor

		public WxMpConfig(){
		}

		public WxMpConfig(
			java.lang.String id
		){
			this.id = id;
		}

		//get and set
		@Override
        public void setId(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.id = value;
		}
		
		@Override
        @Id
	     @WhereSQL(sql="id=:WxMpconfig_id")
		public java.lang.String getId() {
			return this.id;
		}
		public void setSiteId(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.siteId = value;
		}
		
	     @WhereSQL(sql="siteId=:WxMpconfig_siteId")
		public java.lang.String getSiteId() {
			return this.siteId;
		}
		@Override
        public void setAppId(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.appId = value;
		}
		
	     @Override
        @WhereSQL(sql="appId=:WxMpconfig_appId")
		public java.lang.String getAppId() {
			return this.appId;
		}
		@Override
        public void setSecret(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.secret = value;
		}
		
	     @Override
        @WhereSQL(sql="secret=:WxMpconfig_secret")
		public java.lang.String getSecret() {
			return this.secret;
		}
		@Override
        public void setToken(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.token = value;
		}
		
	     @Override
        @WhereSQL(sql="token=:WxMpconfig_token")
		public java.lang.String getToken() {
			return this.token;
		}
		@Override
        public void setAesKey(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.aesKey = value;
		}
		
	     @Override
        @WhereSQL(sql="aesKey=:WxMpconfig_aesKey")
		public java.lang.String getAesKey() {
			return this.aesKey;
		}
		public void setWxId(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.wxId = value;
		}
		
	     @WhereSQL(sql="wxId=:WxMpconfig_wxId")
		public java.lang.String getWxId() {
			return this.wxId;
		}
		public void setActive(java.lang.Integer value) {
			this.active = value;
		}
		
	     @WhereSQL(sql="active=:WxMpconfig_active")
		public java.lang.Integer getActive() {
			return this.active;
		}
		@Override
        public void setPartnerId(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.partnerId = value;
		}
		
	     @Override
        @WhereSQL(sql="partnerId=:WxMpconfig_partnerId")
		public java.lang.String getPartnerId() {
			return this.partnerId;
		}
		@Override
        public void setPartnerKey(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.partnerKey = value;
		}
		
	     @Override
        @WhereSQL(sql="partnerKey=:WxMpconfig_partnerKey")
		public java.lang.String getPartnerKey() {
			return this.partnerKey;
		}
	     
	     @Override
		public void setOauth2(java.lang.Integer value) {
			 
			this.oauth2 = value;
		}
		
	     @WhereSQL(sql="oauth2=:WxMpconfig_oauth2")
	     @Override
		public java.lang.Integer getOauth2() {
			return this.oauth2;
		}
		@Override
        public void setHttpProxyHost(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.httpProxyHost = value;
		}
		
	     @Override
        @WhereSQL(sql="httpProxyHost=:WxMpconfig_httpProxyHost")
		public java.lang.String getHttpProxyHost() {
			return this.httpProxyHost;
		}
		@Override
        public void setHttpProxyPort(java.lang.Integer value) {
			this.httpProxyPort = value;
		}
		
	     @Override
        @WhereSQL(sql="httpProxyPort=:WxMpconfig_httpProxyPort")
		public java.lang.Integer getHttpProxyPort() {
			return this.httpProxyPort;
		}
		@Override
        public void setHttpProxyUsername(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.httpProxyUsername = value;
		}
		
	     @Override
        @WhereSQL(sql="httpProxyUsername=:WxMpconfig_httpProxyUsername")
		public java.lang.String getHttpProxyUsername() {
			return this.httpProxyUsername;
		}
		@Override
        public void setHttpProxyPassword(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.httpProxyPassword = value;
		}
		
	     @Override
        @WhereSQL(sql="httpProxyPassword=:WxMpconfig_httpProxyPassword")
		public java.lang.String getHttpProxyPassword() {
			return this.httpProxyPassword;
		}
		@Override
        public void setCertificateFile(java.lang.String value) {
			    if(StringUtils.isNotBlank(value)){
				 value=value.trim();
				}
			this.certificateFile = value;
		}
		
	     @Override
        @WhereSQL(sql="certificateFile=:WxMpconfig_certificateFile")
		public java.lang.String getCertificateFile() {
			return this.certificateFile;
		}
		
		@Override
        public String toString() {
			return new StringBuilder()
				.append("id[").append(getId()).append("],")
				.append("站点Id[").append(getSiteId()).append("],")
				.append("开发者Id[").append(getAppId()).append("],")
				.append("应用密钥[").append(getSecret()).append("],")
				.append("开发者Id[").append(getToken()).append("],")
				.append("消息加解密密钥[").append(getAesKey()).append("],")
				.append("原始ID[").append(getWxId()).append("],")
				.append("状态 0不可用,1可用[").append(getActive()).append("],")
				.append("partnerId[").append(getPartnerId()).append("],")
				.append("partnerKey[").append(getPartnerKey()).append("],")
				.append("是否支持微信oauth2.0协议,0是不支持,1是支持[").append(getOauth2()).append("],")
				.append("http代理地址[").append(getHttpProxyHost()).append("],")
				.append("http代理端口[").append(getHttpProxyPort()).append("],")
				.append("http代理账号[").append(getHttpProxyUsername()).append("],")
				.append("http代理密码[").append(getHttpProxyPassword()).append("],")
				.append("证书地址[").append(getCertificateFile()).append("],")
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
			if(obj instanceof WxMpConfig == false){
				return false;
			} 
			if(this == obj){
				return true;
			} 
			WxMpConfig other = (WxMpConfig)obj;
			return new EqualsBuilder()
				.append(getId(),other.getId())
				.isEquals();
		}
	  
	@Override
    @Transient
	public String getAccessToken() {
		return accessToken;
	}
	@Override
    public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	@Override
    @Transient
	public String getJsApiTicket() {
		return jsApiTicket;
	}
	@Override
    public void setJsApiTicket(String jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}
	@Override
    @Transient
	public String getCardApiTicket() {
		return cardApiTicket;
	}
	@Override
    public void setCardApiTicket(String cardApiTicket) {
		this.cardApiTicket = cardApiTicket;
	}
	@Override
    @Transient
	public String getTmpDirFile() {
		return tmpDirFile;
	}
	@Override
    public void setTmpDirFile(String tmpDirFile) {
		this.tmpDirFile = tmpDirFile;
	}
	
	
	@Transient
	public Long getAccessTokenExpiresTime() {
		return accessTokenExpiresTime;
	}
	@Override
    public void setAccessTokenExpiresTime(Long accessTokenExpiresTime) {
		this.accessTokenExpiresTime =  System.currentTimeMillis() + (accessTokenExpiresTime - 600) * 1000L;//预留10分钟
	}
	
	
	@Transient
	public Long getCardApiTicketExpiresTime() {
		return cardApiTicketExpiresTime;
	}
	@Override
    public void setCardApiTicketExpiresTime(Long cardApiTicketExpiresTime) {
		//预留10分钟
		this.cardApiTicketExpiresTime = System.currentTimeMillis() + (cardApiTicketExpiresTime - 600) * 1000L;//预留10分钟
	}

	@Transient
	public Long getJsApiTicketExpiresTime() {
		return jsApiTicketExpiresTime;
	}
	@Override
    public void setJsApiTicketExpiresTime(Long jsApiTicketExpiresTime) {
		this.jsApiTicketExpiresTime =  System.currentTimeMillis() + (jsApiTicketExpiresTime - 600) * 1000L;//预留10分钟
	}
	@Override
    @Transient
	public boolean isAccessTokenExpired() {
		 return System.currentTimeMillis() > this.accessTokenExpiresTime;
	}
	@Override
    @Transient
	public boolean isJsApiTicketExpired() {
	    return System.currentTimeMillis() > this.jsApiTicketExpiresTime;
	  }
	@Override
    @Transient
	public boolean isCardApiTicketExpired() {
	    return System.currentTimeMillis() > this.cardApiTicketExpiresTime;
	  }
	@Override
    @Transient
	public boolean autoRefreshToken() {
	    return true;
	  }

	

}
