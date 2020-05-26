package com.bing.excel.converter;

import java.util.List;

import com.bing.excel.mapper.ExcelConverterMapperHandler;
import com.bing.excel.vo.CellKV;


public interface HeaderReflectConverter {
   List<CellKV<String>> getHeader(ExcelConverterMapperHandler handler);
}
