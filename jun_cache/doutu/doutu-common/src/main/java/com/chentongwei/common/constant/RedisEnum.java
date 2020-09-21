package com.chentongwei.common.constant;

/**
 * Redis的常量
 * 
 * @author TongWei.Chen 2017-5-21 20:19:24
 */
public enum RedisEnum {
	/**
	 * 图片分类key
	 */
	CATALOG("catalog"),
	/**
	 * 图片key
	 */
	PICTURE("picture_"),

	PICTURE_INDEX("index"),

	/**
	 * 图片压缩打包zip
	 */
	MKZIP("qiniu_zip")
	;

	//redis的key
    private String key;
	
	private RedisEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
