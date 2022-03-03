package com.jun.plugin.poi.poiTemplate;

import com.jun.plugin.poi.PoiTemplate;

/**
 * 测试标签
 * @author Wujun
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
