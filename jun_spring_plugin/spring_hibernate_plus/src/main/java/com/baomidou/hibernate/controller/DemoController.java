package com.baomidou.hibernate.controller;

import com.baomidou.hibernate.vo.DemoVO;
import com.baomidou.hibernate.service.DemoService;
import com.baomidou.hibernateplus.condition.DeleteWrapper;
import com.baomidou.hibernateplus.condition.SelectWrapper;
import com.baomidou.hibernateplus.entity.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

	@Autowired
	private DemoService demoService;

	@RequestMapping("/test")
	@ResponseBody
	public void test() {
		List<DemoVO> lists = new ArrayList<DemoVO>();
		for (int i = 0; i <= 100; i++) {
			DemoVO vdemo = new DemoVO();
			vdemo.setDemo1(i + "");
			vdemo.setDemo2(i + "");
			vdemo.setDemo3(i + "");
			lists.add(vdemo);
		}
		// 批量插入
		boolean insertBatch = demoService.insertBatch(lists);
		System.out.println(insertBatch);
		// Condition 链式查询列表
		List<DemoVO> vdemoList = demoService.selectList(SelectWrapper.instance().le("id", 10));
		System.out.println(vdemoList);
		Map map = new HashMap();
		map.put("id", 99L);
		// 根据Condition 查询单条记录
		DemoVO vdemo = demoService.selectOne(SelectWrapper.instance().eq("id", 10));
		System.out.println(vdemo);
		List<Map<String, Object>> mapList = demoService.selectMaps(SelectWrapper.instance().ge("id", 80));
		System.out.println(mapList);
		// 根据属性查询单条记录
		DemoVO vdemo1 = demoService.get("1");
		if (vdemo1 != null) {
			vdemo1.setDemo1("999");
			vdemo1.setDemo2("999");
			vdemo1.setDemo3("999");
			// 修改或保存
			demoService.saveOrUpdate(vdemo1);
		}

		DemoVO vdemo2 = demoService.get("1");
		if (vdemo2 != null) {
			vdemo2.setId(null);
			demoService.saveOrUpdate(vdemo2);
		}

		int selectCount2 = demoService.selectCount(SelectWrapper.instance().ge("id", 80));
		System.out.println(selectCount2);
		Page page = new Page(1, 20);
		page.setOrderByField("id");
		page.setAsc(false);
		// 查询分页
		Page selectPage = demoService.selectPage(SelectWrapper.DEFAULT, page);
		System.out.println(selectPage);
		// Condition链式查询分页返回Map
		Page selectMapPage = demoService.selectMapsPage(SelectWrapper.instance().ge("id", 50), page);
		System.out.println(selectMapPage);
		// Condition链式查询分页返回VO
		Page selectPage2 = demoService.selectPage(SelectWrapper.instance().ge("id", 50), page);
		System.out.println(selectPage2);
		// Condition链式 删除单条记录
		demoService.delete(DeleteWrapper.instance().eq("id", 1));
		List<DemoVO> vdemos = demoService.selectList(SelectWrapper.instance());
		Iterator<DemoVO> iterator = vdemos.iterator();
		while (iterator.hasNext()) {
			DemoVO vdemo3 = iterator.next();
			vdemo3.setDemo1(vdemo3.getDemo1() + "Caratacus Plus 1");
			vdemo3.setDemo2(vdemo3.getDemo2() + "Caratacus Plus 2");
			vdemo3.setDemo3(vdemo3.getDemo3() + "Caratacus Plus 3");
		}
		// 批量修改
		demoService.updateBatch(vdemos);
		// Condition链式 删除所有记录
		demoService.delete(DeleteWrapper.DEFAULT);

	}

}
