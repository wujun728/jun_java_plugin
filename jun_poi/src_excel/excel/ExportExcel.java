package excel;

import java.util.List;

public class ExportExcel {
	
	public static BeanExport BeanExport(Class<?> cls){
		return new BeanExport(cls);
	}
	
	public static BeanExport BeanExport(Class<?> cls, int type, int... groups){
		return new BeanExport(cls, type, groups);
	}
	
	public static MapExport mapExport(List<MapHeader> map){
		return new MapExport(map);
	}

}
