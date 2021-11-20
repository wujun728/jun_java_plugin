package com.jun.plugin.excelutils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Chen zheng you
 * CreateTime: 2018-12-05 15:24
 * Description:
 */
public class ExcelPojo {

    /**
     * 导出数据必填
     */
    private List<List<String[]>> dataLists;
    /**
     * sheet名称必填
     */
    private String[] sheetName;
    /**
     * 每个表格的大标题
     */
    private String[] labelName;
    /**
     * 页面响应
     */
    private HttpServletResponse response;
    /**
     * 忽略边框(默认是有边框)
     */
    private HashMap notBorderMap;
    /**
     * 自定义：单元格合并
     */
    private HashMap regionMap;
    /**
     * 自定义：对每个单元格自定义列宽
     */
    private HashMap columnMap;
    /**
     * 自定义：每一个单元格样式
     */
    private HashMap styles;
    /**
     * 自定义：固定表头
     */
    private HashMap paneMap;
    /**
     * 自定义：某一行样式
     */
    private HashMap rowStyles;
    /**
     * 自定义：某一列样式
     */
    private HashMap columnStyles;
    /**
     * 自定义：对每个单元格自定义下拉列表
     */
    private HashMap dropDownMap;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 导出本地路径
     */
    private String filePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<List<String[]>> getDataLists() {
        return dataLists;
    }

    public void setDataLists(List<List<String[]>> dataLists) {
        this.dataLists = dataLists;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public HashMap getNotBorderMap() {
        return notBorderMap;
    }

    public void setNotBorderMap(HashMap notBorderMap) {
        this.notBorderMap = notBorderMap;
    }

    public HashMap getRegionMap() {
        return regionMap;
    }

    public void setRegionMap(HashMap regionMap) {
        this.regionMap = regionMap;
    }

    public HashMap getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(HashMap columnMap) {
        this.columnMap = columnMap;
    }

    public HashMap getStyles() {
        return styles;
    }

    public void setStyles(HashMap styles) {
        this.styles = styles;
    }

    public HashMap getPaneMap() {
        return paneMap;
    }

    public void setPaneMap(HashMap paneMap) {
        this.paneMap = paneMap;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getSheetName() {
        return sheetName;
    }

    public void setSheetName(String[] sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getLabelName() {
        return labelName;
    }

    public void setLabelName(String[] labelName) {
        this.labelName = labelName;
    }

    public HashMap getRowStyles() {
        return rowStyles;
    }

    public void setRowStyles(HashMap rowStyles) {
        this.rowStyles = rowStyles;
    }

    public HashMap getColumnStyles() {
        return columnStyles;
    }

    public void setColumnStyles(HashMap columnStyles) {
        this.columnStyles = columnStyles;
    }

    public HashMap getDropDownMap() {
        return dropDownMap;
    }

    public void setDropDownMap(HashMap dropDownMap) {
        this.dropDownMap = dropDownMap;
    }
}
