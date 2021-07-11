package com.cl.search.model;

public enum ResultType {
	
	EMPYTY(0),			//无结果
	NORMAL(1),			//正常
	PINYIN(2),			//关键字拼音查询
	ANALYZER(3);		//关键字中文分词查询

	private int type;

	private ResultType(int type){
		this.type = type;
	}
	
	public int getResultType(){
		return type;
	}
}
