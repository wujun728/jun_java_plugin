package com.kvn.poi.exp.processor;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;

import com.google.common.collect.Lists;

public class RowProcessorStrategy {
	
	public static final List<RowProcessor> processors = Lists.newArrayList();
	static{
		processors.add(new ForeachRowProcessor());
	}
	
	public static RowProcessor getRowProcessor(XSSFRow row){
		for(RowProcessor processor : processors){
			int flag = processor.support(row);
			if(flag >= 0){
				return processor;
			}
		}
		
		return DefaultRowProcessor.SINGLE.INSTANCE;
	}
	
}
