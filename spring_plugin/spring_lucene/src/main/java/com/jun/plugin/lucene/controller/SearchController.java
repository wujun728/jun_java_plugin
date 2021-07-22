package com.jun.plugin.lucene.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
//import com.jfinal.kit.JsonKit;
import com.jun.plugin.lucene.bean.JGoods;
import com.jun.plugin.lucene.bean.Jpage;
import com.jun.plugin.lucene.bean.QueryConfig;
import com.jun.plugin.lucene.enums.SortType;
import com.jun.plugin.lucene.service.ILuceneService;

/**
 * 搜索控制器
 * @author gaojun.zhou
 * @date 2016年9月2日
 */
@RequestMapping("/")
@Controller
public class SearchController {
	final Logger logger = Logger.getLogger(SearchController.class);

	@Autowired private ILuceneService luceneService;
	
	/**
	 * 搜索
	 * @param q
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search")
	@ResponseBody
	public String search(
			
			String q,
			@RequestParam(defaultValue = "1",name="pageNumber") Integer pageNumber,
			@RequestParam(defaultValue = "80",name="pageSize") Integer pageSize
			) throws Exception  {
		
		Jpage<JGoods> jpage = luceneService.search(q, pageNumber, pageSize);
		
		return JSON.toJSONString(jpage);
		
	}
	
	/**
	 * 搜索2
	 * @param q
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/search2")
	@ResponseBody
	public String search2(
			
			String q,
			@RequestParam(defaultValue = "1",name="pageNumber") Integer pageNumber,
			@RequestParam(defaultValue = "80",name="pageSize") Integer pageSize,
			Integer sort,
			Float min,
			Float max
			) throws Exception  {
		
		QueryConfig<JGoods> config = new QueryConfig<JGoods>();
		config.setKw(q);
		config.setJpage(new Jpage<JGoods>(pageNumber, pageSize));
		config.setMin(min);
		config.setMax(max);
		
		config.setSortType(SortType.getSortType(sort));
		//config.setQueryFilter(new QueryFilter("id","501bde049ac3476ba614541314db9fac"));
		Jpage<JGoods> jpage = luceneService.search(config);
		
		return JSON.toJSONString(jpage);
	}
}
