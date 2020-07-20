package com.ic911.core.hadoop.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 作业节点
 * @author caoyang
 */
@Entity
@Table(name = "hdp_jt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobTracker extends BaseNodeInfo{
	private static final long serialVersionUID = 92501554357338497L;

	@NotNull
	@OneToOne
	@JoinColumn(name = "nn_id")
	private NameNode nameNode;
	
	public NameNode getNameNode() {
		return nameNode;
	}

	public void setNameNode(NameNode nameNode) {
		this.nameNode = nameNode;
	}
	
}
