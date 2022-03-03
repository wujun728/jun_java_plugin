package com.jun.plugin.poi.poiTemplate;

import java.util.List;

import com.jun.plugin.poi.PoiTemplate;
/**
 * 标签抽象类，定义一些标签必须的方法
 * @author Wujun
 *
 */
public abstract class Tag {

	/**
	 * 是否有结束标签
	 * @return
	 */
	public abstract boolean hasEnd();

	/**
	 * 处理标签
	 * @param temp 模板对象
	 * @param index 最后处理完成的单元格序号
	 * @return
	 */
	public abstract int parseTag(PoiTemplate temp, int index);
	
	/**
	 * 获取标签名
	 * @return
	 */
	public abstract String getTagName();
	
	/**
	 * 获取结束标签名
	 * @return
	 */
	public String getEndKey(){
		return "#end";
	}
	
	/**
	 * 获取标签结束标签的序号
	 * @param cells 单元格模板集合
	 * @param fromIndex 从
	 * @param endkey 结束标签名
	 * @return
	 */
	public int getEndCell(List<TempCell> cells, int fromIndex, String endkey){
		for(int i = fromIndex + 1; i < cells.size(); i++){
			TempCell tc = cells.get(i);
			if(tc.isTag){
				Tag tag = tc.tag;
				if(tag.hasEnd()){
					i = tag.getEndCell(cells, i, tag.getEndKey());
				}else{
					continue;
				}
			}else{
				Object value = tc.value;
				if(value instanceof String){
					String s = (String) value;
					if(s.equals(endkey)){
						return i;
					}
				}
			}
			
		}
		return -1;
	}
	
	/**
	 * 是否在标签所在行有其他单元格有值
	 * @param cells 单元格模板集合
	 * @param tagIndex 标签所在的序号
	 * @return
	 */
	public boolean isSameLine(List<TempCell> cells, int tagIndex){
		TempCell tagCell = cells.get(tagIndex);
		for(int i = tagIndex + 1; i < cells.size(); i++){
			TempCell cell = cells.get(i);
			if(cell.row == tagCell.row && cell.value != null){
				return true;
			}
			if(cell.row > tagCell.row){
				return false;
			}
		}
		return false;
	}
	
}
