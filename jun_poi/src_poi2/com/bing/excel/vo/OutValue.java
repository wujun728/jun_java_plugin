package com.bing.excel.vo;

public class OutValue {
	public enum OutType {
		INTEGER, LONG, DOUBLE, STRING, DATE, UNDEFINED
	}
	
	private OutType outType;
	private Object value;
	
	
	
	public OutValue(OutType outType, Object value) {
		super();
		this.outType = outType;
		this.value = value;
	}
	public static OutValue intValue(Object obj){
		return new OutValue(OutType.INTEGER,obj);
	}
	public static OutValue doubleValue(Object obj){
		return new OutValue(OutType.DOUBLE,obj);
	}
	public static OutValue longValue(Object obj){
		return new OutValue(OutType.LONG,obj);
	}
	public static OutValue stringValue(Object obj){
		return new OutValue(OutType.STRING,obj);
	}
	public static OutValue dateValue(Object obj){
		return new OutValue(OutType.DATE,obj);
	}
	public OutType getOutType() {
		return outType;
	}
	public void setOutType(OutType outType) {
		this.outType = outType;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
