package com.kvn.poi.export_test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kvn.poi.MockUtil;
import com.kvn.poi.exp.PoiExporter;
import com.kvn.poi.exportvo.Order;
import com.kvn.poi.exportvo.Plan;

/**
* @author wzy
* @date 2017年7月4日 下午5:52:23
*/
public class MixTemplateTest {

	public static void main(String[] args) throws Exception {
		Map<String, Object> rootObjectMap = Maps.newHashMap();
		Plan plan = MockUtil.randomInstanceOfNonCollection(Plan.class);
		Order order1 = MockUtil.randomInstanceOfNonCollection(Order.class);
		Order order2 = MockUtil.randomInstanceOfNonCollection(Order.class);
		List<Order> list = Lists.newArrayList(order1, order2);
		
		
		rootObjectMap.put("mangerName", "张三");
		rootObjectMap.put("plan", plan);
		rootObjectMap.put("list", list);
		
		OutputStream out = new FileOutputStream("E:\\rlt\\test.xlsx");
		InputStream is = ForeachTest.class.getClassLoader().getResourceAsStream("template/03_Template_混合模板.xlsx");
		PoiExporter.export2Destination(is, rootObjectMap, out);
	}

}
