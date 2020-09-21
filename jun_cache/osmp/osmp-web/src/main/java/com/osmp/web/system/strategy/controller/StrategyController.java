package com.osmp.web.system.strategy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.system.strategy.dao.StrategyConditionMapper;
import com.osmp.web.system.strategy.dao.StrategyMapper;
import com.osmp.web.system.strategy.entity.Strategy;
import com.osmp.web.system.strategy.entity.StrategyCondition;
import com.osmp.web.system.strategy.service.StrategyService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;
import com.osmp.web.utils.Utils;
/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年5月4日 上午9:29:33
 */
@Controller
@RequestMapping("/strategy")
public class StrategyController {
	
	@Autowired
	private StrategyService strategyService;
	
	@Autowired
	private StrategyMapper strategyMapper;
	
	@Autowired
	private StrategyConditionMapper strategyConditionMapper;
	
	@RequestMapping("/toList")
	public String toList() {
		return "system/strategy/strategylist";
	}

	@RequestMapping("/strategyList")
	@ResponseBody
	public Map<String, Object> serversList(DataGrid dg) {
		final Pagination<Strategy> strategy = new Pagination<Strategy>(dg.getPage(), dg.getRows());
		final List<Strategy> list = strategyService.getStrategyByPage(strategy, "");
		dg.setTotal(strategy.getTotal());
		dg.setResult(list);
		return dg.getMap();
	}
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "system/strategy/strategyAdd";
	}
	
	@RequestMapping("/deleteStrategy")
	@ResponseBody
	public Map<String, Object> deleteStrategy(Strategy p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			strategyService.deletStrategy(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/toEdit")
	public ModelAndView toEdit(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		final ModelAndView mv = new ModelAndView("system/strategy/strategyEdit");
		if (!"".equals(id)) {
			final Strategy strategy = new Strategy();
			strategy.setId(id);
			mv.addObject("strategy", strategyService.getStrategy(strategy));
		}
		return mv;
	}
	
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Strategy strategy) {
		final Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (null != strategy && Utils.isEmpty(strategy.getId())) {
				strategy.setId(UUID.randomUUID().toString());
				strategyMapper.insert(strategy);
			} else {
				strategyMapper.update(strategy);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/toConditionList")
	public ModelAndView toConditionList(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		ModelAndView mv = new ModelAndView("system/strategy/conditionList");
		mv.addObject("id", id);
		return mv;
	}
	
	@RequestMapping("/conditionList")
	@ResponseBody
	public List<StrategyCondition> conditionList(String id) {
		return strategyService.getAll("strategy_id = '"+id+"'");
	}
	
	@RequestMapping("/toConditionAdd")
	public ModelAndView toConditionAdd(@RequestParam(value = "id", required = false, defaultValue = "") String id) {
		ModelAndView mv = new ModelAndView("system/strategy/conditionAdd");
		mv.addObject("id", id);
		return mv;
	}
	
	@RequestMapping("/conditionAdd")
	@ResponseBody
	public Map<String, Object> conditionAdd(StrategyCondition p) {
		final Map<String, Object> map = new HashMap<String, Object>();
		try {
			p.setId(UUID.randomUUID().toString());
			strategyConditionMapper.insert(p);
		} catch (final Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/deleteCondition")
	@ResponseBody
	public Map<String, Object> deleteCondition(StrategyCondition p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			strategyConditionMapper.delete(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	/**
	 * 刷新分发策略到内存
	 * 
	 * @return
	 */
	@RequestMapping("/refresh")
	@ResponseBody
	public Map<String, Object> refresh() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			strategyService.refresh();
			map.put("success", true);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
		}
		return map;
	}
}
