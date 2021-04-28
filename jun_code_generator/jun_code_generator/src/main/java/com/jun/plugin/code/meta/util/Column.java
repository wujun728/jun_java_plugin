package com.jun.plugin.code.meta.util;

import lombok.Data;

/**
 */
@Data
public class Column {
	private String columnName;
    private String fieldName;//name
    private String fieldClass;//fieldType
    private String fieldComment;//remarks desc
    private String length;
    private String nullable;
    
    //属性类型
    private String type;
    //类型-只有名字
    private String simpleType;
    //属性名字
    private String name;
    //首字母大写属性名字
    private String upperName;
    //属性描述
    private String desc;
    //是否是主键
    private Boolean id;
    //列名
    private String column;
    //是否自增  YES自增，NO：非自增
    private String identity;
     
    
			
	 
	
}
