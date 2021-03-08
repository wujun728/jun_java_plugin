package com.zh.springbooteasypoi.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@Slf4j
public class ExcelUtil {

    public static void exportExcel(String exportPath, Workbook workbook) throws IOException {
        @Cleanup FileOutputStream out = new FileOutputStream(exportPath);
        workbook.write(out);
    }

    public static void exportSimple03Excel(String exportPath, Class<?> modelClass, List<?> dataList) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(),modelClass,dataList);
        exportExcel(exportPath,workbook);
    }

    public static void exportSimple07Excel(String exportPath, Class<?> modelClass, List<?> dataList) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null,null, ExcelType.XSSF),modelClass,dataList);
        exportExcel(exportPath,workbook);
    }

    public static void exportExcelWithParam(String exportPath, ExportParams params, Class<?> modelClass, List<?> dataList) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(params,modelClass,dataList);
        exportExcel(exportPath,workbook);
    }

    public static void exportBigExcelWithParam(String exportPath, ExportParams params, Class<?> modelClass, List<?> dataList) throws IOException {
        params = Optional.ofNullable(params).map(e -> {
            e.setType(ExcelType.XSSF);
            return e;
        }).orElse(new ExportParams(null,null,ExcelType.XSSF));
        params.setType(ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportBigExcel(params,modelClass,dataList);
        exportExcel(exportPath,workbook);
    }

    public static <T> List<T> readExcel(String importPath, Class<?> modelClass){
        return ExcelImportUtil.importExcel(new File(importPath), modelClass, new ImportParams());
    }

    public static <T> List<T> readExcelByParam(String importPath, Class<?> modelClass,ImportParams params){
        return ExcelImportUtil.importExcel(new File(importPath), modelClass, params);
    }

}
