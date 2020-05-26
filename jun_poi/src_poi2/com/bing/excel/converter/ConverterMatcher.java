package com.bing.excel.converter;


/**
 * <p>Title: ConverterMatcher</p>
 * <p>Description: ConverterMatcher is the base interface of any converter</p>
 * <p>Company: bing</p> 
 * 
 * @author zhongtao.shi
 * @date 2015-12-14
  */
public interface ConverterMatcher {

    /**
     * Determines whether the converter can marshall a particular type.
     * @param clz the Class representing the object type to be converted
     */
    boolean canConvert(Class<?> clz);

}