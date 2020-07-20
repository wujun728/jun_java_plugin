package com.ic911.core.hadoop.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
/**
 * 次要元数据节点
 * 用于恢复主节点数据
 * @see http://www.cnblogs.com/ggjucheng/archive/2012/04/18/2454693.html
 * @author caoyang
 */
@Entity
@Table(name = "hdp_snn")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecondaryNameNode extends BaseNodeInfo{
	private static final long serialVersionUID = -2699297806064655714L;
	
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
