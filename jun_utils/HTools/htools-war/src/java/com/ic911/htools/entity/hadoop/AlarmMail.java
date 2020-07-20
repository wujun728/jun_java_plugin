package com.ic911.htools.entity.hadoop;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.ic911.core.domain.BaseEntity;
import com.ic911.core.hadoop.MasterConfig;
/**
 * 告警邮箱
 * @author caoyang
 */
@Entity
@Table(name = "hdp_alarm_mail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AlarmMail extends BaseEntity{
	private static final long serialVersionUID = -7177194745881463002L;

	@NotBlank(message="邮箱地址不能为空!")
	@Email(message="告警邮箱格式不正确!")
	private String mail;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "config_id")
	private MasterConfig masterConfig;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public MasterConfig getMasterConfig() {
		return masterConfig;
	}

	public void setMasterConfig(MasterConfig masterConfig) {
		this.masterConfig = masterConfig;
	}
	
}
