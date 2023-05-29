package com.sanri.excel.poi.converter;

public class DefaultBooleanStringConverter implements ExcelConverter<Boolean,String> {
    @Override
    public String convert(Boolean source) {
        return String.valueOf(source);
    }
}
