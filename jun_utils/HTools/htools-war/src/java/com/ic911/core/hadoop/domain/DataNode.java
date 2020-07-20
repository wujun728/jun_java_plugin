package com.ic911.core.hadoop.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * 数据节点
 * @author caoyang
 */
@Entity
@Table(name = "hdp_dn")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataNode extends BaseNodeInfo{
	private static final long serialVersionUID = -3044953190650551189L;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "nn_id")
	private NameNode nameNode;

	public NameNode getNameNode() {
		return nameNode;
	}

	public void setNameNode(NameNode nameNode) {
		this.nameNode = nameNode;
	}
	
}
