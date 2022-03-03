package com.jun.excel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.excel.excelTools.JsGridReportBase;
import com.jun.excel.excelTools.TableData;
import com.jun.excel.module.People;
import com.jun.plugin.poi.ExcelUtil2;

@Controller
@RequestMapping("/export.do")
@SuppressWarnings("unchecked")
public class ExportController {

	/**
	 * 数组数据样例
	 * @return
	 */
	private List<Object[]> getData(){
		List<Object[]> list = new ArrayList<Object[]>();
		for(int i=1;i<100;i++){
			list.add(new Object[]{i,"童鞋"+i,"你猜","K型","阴阳路"+i+"号"});
		}
		return list;
	}
	
	/**
	 * Bean数据样例
	 * @return
	 */
	private List<People> getBeanData(){
		List<People> list = new ArrayList<People>();
		for(int i=1;i<100;i++){
			People p = new People();
			p.setCode(i);
			p.setName("童鞋"+i);
			p.setAddr("阴阳路"+i+"号");
			p.setBlood("K型");
			p.setSex("你猜");
			list.add(p);
		}
		return list;
	}

	/**
	 * Map数据样例
	 * @return
	 */
	private List<Map> getMapData(){
		List<Map> list = new ArrayList<Map>();
		for(int i=1;i<100;i++){
			Map map = new HashMap();
			map.put("code", i);
			map.put("name", "童鞋"+i);
			map.put("sex", "你猜");
			map.put("blood", "K型");
			map.put("addr", "阴阳路"+i+"号");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 表格数据查询
	 * @return
	 */
	@RequestMapping(params = "method=loadData")
	@ResponseBody
	public List<People> loadData(){
		return getBeanData();
	}
	
	/**
	 * 普通Excel导出，获取的数据格式是List<JavaBean>
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "method=exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/msexcel;charset=GBK");
        
        List<People> list = getBeanData();//获取数据
        
        String title = "普通Excel表";
        String[] hearders = new String[] {"编号", "姓名", "性别", "血型", "地址"};//表头数组
        String[] fields = new String[] {"code", "name", "sex", "blood", "addr"};//People对象属性数组
        TableData td = ExcelUtil2.createTableData(list, ExcelUtil2.createTableHeader(hearders),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, "admin", td);
	}

	/**
	 * 合并列表头Excel导出，获取的数据格式是List<Map>
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "method=spanExport")
	public void spanExport(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/msexcel;charset=GBK");
        List<Map> list = getMapData();//获取数据

        String title = "合并表头Excel表";
        String[] parents = new String[] {"", "基本信息", ""};//父表头数组
        String[][] children = new String[][] {
        		new String[]{"编号"},
        		new String[]{"姓名", "性别", "血型"}, 
        		new String[]{"地址"}};//子表头数组
        String[] fields = new String[] {"code", "name", "sex", "blood", "addr"};//People对象属性数组
        TableData td = ExcelUtil2.createTableData(list, ExcelUtil2.createTableHeader(parents,children),fields);
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, "admin", td);
	}

	/**
	 * 多Sheet Excel导出，获取的数据格式是List<Object[]>
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(params = "method=sheetsExport")
	public void exportSheets(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/msexcel;charset=GBK");
        List<Object[]> list = getData();
        
        List<TableData> tds = new ArrayList<TableData>();
        
        //Sheet1
        String[] parents = new String[] {"", "基本信息", ""};//父表头数组
        String[][] children = new String[][] {
        		new String[]{"编号"},
        		new String[]{"姓名", "性别", "血型"}, 
        		new String[]{"地址"}};//子表头数组
        TableData td = ExcelUtil2.createTableData(list, ExcelUtil2.createTableHeader(parents,children),null);
        td.setSheetTitle("合并表头示例");
        tds.add(td);

        //Sheet2
        String[] hearders = new String[] {"编号", "姓名", "性别", "血型", "地址"};//表头数组
        td = ExcelUtil2.createTableData(list, ExcelUtil2.createTableHeader(hearders),null);
        td.setSheetTitle("普通表头示例");
        tds.add(td);

        String title = "多Sheet表";
        JsGridReportBase report = new JsGridReportBase(request, response);
        report.exportToExcel(title, "admin", tds);
	
	}
}
