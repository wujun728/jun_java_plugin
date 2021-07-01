package com.vacomall.lucene.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.kit.JsonKit;
import com.vacomall.lucene.bean.JGoods;
import com.vacomall.lucene.bean.Jpage;
import com.vacomall.lucene.bean.QueryConfig;
import com.vacomall.lucene.enums.SortType;
import com.vacomall.lucene.service.ILuceneService;

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
		
		return JsonKit.toJson(jpage);
		
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
		
		return JsonKit.toJson(jpage);
	}
}
