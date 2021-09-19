package com.jun.plugin.poi.test.excel.converter;

import java.util.List;

import com.jun.plugin.poi.test.excel.mapper.ExcelConverterMapperHandler;
import com.jun.plugin.poi.test.excel.vo.CellKV;


public interface HeaderReflectConverter {
   List<CellKV<String>> getHeader(ExcelConverterMapperHandler handler);
}
