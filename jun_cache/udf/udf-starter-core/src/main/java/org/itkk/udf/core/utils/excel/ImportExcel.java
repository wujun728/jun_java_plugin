/**
 * ImportExcel.java
 * Created at 2016-03-01
 * Created by Administrator
 * Copyright (C) 2016 LLSFW, All rights reserved.
 */

package org.itkk.udf.core.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 导入excel(2003+)
 *
 * @author Administrator
 */
public class ImportExcel {

    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ImportExcel.class);

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 构造函数
     *
     * @param fileName  文件名称(完整路径)
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws IOException 异常
     */
    public ImportExcel(String fileName, int headerNum) throws IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * 构造函数
     *
     * @param file      文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws IOException 异常
     */
    public ImportExcel(File file, int headerNum) throws IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     *
     * @param fileName   文件名称(完整路径)
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException 异常
     */
    public ImportExcel(String fileName, int headerNum, int sheetIndex) throws IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param file       导入文件对象
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException 异常
     */
    public ImportExcel(File file, int headerNum, int sheetIndex) throws IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param multipartFile 上传文件对象
     * @param headerNum     标题行号，数据行号=标题行号+1
     * @param sheetIndex    工作表编号
     * @throws IOException 异常
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex) throws IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     *
     * @param fileName   文件名称(完整路径)
     * @param is         输入流
     * @param headerNum  标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException 异常
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex) throws IOException {
        if (StringUtils.isBlank(fileName)) {
            throw new IOException("导入文档为空!");
        } else if (fileName.toLowerCase().endsWith("xls")) {
            this.wb = new HSSFWorkbook(is);
        } else if (fileName.toLowerCase().endsWith("xlsx")) {
            this.wb = new XSSFWorkbook(is);
        } else {
            throw new IOException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets() < sheetIndex) {
            throw new IOException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        LOG.debug("Initialize success.");
    }

    /**
     * 获取行对象
     *
     * @param rownum 行号
     * @return 行
     */
    public Row getRow(int rownum) {
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     *
     * @return 行号
     */
    public int getDataRowNum() {
        return this.headerNum + 1;
    }

    /**
     * 获取最后一个数据行号
     *
     * @return 行号
     */
    public int getLastDataRowNum() {
        return this.sheet.getLastRowNum() + this.headerNum;
    }

    /**
     * 获取最后一个列号
     *
     * @return 列号
     */
    public int getLastCellNum() {
        return this.getRow(this.headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        Object val = "";
        Cell cell = row.getCell(column);
        if (cell != null) {
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    val = cell.getNumericCellValue();
                    break;
                case STRING:
                    val = cell.getStringCellValue();
                    break;
                case FORMULA:
                    val = cell.getCellFormula();
                    break;
                case BOOLEAN:
                    val = cell.getBooleanCellValue();
                    break;
                case ERROR:
                    val = cell.getErrorCellValue();
                    break;
                default:
                    val = null;
                    break;
            }
        }
        return val;
    }
}
