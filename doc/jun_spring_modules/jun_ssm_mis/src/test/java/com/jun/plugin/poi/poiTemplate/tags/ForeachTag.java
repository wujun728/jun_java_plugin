package com.jun.plugin.poi.poiTemplate.tags;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.jun.plugin.poi.PoiTemplate;
import com.jun.plugin.poi.poiTemplate.Tag;
import com.jun.plugin.poi.poiTemplate.TempCell;

/**
 * 集合循环处理类
 * @author Wujun
 *
 */
public class ForeachTag extends Tag{
	
	private static final String KEY = "#foreach";
	
	@Override
	public boolean hasEnd() {
		return true;
	}

	@Override
	public int parseTag(PoiTemplate temp, int index) {
		List<TempCell> cells = temp.tempCells;
		TempCell tagCell = cells.get(index);
		tagCell.isHandled = true;
		int endIndex = tagCell.endIndex;
		String tagStr = (String)cells.get(index).value;
		if(endIndex != -1){
			boolean isTagSameLine = isSameLine(cells, index);
			boolean isEndSameLine = isSameLine(cells, endIndex);
			TempCell endCell = cells.get(endIndex);
			endCell.isHandled = true;
			int between = 0;
			if(!isTagSameLine) {
				between = endCell.row - cells.get(index).row - 1;
				temp.moveRow--;
			}else{
				between = endCell.row - cells.get(index).row;
			}
			Collection<?> collection = null;
			String[] tag = tagStr.split(" ");
			if(tag.length == 4){
				String item = tag[1];
				String el = tag[3];
				collection = (Collection<?>) temp.getEl(el);
				Iterator<?> iterator = temp.getIterator(collection);
				int dataIndex = 0;
				if (null != iterator) {
					while (iterator.hasNext()) {
						temp.addValue(item, iterator.next());
						temp.addValue(item + "Index", dataIndex ++);
						temp.addValue(item + "Count", dataIndex);
						temp.addValue(item + "Odd",dataIndex%2!=0);
						for(int i = index + 1; i < endIndex; i ++){
							TempCell c = cells.get(i);
							if(c.isTag){
								i = c.tag.parseTag(temp, i);
							}else{
								if(c.value != null){
									temp.setValue(c);
								}
							}
						}
						temp.moveRow += between;
					}
					temp.moveRow -= between;
					for(int i = index + 1; i < endIndex; i ++){
						TempCell c = cells.get(i);
						c.isHandled = true;
					}
				}
				if(!isEndSameLine) temp.moveRow --;
			}
		}
		return endIndex;
	}

	@Override
	public String getTagName() {
		return KEY;
	}
	
}
