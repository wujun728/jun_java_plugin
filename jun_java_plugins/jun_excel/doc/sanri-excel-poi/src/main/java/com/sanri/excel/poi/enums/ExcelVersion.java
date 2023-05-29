package com.sanri.excel.poi.enums;

/**
 * 
 * 作者:sanri <br/>
 * 时间:2017-8-12下午1:37:27<br/>
 * 功能:excel 的版本,支持 2007 和 2003 版本 <br/>
 */
public enum ExcelVersion {
	EXCEL2007(2007),EXCEL2003(2003);
	
	private int version;
	ExcelVersion(int version){
		this.version = version;
	}
	
	public int getVersion(int version) {
		return this.version;
	}
	
}
