package com.sanri.excel.poi.converter;

/**
 * 类型转换器只针对于写 Excel 模式
 * @param <S>
 * @param <T>
 */
public interface ExcelConverter<S,T> {
    T convert(S source);
}
