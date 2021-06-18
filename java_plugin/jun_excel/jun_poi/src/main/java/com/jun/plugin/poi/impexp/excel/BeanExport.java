package com.jun.plugin.poi.impexp.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.jun.plugin.poi.impexp.excel.annotation.ExcelField;

public class BeanExport extends BaseExportExcel{
	private static Logger log = LoggerFactory.getLogger(BeanExport.class);
	private List<Object[]> annotationList = new ArrayList<Object[]>();
 	
	/**
	 * 样式列表
	 */
	
	
	
	

	/**
	 * 
	 * @param cls //工作本默认的 class依据,如果调用createBeanSheet不传入class则使用此默认class
	 * @param type 1,导出数据,2导出模板
	 * @param groups
	 */
	public BeanExport(Class<?> cls, int type, int... groups){
		this.wb = new SXSSFWorkbook(500);
		this.styles = createStyles(wb);
		init(cls, type, groups);
		log.debug("创建成功");
	}
	
	/**
	 * 
	 * @param cls //工作本默认的 class依据,如果调用createBeanSheet不传入class则使用此默认class
	 * 默认到处数据和使用默认分�?
	 */
	public BeanExport(Class<?> cls){
		this(cls,1);
	}
	
	/**
	 * 
	 * @param name
	 * @param title
	 * @param headerList
	 * @param width
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,List<String> headerList,int[] width){
		return new BeanSheel(title, headerList, width, this.wb, name, this.styles);
	}
	
	/**
	 * 
	 * @param name
	 * @param title
	 * @param headers
	 * @param width
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,String[] headers,int[] width){
		return new BeanSheel(title,Lists.newArrayList(headers), width, this.wb, name, this.styles);
	}
	
	/**
	 * 
	 * @param name
	 * @param title
	 * @param cls
	 * @param type
	 * @param groups
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,Class<?> cls, int type,int... groups){
		return new BeanSheel(title, cls, type, this.wb, name, this.styles, groups);
	}
	
	/**
	 * 
	 * @param name
	 * @param title
	 * @param cls
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title,Class<?> cls){
		return new BeanSheel(title, cls,1, this.wb, name, this.styles);
	}
	
	/**
	 * 
	 * @param name
	 * @param title
	 * @return
	 */
	public BeanSheel createBeanSheet(String name,String title){
		return new BeanSheel(this.annotationList,this.headerList, this.width, name, title, wb, styles);
	}
	
	




	
	private void init(Class<?> cls, int type, int... groups){
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}

		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		// Initialize
		this.width = new int[annotationList.size()];
		for(int i=0;i<annotationList.size();i++){
			ExcelField ef = (ExcelField)annotationList.get(i)[0];
			String t = ef.title();
			// 如果是导出数据，则去掉注�?
			if (type==1){
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length==2){
					t = ss[0];
				}
			}
		    this.width[i]=ef.width();
			this.headerList.add(t);
		}
	}
	
	

	

}
