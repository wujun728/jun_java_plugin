package com.osmp.web.system.strategy.service;

import java.util.List;
import java.util.Map;

import com.osmp.web.system.strategy.entity.Strategy;
import com.osmp.web.system.strategy.entity.StrategyCondition;
import com.osmp.web.utils.Pagination;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年5月4日 上午9:40:57
 */
public interface StrategyService {

	public List<Strategy> getStrategyByPage(Pagination<Strategy> p, String where);

	public Strategy getStrategy(Strategy strategy);
	
	/**
	 * 获取满足条件的策略条件
	 * @param where
	 * @return
	 */
	public List<StrategyCondition> getAll(String where);
	
	/**
	 * 删除策略，关联删除改策略的条件
	 * @param strategy
	 */
	public void deletStrategy(Strategy strategy);
	
	/**
	 * 刷内存
	 */
	public void refresh();
	
	/**
	 * 初始化分发策略到内存
	 * 
	 * @return
	 */
	public Map<String, List<StrategyCondition>> getStrategyMap();

}
