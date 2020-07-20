package com.ic911.htools.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ic911.core.domain.BaseEntity;
/**
 * 学习知识库
 * @author caoyang
 */
@Entity
@Table(name = "hdp_learn")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Learn extends BaseEntity{
	private static final long serialVersionUID = -3541350841974045651L;
	
	private Integer num;
	
	@Column(length=1000)
	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
