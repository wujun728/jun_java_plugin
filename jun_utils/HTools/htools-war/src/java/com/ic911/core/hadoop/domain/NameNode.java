package com.ic911.core.hadoop.domain;

import java.util.Collection;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Sets;
import com.ic911.core.hadoop.MasterConfig;

/**
 * 元数据节点
 * @author caoyang
 */
@Entity
@Table(name = "hdp_nn")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NameNode extends BaseNodeInfo{
	private static final long serialVersionUID = 92501554357338497L;

	@NotNull
	@OneToOne
	@JoinColumn(name = "config_id")
	private MasterConfig masterConfig;
	
	@OneToOne
	@JoinColumn(name = "ssn_id")
	private SecondaryNameNode secondaryNameNode;
	
	@OneToOne
	@JoinColumn(name = "jt_id")
	private JobTracker jobTracker;
	
	@OneToMany(mappedBy = "nameNode",fetch=FetchType.LAZY)
	private Collection<DataNode> dataNodes = Sets.newHashSet();
	
	@Transient
	private ConcurrentSkipListSet<DataNode> cloneDataNodes = new ConcurrentSkipListSet<DataNode>();

	public MasterConfig getMasterConfig() {
		return masterConfig;
	}

	public void setMasterConfig(MasterConfig masterConfig) {
		this.masterConfig = masterConfig;
	}

	public Collection<DataNode> getDataNodes() {
		return dataNodes;
	}
	
	public Collection<DataNode> getCloneDataNodes() {
		return cloneDataNodes;
	}

	public void setDataNodes(Collection<DataNode> dataNodes) {
		this.dataNodes = dataNodes;
		try{
			cloneDataNodes.clear();
			for(DataNode node: dataNodes){
				cloneDataNodes.add(node);
			}
		}catch(Exception e){
		}
	}

	public JobTracker getJobTracker() {
		return jobTracker;
	}

	public void setJobTracker(JobTracker jobTracker) {
		this.jobTracker = jobTracker;
	}

	public SecondaryNameNode getSecondaryNameNode() {
		return secondaryNameNode;
	}

	public void setSecondaryNameNode(SecondaryNameNode secondaryNameNode) {
		this.secondaryNameNode = secondaryNameNode;
	}

}
