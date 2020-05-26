package excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class MapExport extends BaseExportExcel{

	private List<String> keyList = new ArrayList<String>();
	private List<MapHeader> map;
	
	public MapExport(List<MapHeader> map){
		this.wb = new SXSSFWorkbook(500);
		this.styles = createStyles(wb);
		init(map);
	}
	
	public MapSheel createMapSheel(String name,String title,List<MapHeader> lh){
		return new MapSheel(this.wb, this.styles, name, title, lh);
	}
	
	public MapSheel createMapSheel(String name,String title){
		return new MapSheel(this.wb, this.styles, name, title,this.map, this.headerList, this.width, this.keyList);
	}
	
	private void init(List<MapHeader> lh){
		this.width = new int[lh.size()];
		for(int i=0;i<lh.size();i++){
			MapHeader mh= lh.get(i);
			this.headerList.add(mh.getTitle());
			this.keyList.add(mh.getMapKey());
			this.width[i] = mh.getWidth();
		}
		
	}

}
