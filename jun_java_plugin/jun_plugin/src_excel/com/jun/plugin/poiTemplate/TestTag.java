package com.jun.plugin.poiTemplate;

import com.jun.plugin.poiTemplate.PoiTemplate;
import com.jun.plugin.poiTemplate.Tag;
import com.jun.plugin.poiTemplate.TempCell;

/**
 * 测试标签
 * @author Administrator
 *
 */
public class TestTag extends Tag{
	
	private static final String KEY_TEST = "#test";

	@Override
	public boolean hasEnd() {
		return false;
	}

	@Override
	public int parseTag(PoiTemplate temp, int index) {
		TempCell tempCell = temp.tempCells.get(index);
		tempCell.value = "Hello Test111";
		temp.setValue(tempCell);
		tempCell.isHandled = true;
		return index;
	}

	@Override
	public String getTagName() {
		return KEY_TEST;
	}
	
}
