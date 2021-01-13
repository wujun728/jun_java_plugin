package com.kvn.poi.import_test;

import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.kvn.poi.imp.PoiImporter;
import com.kvn.poi.imp.vo.PoiGenericSheetVo;
import com.kvn.poi.importvo.OrderImportVo;

/**
* @author wzy
* @date 2017年7月12日 下午2:00:30
*/
public class ImportGenericTest {

	public static void main(String[] args) {
		InputStream is = ImportGenericTest.class.getClassLoader().getResourceAsStream("excel/order.xlsx");
		PoiGenericSheetVo<OrderImportVo> genericSheetVo = PoiImporter.importFirstSheetFrom(is, OrderImportVo.class);
		System.out.println("===>" + JSON.toJSONString(genericSheetVo.getHead()));
		System.out.println("===>" + JSON.toJSONString(genericSheetVo.getBody()));
	}
	
}
