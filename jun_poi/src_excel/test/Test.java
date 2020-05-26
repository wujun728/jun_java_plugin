package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import excel.BeanExport;
import excel.ExportExcel;
import excel.MapExport;
import excel.MapHeader;


public class Test {

	public static void main(String[] arg) throws FileNotFoundException, IOException{

		testBean();
		testMap();
	}
	
	public static void testBean() throws FileNotFoundException, IOException{
		List<MyBean> l = new ArrayList<MyBean>();
		for(int i=0;i<100;i++){
			l.add(new MyBean());
		}
		
		
		BeanExport be = ExportExcel.BeanExport(MyBean.class);
		be.createBeanSheet("1月份", "1月份人员信息").addData(l);
		be.createBeanSheet("2月份","2月份人员信息").addData(l);
		be.writeFile("D:/bean人员信息8.xlsx");
	}
	
	public static void testMap () throws FileNotFoundException, IOException{
		List<MapHeader> l  = new ArrayList<MapHeader>();
		l.add(new MapHeader("姓名","name",5000));
		l.add(new MapHeader("年龄","age",4000));
		l.add(new MapHeader("生日","birthdate",3000));
		l.add(new MapHeader("地址","address",5000));
		l.add(new MapHeader("双精度","d",4000));
		l.add(new MapHeader("float","f",6000));
		
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		for(int i=0;i<100;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name","闪电球");
			map.put("age",100);
			map.put("birthdate",new Date());
			map.put("address","北京市广东省AAA号123楼!");
			map.put("d",22.222d);
			map.put("f",295.22f);
			lm.add(map);
		}
	
		
		
		MapExport me  = ExportExcel.mapExport(l);
		me.createMapSheel("1月份","广东省人员信息").addData(lm);
		me.createMapSheel("2月份", "北京市人员信息").addData(lm);
		me.writeFile("E:/map人员信息9.xlsx");
		
		
	}
}
