package com.jun.plugin.project.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.jun.plugin.framework.web.domain.BaseEntity;

/**
 * 系统配置表 sys_config
 * 
 * @author ruoyi
 */
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配置主键 */
    private Long configId;

    /** 作者 */
    private String author;

    /** 默认生成包路径*/
    private String packageName;

    /** 自动去除表前缀（0否 1是） */
    private String autoRemovePre;
    
    /** 表前缀 */
    private String tablePrefix;

    
    public Long getConfigId() {
		return configId;
	}


	public void setConfigId(Long configId) {
		this.configId = configId;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAutoRemovePre() {
		return autoRemovePre;
	}


	public void setAutoRemovePre(String autoRemovePre) {
		this.autoRemovePre = autoRemovePre;
	}


	public String getTablePrefix() {
		return tablePrefix;
	}


	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}


	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("author", getAuthor())
            .append("packageName", getPackageName())
            .append("autoRemovePre", getAutoRemovePre())
            .append("tablePrefix", getTablePrefix())
            .toString();
    }
}
