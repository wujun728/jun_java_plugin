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

/**
* @author wzy
* @date 2017年7月4日 下午5:52:23
*/
public class ForeachTest {
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> rootObjectMap = Maps.newHashMap();
		Order order1 = MockUtil.randomInstanceOfNonCollection(Order.class);
		Order order2 = MockUtil.randomInstanceOfNonCollection(Order.class);
		Order order3 = MockUtil.randomInstanceOfNonCollection(Order.class);
		
		List list = Lists.newArrayList(order1, order2, order3);
		rootObjectMap.put("list", list);
		OutputStream des = new FileOutputStream("E:\\rlt\\test.xlsx");
		InputStream is = ForeachTest.class.getClassLoader().getResourceAsStream("template/01_Template_foreach.xlsx");
		PoiExporter.export2Destination(is, rootObjectMap, des);
	}

}
