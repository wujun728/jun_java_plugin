package com.osmp.web.system.strategy.dao;

import org.apache.ibatis.annotations.Param;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.system.strategy.entity.Strategy;

public interface StrategyMapper extends BaseMapper<Strategy> {

	public void deletConditionByStrategy(@Param("strategyId" )String strategyId);
}
