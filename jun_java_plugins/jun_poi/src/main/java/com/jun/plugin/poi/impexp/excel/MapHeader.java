package com.jun.plugin.poi.impexp.excel;

public class MapHeader {
	private String title;
	private String mapKey;
	private int width=3000;
	
	
	public MapHeader(String title, String mapKey, int width) {
		super();
		this.title = title;
		this.mapKey = mapKey;
		this.width = width;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMapKey() {
		return mapKey;
	}
	public void setMapKey(String mapKey) {
		this.mapKey = mapKey;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	
	

}
