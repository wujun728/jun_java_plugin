package com.jun.plugin.poi.impexp.excel;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.jun.plugin.poi.impexp.excel.annotation.ExcelField;


public  class BeanSheel extends BaseSheel{
	private static Logger log = LoggerFactory.getLogger(BeanSheel.class);
	
	/**
	 * 数据LIST
	 */
	private List<Object> dataList;
	
	List<Object[]> annotationList =null;

	

	/**
	 * 构�?函数
	 * @param title 表格标题，传“空值�?，表示无标题
	 * @param headerList 表头列表
	 * @return 
	 */
	public BeanSheel(String title, List<String> headerList,int[] width,SXSSFWorkbook wb,String name,Map<String, CellStyle> styles) {
		super(wb, name, styles);
		initialize(title, headerList,width);
	}
	
	
	/**
	 * 构�?函数
	 * @param title 表格标题，传“空值�?，表示无标题
	 * @param cls 实体对象，�?过annotation.ExportField获取标题
	 * @param type 导出类型�?:导出数据�?：导出模板）
	 * @param groups 导入分组
	 * @return 
	 */
	public BeanSheel(String title, Class<?> cls, int type,SXSSFWorkbook wb,String name,Map<String, CellStyle> styles ,int... groups) {
		super(wb, name, styles);
		init(title, cls, type, groups);
	}
	
	public BeanSheel(List<Object[]> annotationList,List<String> headerList,int[] width,String name,String title,SXSSFWorkbook wb,Map<String, CellStyle> styles) {
		super(wb, name, styles);
		this.annotationList = annotationList;
		initialize(title, headerList,width);
	}
	/**
	 * 初始�?
	 * @param title 表格标题，传“空值�?，表示无标题
	 * @param cls 实体对象，�?过annotation.ExportField获取标题
	 * @param type 导出类型�?:导出数据�?：导出模板）
	 * @param groups 导入分组
	 * @return 
	 */
	private void init(String title, Class<?> cls, int type, int... groups){
		// Get annotation field 
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
		List<String> headerList = Lists.newArrayList();
		int[] width = new int[annotationList.size()];
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
		    width[i]=ef.width();
			headerList.add(t);
		}
		initialize(title, headerList,width);
	}
	
	
	

	public <E> BeanSheel addData(List<E> list){
		for (E e : list){
			
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				ExcelField ef = (ExcelField)os[0];
				Object val = null;
				// 获取�?
				try{
					if (StringUtils.isNotBlank(ef.value())){
						val = Reflections.invokeGetter(e, ef.value());
					}else{
						if (os[1] instanceof Field){
							val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
						}
//						else if (os[1] instanceof Method){//现在只支持注解属性了�?
//							val = Reflections.invokeMethod(e, ((Method)os[1]).getName(), new Class[] {}, new Object[] {});
//						}
					}
					// 如果是字典类型就获取字典的label
//					if (StringUtils.isNotBlank(ef.dictType())){
//			//			val = DictUtils.getDictLabel(val==null?"":val.toString(), ef.dictType(), "");
//					}
				}catch(Exception ex) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				sb.append(val + ", ");
			}
			log.debug("成功写入: ["+row.getRowNum()+"] "+sb.toString());
		}
		return this;
	}

	
	
	
	
}