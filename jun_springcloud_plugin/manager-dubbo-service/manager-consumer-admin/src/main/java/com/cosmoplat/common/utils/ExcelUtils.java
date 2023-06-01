package com.cosmoplat.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cosmoplat.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wenbin
 * @date: 2020/9/3
 * Description: 导出excel
 */
@Slf4j
public class ExcelUtils {
    private ExcelUtils() {
        throw new IllegalStateException("Utility class");
    }


    private static String exceptionStr = "异常：{}";
    private static String fileNotBlank = "excel文件不能为空！";

    /**
     * 导出excel
     *
     * @param list           list
     * @param title          title
     * @param sheetName      sheetName
     * @param pojoClass      pojoClass
     * @param fileName       fileName
     * @param isCreateHeader isCreateHeader
     * @param response       response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出excel
     *
     * @param list      list
     * @param title     title
     * @param sheetName sheetName
     * @param pojoClass pojoClass
     * @param fileName  fileName
     * @param response  response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出excle
     *
     * @param list        list
     * @param title       title
     * @param sheetName   sheetName
     * @param pojoClass   pojoClass
     * @param fileName    fileName
     * @param response    response
     * @param dictHandler dictHandler
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, IExcelDictHandler dictHandler, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setDictHandler(dictHandler);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    public static void multipleExport(List<Map<String, Object>> sheetsList, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error(exceptionStr, e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return Collections.emptyList();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.error(fileNotBlank);
            throw new BusinessException(fileNotBlank);
        } catch (Exception e) {
            log.error(exceptionStr, e.getMessage(), e);
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            log.error("导入文件不能为空");
            throw new BusinessException("文件不能为空!");
        }
        String fileOrgName = file.getOriginalFilename();
        if (fileOrgName != null && fileOrgName.lastIndexOf(".") > 0) {
            String suffix = fileOrgName.substring(fileOrgName.lastIndexOf(".") + 1);
            if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
                log.error("导入文件类型必须是excel");
                throw new BusinessException("excel文件类型有误");
            }
        } else {
            log.error("导入文件类型必须是excel-");
            throw new BusinessException("excel文件类型有误!");
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.error(fileNotBlank);
            throw new BusinessException(fileNotBlank);
        } catch (Exception e) {
            log.error(exceptionStr, e.getMessage(), e);
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Integer startSheetIndex, Class<T> pojoClass) {
        if (file == null) {
            log.error("导入文件不能为空");
            throw new BusinessException("文件不能为空!");
        }
        String fileOrgName = file.getOriginalFilename();
        if (fileOrgName != null && fileOrgName.lastIndexOf(".") > 0) {
            String suffix = fileOrgName.substring(fileOrgName.lastIndexOf(".") + 1);
            if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
                log.error("导入文件类型必须是excel");
                throw new BusinessException("excel文件类型有误");
            }
        } else {
            log.error("导入文件类型必须是excel-");
            throw new BusinessException("excel文件类型有误!");
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setStartSheetIndex(startSheetIndex);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.error(fileNotBlank);
            throw new BusinessException(fileNotBlank);
        } catch (Exception e) {
            log.error(exceptionStr, e.getMessage(), e);
        }
        return list;
    }
}
