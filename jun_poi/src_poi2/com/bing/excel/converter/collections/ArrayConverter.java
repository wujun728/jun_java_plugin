package com.bing.excel.converter.collections;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import com.bing.excel.converter.AbstractFieldConvertor;
import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.core.handler.ConverterHandler;
import com.bing.excel.exception.ConversionException;
import com.bing.excel.exception.IllegalEntityException;
import com.bing.excel.vo.OutValue;
import com.google.common.base.Strings;

/**
 * @author shizhongtao
 *
 * @date 2016-3-24
 * Description:  
 */
public class ArrayConverter extends AbstractFieldConvertor {

	
	
	private final String splitCharacter;
	public final static String SPACE_SPLIT=" ";
	public final static String SPACE_COMMA=",";
	public final static String SPACE_SEMICOLON=";";
	
	public ArrayConverter() {
		splitCharacter=SPACE_COMMA;
	}

	public ArrayConverter(String splitCharacter) {
		this.splitCharacter=splitCharacter;
	}

	@Override
	public boolean canConvert(Class<?> clz) {
		
		 return  clz.isArray();
		
	}


	@Override
	public OutValue toObject(Object source,ConverterHandler converterHandler) {
		if(source==null){
			return null;
		}
		Class<?> type = source.getClass().getComponentType();
		FieldValueConverter converter = converterHandler.getLocalConverter(type);
		if(converter==null){
			throw new ConversionException("can find the converter for type ["
							+ type + "]");
		}
		int length = Array.getLength(source);
		StringBuilder bd=new StringBuilder();
    	for(int i=0;i<length;i++){
    		Object object = Array.get(source, i);
    		OutValue value = converter.toObject(object, converterHandler);
    		bd.append(value.getValue());
    		if(i<length-1){
    			bd.append(splitCharacter);
    		}
    	}
    	return OutValue.stringValue(bd.toString());
	}

	@Override
	public Object fromString(String cell,ConverterHandler converterHandler,Type targetType) {
		if(Strings.isNullOrEmpty(cell)){
			return null;
		}
		if(targetType==null){
			return null;
		}
		Class  type=(Class) targetType;
		String[] splitArr = cell.split(splitCharacter);
		
		FieldValueConverter converter = converterHandler.getLocalConverter(type);
		if(converter==null){
			throw new ConversionException("can find the converter for type ["
							+ type + "]");
		}
		 Object array = Array.newInstance(type, splitArr.length);
		 for (int i = 0; i < splitArr.length; i++) {
			 Object object = converter.fromString(splitArr[i], converterHandler,type);
			 Array.set(array, i, object);
			}
		
		return array;
	}

}
