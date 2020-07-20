package com.ic911.core.hadoop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import com.ic911.core.commons.NetUtils;
import com.ic911.core.domain.BaseEntity;
/**
 * 主节点的详细hadoop配置信息，主要是用于管理和ssh连接
 * 需要向客户推荐，所有节点尽可能使用统一的配置及认证
 * @see http://www.cnblogs.com/ggjucheng/archive/2012/04/17/2454590.html
 * @author caoyang
 */
@Entity
@Table(name = "hdp_master_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MasterConfig extends BaseEntity{
	private static final long serialVersionUID = -860793560625703894L;

	@NotBlank(message="主机名不能为空!")
	@Column(nullable=false,unique=true)
	private String hostname;
	
	@NotBlank(message="IP地址不能为空!")
	@Column(nullable=false,unique=true)
	private String ip;
	
	@NotNull(message="HDFS端口不能为空!")
	@Column(nullable=false)
	private Integer hdfsPort = 9000;
	
	@NotBlank(message="系统字符集不能为空!")
	@Column(nullable=false)
	private String encode = "UTF-8";
	
	private String hdfsBasePath = "";//默认是/user/hadoop用户
	
	@NotBlank(message="JDK虚拟机安装目录不能为空!")
	@Column(nullable=false)
	private String jvmSetupPath;
	
	@NotBlank(message="Hadoop安装目录不能为空!")
	@Column(nullable=false)
	private String hadoopSetupPath;
	
	@Column(nullable=false)
	private Boolean enabled = Boolean.TRUE;//是否启用
	
	private String hadoopNamesecondaryPath;//默认值core-site.xml fs.checkpoint.dir属性${hadoop.tmp.dir}/dfs/namesecondary
	
	private String hadoopTmpPath;//默认core-site.xml文件hadoop.tmp.dir属性为/tmp/hadoop-用户名,初始化时可以参考HadoopHandler相关方法。

	private String hadoopNamePath;//默认hdfs-site.xml文件dfs.name.dir属性为{hadoop.tmp.dir}/dfs/name,初始化时可以参考HadoopHandler相关方法。
	
	//private String hadoopDataPath;//可以多个用逗号分隔，默认hdfs-site.xm文件dfs.data.dir属性为${hadoop.tmp.dir}/dfs/data,初始化时可以参考HadoopHandler相关方法。
	
	@NotBlank(message="SSH用户名不能为空!")
	@Column(nullable=false)
	private String sshUsername;
	
	@NotBlank(message="SSH密码不能为空!")
	@Column(nullable=false)
	private String sshPassword;
	
	@NotNull(message="SSH端口不能为空!")
	@Column(nullable=false)
	private Integer sshPort = 22;
	
	private String hbaseSetupPath;//目前版本没有用到，备用
	
	private String hiveSetupPath;//目前版本没有用到，备用
	
	private String pigSetupPath;//目前版本没有用到，备用
	
	private String sqoopSetupPath;//目前版本没有用到，备用
	
	private String zookeeperSetupPath;//目前版本没有用到，备用
	

	public Integer getHdfsPort() {
		return hdfsPort;
	}

	public void setHdfsPort(Integer hdfsPort) {
		this.hdfsPort = hdfsPort;
	}

	public String getSshUsername() {
		return sshUsername;
	}

	public void setSshUsername(String sshUsername) {
		this.sshUsername = sshUsername;
	}

	public String getSshPassword() {
		return sshPassword;
	}

	public void setSshPassword(String sshPassword) {
		this.sshPassword = sshPassword;
	}

	public Integer getSshPort() {
		return sshPort;
	}

	public void setSshPort(Integer sshPort) {
		this.sshPort = sshPort;
	}

	public String getHdfsFullPath() {
		return "hdfs://".concat(getIp()).concat(":").concat(""+getHdfsPort()).concat(getHdfsBasePath());
	}
	
	public String getHdfsBasePath() {
		return this.hdfsBasePath;
	}

	public void setHdfsBasePath(String hdfsBasePath) {
		this.hdfsBasePath = hdfsBasePath;
	}

	public String getHadoopSetupPath() {
		return hadoopSetupPath;
	}

	public void setHadoopSetupPath(String hadoopSetupPath) {
		this.hadoopSetupPath = hadoopSetupPath;
	}

	public String getHbaseSetupPath() {
		return hbaseSetupPath;
	}

	public void setHbaseSetupPath(String hbaseSetupPath) {
		this.hbaseSetupPath = hbaseSetupPath;
	}

	public String getHiveSetupPath() {
		return hiveSetupPath;
	}

	public void setHiveSetupPath(String hiveSetupPath) {
		this.hiveSetupPath = hiveSetupPath;
	}

	public String getPigSetupPath() {
		return pigSetupPath;
	}

	public void setPigSetupPath(String pigSetupPath) {
		this.pigSetupPath = pigSetupPath;
	}

	public String getSqoopSetupPath() {
		return sqoopSetupPath;
	}

	public void setSqoopSetupPath(String sqoopSetupPath) {
		this.sqoopSetupPath = sqoopSetupPath;
	}

	public String getZookeeperSetupPath() {
		return zookeeperSetupPath;
	}

	public void setZookeeperSetupPath(String zookeeperSetupPath) {
		this.zookeeperSetupPath = zookeeperSetupPath;
	}

	public String getJvmSetupPath() {
		return jvmSetupPath;
	}

	public void setJvmSetupPath(String jvmSetupPath) {
		this.jvmSetupPath = jvmSetupPath;
	}

	public String getHadoopTmpPath() {
		if(hadoopTmpPath==null||hadoopTmpPath.trim().isEmpty()){
			return "/tmp/hadoop-".concat(getSshUsername());
		}
		return hadoopTmpPath;
	}

	public void setHadoopTmpPath(String hadoopTmpPath) {
		this.hadoopTmpPath = hadoopTmpPath;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

/*	public String getHadoopDataPath() {
		if(hadoopDataPath==null||hadoopDataPath.trim().isEmpty()){
			return getHadoopTmpPath().concat("/dfs/data");
		}
		return hadoopDataPath;
	}

	public void setHadoopDataPath(String hadoopDataPath) {
		this.hadoopDataPath = hadoopDataPath;
	}*/

	public String getHadoopNamePath() {
		if(hadoopNamePath==null||hadoopNamePath.trim().isEmpty()){
			return getHadoopTmpPath().concat("/dfs/name");
		}
		return hadoopNamePath;
	}

	public void setHadoopNamePath(String hadoopNamePath) {
		this.hadoopNamePath = hadoopNamePath;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIp() {
		return ip==null?NetUtils.getIpByHostname(hostname):ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHadoopNamesecondaryPath() {
		if(hadoopNamesecondaryPath==null||hadoopNamesecondaryPath.trim().isEmpty()){
			return getHadoopTmpPath().concat("/dfs/namesecondary");
		}
		return hadoopNamesecondaryPath;
	}

	public void setHadoopNamesecondaryPath(String hadoopNamesecondaryPath) {
		this.hadoopNamesecondaryPath = hadoopNamesecondaryPath;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
