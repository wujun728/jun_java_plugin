package excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class MapSheel extends BaseSheel{

	private List<MapHeader> map;
	private List<String> keyList;
	protected MapSheel(SXSSFWorkbook wb, String name,
			Map<String, CellStyle> styles) {
		super(wb, name, styles);
		// TODO Auto-generated constructor stub
	}
	
	public MapSheel(SXSSFWorkbook wb,Map<String, CellStyle> styles,String name,String title,List<MapHeader> lh)
	{
		this(wb, name, styles);
		init(lh, title);
	}
	
	public MapSheel(SXSSFWorkbook wb,Map<String, CellStyle> styles,String name,String title,List<MapHeader> lh,List<String> headerList,int[] width,List<String> keyList){
		this(wb, name, styles);
		this.keyList = keyList;
		this.map = lh;
		initialize(title, headerList, width);
	}
	
	
	public MapSheel addData(List<Map<String,Object>> data){
		for(int i = 0;i< data.size(); i++){
			Row row = addRow();
			for(int j=0;j<keyList.size();j++){
				addCell(row, j,data.get(i).get(keyList.get(j)));
			}
		}
		
		return this;
	}
	
	private void init(List<MapHeader> lh,String title){
		List<String> headerList = new ArrayList<String>();
		int[] width = new int[lh.size()];
		for(int i=0;i<lh.size();i++){
			MapHeader mh= lh.get(i);
			headerList.add(mh.getTitle());
			keyList.add(mh.getMapKey());
			width[i] = mh.getWidth();
		}
		initialize(title, headerList, width);
		
	}

}
