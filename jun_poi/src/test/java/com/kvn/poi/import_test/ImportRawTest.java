package com.kvn.poi.import_test;

import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.kvn.poi.imp.PoiImporter;
import com.kvn.poi.imp.vo.PoiSheetVo;

/**
* @author wzy
* @date 2017年7月12日 下午2:00:30
*/
public class ImportRawTest {

	public static void main(String[] args) {
		InputStream is = ImportRawTest.class.getClassLoader().getResourceAsStream("excel/order.xlsx");
		PoiSheetVo sheetVo = PoiImporter.importFirstSheetFrom(is);
		System.out.println("===>" + JSON.toJSONString(sheetVo.getContent()));
	}
	
}
