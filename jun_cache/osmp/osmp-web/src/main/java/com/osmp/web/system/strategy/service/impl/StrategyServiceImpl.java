package com.osmp.web.system.strategy.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.cache.annotation.Cacheable;
import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.strategy.dao.StrategyConditionMapper;
import com.osmp.web.system.strategy.dao.StrategyMapper;
import com.osmp.web.system.strategy.entity.Strategy;
import com.osmp.web.system.strategy.entity.StrategyCondition;
import com.osmp.web.system.strategy.service.StrategyService;
import com.osmp.web.utils.Pagination;

/**
 * 
 * Description:
 *
 * @author: chelongquan
 * @date: 2015年5月4日 上午9:44:29
 */
@Service
public class StrategyServiceImpl implements StrategyService {

	@Autowired
	private StrategyMapper strategyMapper;

	@Autowired
	private StrategyConditionMapper strategyConditionMapper;

	@Override
	public List<Strategy> getStrategyByPage(Pagination<Strategy> p, String where) {
		return strategyMapper.selectByPage(p, Strategy.class, where);
	}

	@Override
	public Strategy getStrategy(Strategy strategy) {
		final List<Strategy> list = strategyMapper.getObject(strategy);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new Strategy();
	}

	@Cacheable(name = "策略列表", timeToLive = 1600, timeToIdle = 500)
	@Override
	public List<StrategyCondition> getAll(String where) {
		return strategyConditionMapper.selectAll(StrategyCondition.class, where);
	}

	@Override
	public void deletStrategy(Strategy strategy) {
		strategyMapper.deletConditionByStrategy(strategy.getId());
		strategyMapper.delete(strategy);
	}

	@Override
	public void refresh() {
		SystemFrameWork.refreshStrategy();
	}

	@Override
	public Map<String, List<StrategyCondition>> getStrategyMap() {
		Map<String, List<StrategyCondition>> map = new ConcurrentHashMap<String, List<StrategyCondition>>();
		List<Strategy> strategyList = strategyMapper.selectAll(Strategy.class, "");
		for (Strategy s : strategyList) {
			String id = s.getId();
			map.put(id, getConditionByStrategy(s));
		}
		return map;
	}

	private List<StrategyCondition> getConditionByStrategy(Strategy strategy) {
		List<StrategyCondition> result = strategyConditionMapper.selectAll(StrategyCondition.class, "strategy_id='"
				+ strategy.getId() + "'");
		return result;
	}

}
