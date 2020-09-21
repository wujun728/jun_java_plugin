package com.osmp.web.system.strategy.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年5月4日 上午9:22:36
 */
@Table(name="t_strategy_condition")
public class StrategyCondition {
	
	@Id
    @Column
	private String id;
	
	@Column(name="strategy_id")
	private String strategyId;
	
	@Column(name="name_key")
	private String key;
	
	@Column
	private String type;
	
	@Column(name="type_remark")
	private String typeRemark;
	
	@Column
	private String value;
	
	@Column(name="judge")
	private String condition;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTypeRemark() {
		return typeRemark;
	}

	public void setTypeRemark(String typeRemark) {
		this.typeRemark = typeRemark;
	}
	
}
