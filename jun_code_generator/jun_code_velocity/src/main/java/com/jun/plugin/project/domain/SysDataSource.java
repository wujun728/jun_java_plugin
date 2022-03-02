package com.jun.plugin.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.jun.plugin.framework.web.domain.BaseEntity;



/**
 * 系统数据源配置表 sys_config
 * 
 * @author ruoyi
 */
public class SysDataSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据源主键 */
    private Long id;

    /** 名称 */
    private String name;

    /** 数据库类型*/
    private String dbType;
    
    /** oracle连接方式*/
    private String oracleConnMode;
    
    /** oracle连接服务名或SID*/
    private String serviceNameOrSid;
    
    /** 主机 */
    private String host;
    
    /** 端口号 */
    private Integer port;
    
    /** 用户名 */
    private String username;
    
    /** 密码 */
    private String password;
    
    /** 模式 */
    private String schemaName = "public";
    
    /** 状态（0正常 1停用） */
    private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getOracleConnMode() {
		return oracleConnMode;
	}

	public void setOracleConnMode(String oracleConnMode) {
		this.oracleConnMode = oracleConnMode;
	}

	public String getServiceNameOrSid() {
		return serviceNameOrSid;
	}

	public void setServiceNameOrSid(String serviceNameOrSid) {
		this.serviceNameOrSid = serviceNameOrSid;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("dbType", getDbType())
            .append("oracleConnType", getOracleConnMode())
            .append("serviceNameOrSid", getServiceNameOrSid())
            .append("host", getHost())
            .append("port", getPort())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("schemaName", getSchemaName())
            .append("status", getStatus())
            .toString();
    }
}
