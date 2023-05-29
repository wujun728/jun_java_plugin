package com.sanri.excel.poi.converter;

/**
 * 不做任何转换
 */
public class NULLConverter implements ExcelConverter {
    @Override
    public Object convert(Object source) {
        return source;
    }
}
