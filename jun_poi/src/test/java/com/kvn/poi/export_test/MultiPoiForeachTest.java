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
 * 测试有多个<poi:foreach>的情况
* @author wzy
* @date 2017年7月4日 下午5:52:23
*/
public class MultiPoiForeachTest {
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> rootObjectMap = Maps.newHashMap();
		Order order1 = MockUtil.randomInstanceOfNonCollection(Order.class);
		Order order2 = MockUtil.randomInstanceOfNonCollection(Order.class);
		Order order3 = MockUtil.randomInstanceOfNonCollection(Order.class);
		
		List list = Lists.newArrayList(order1, order2, order3);
		rootObjectMap.put("list", list);
		
		
		Order order4 = MockUtil.randomInstanceOfNonCollection(Order.class);
		Order order5 = MockUtil.randomInstanceOfNonCollection(Order.class);
		List list2 = Lists.newArrayList(order4, order5);
		rootObjectMap.put("list2", list2);
		
		OutputStream out = new FileOutputStream("E:\\rlt\\test.xlsx");
		InputStream is = ForeachTest.class.getClassLoader().getResourceAsStream("template/02_Template_多个foreach.xlsx");
		PoiExporter.export2Destination(is, rootObjectMap, out);
	}

}
