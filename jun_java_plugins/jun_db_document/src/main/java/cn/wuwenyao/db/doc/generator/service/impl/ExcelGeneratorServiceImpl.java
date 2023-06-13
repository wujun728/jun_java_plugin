package cn.wuwenyao.db.doc.generator.service.impl;

import cn.wuwenyao.db.doc.generator.entity.TableFieldInfo;
import cn.wuwenyao.db.doc.generator.entity.TableInfo;
import cn.wuwenyao.db.doc.generator.entity.TableKeyInfo;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/***
 * 生成文档服务-excel实现
 *
 * @author wwy
 *
 */
public final class ExcelGeneratorServiceImpl extends AbstractGeneratorServiceImpl {

    /***
     * excel对象
     */
    private HSSFWorkbook workbook;

    /***
     * 标题样式
     */
    private HSSFCellStyle titleCellStyle;

    /***
     * 一般样式
     */
    private HSSFCellStyle simpleCellStyle;

    /***
     * 链接样式
     */
    private HSSFCellStyle linkCellStyle;

    /***
     * 目录sheet的名称
     */
    private String indexSheetName = "目录";


    public ExcelGeneratorServiceImpl() {
        workbook = new HSSFWorkbook();
        initCellStyle();
    }

    /***
     * 初始化样式
     */
    public void initCellStyle() {
        // 创建标题样式
        titleCellStyle = workbook.createCellStyle();
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("微软雅黑");
        titleFont.setBold(false);
        titleFont.setFontHeightInPoints((short) 20);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.CORAL.getIndex());

        // 创建一般样式
        simpleCellStyle = workbook.createCellStyle();
        simpleCellStyle.setBorderLeft(BorderStyle.THIN);
        simpleCellStyle.setBorderRight(BorderStyle.THIN);
        simpleCellStyle.setBorderTop(BorderStyle.THIN);
        simpleCellStyle.setBorderBottom(BorderStyle.THIN);
        HSSFFont simpleFont = workbook.createFont();
        simpleFont.setFontName("微软雅黑");
        simpleFont.setFontHeightInPoints((short) 16);
        simpleCellStyle.setFont(simpleFont);

        // 创建超链接样式
        linkCellStyle = workbook.createCellStyle();
        linkCellStyle.setBorderLeft(BorderStyle.THIN);
        linkCellStyle.setBorderRight(BorderStyle.THIN);
        linkCellStyle.setBorderTop(BorderStyle.THIN);
        linkCellStyle.setBorderBottom(BorderStyle.THIN);
        HSSFFont linkFont = workbook.createFont();
        linkFont.setFontName("YAHEI");
        linkFont.setFontHeightInPoints((short) 16);
        linkFont.setItalic(true);
        linkCellStyle.setFont(linkFont);


    }


    @Override
    public void generateDbDoc() throws Exception {
        String databaseName = dbInfoDao.databaseName();
        List<TableInfo> tableInfos = dbInfoDao.tableInfoList();
        // 生成文件
        File file = createFile("xls");
        // 初始化样式
        initCellStyle();
        // 创建目录sheet
        createIndexSheet();
        // 创建各种表格sheet
        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            createTableSheet(tableInfo);
        }
        FileOutputStream exportXls = new FileOutputStream(file);
        workbook.write(exportXls);
        workbook.close();
        exportXls.close();
    }

    private Integer emptyRows(HSSFSheet sheet, Integer rowIndex, Integer rows) {
        for (int i = 0; i < rows; i++) {
            sheet.createRow(rowIndex++);
        }
        return rowIndex;
    }

    private void createIndexSheet() {
        String databaseName = dbInfoDao.databaseName();
        List<TableInfo> tableInfos = dbInfoDao.tableInfoList();
        CreationHelper createHelper = workbook.getCreationHelper();
        // 创建目录sheet
        HSSFSheet indexSheet = workbook.createSheet(indexSheetName);
        indexSheet.setActive(true);
        indexSheet.setDefaultColumnWidth(40);
        // 创建数据库名称row
        HSSFRow dbNameRow = indexSheet.createRow(0);
        createCell(0, dbNameRow, "数据库名称:" + databaseName, simpleCellStyle);

        HSSFRow tablesRow = indexSheet.createRow(2);
        createCell(0, tablesRow, "表", simpleCellStyle);
        // 创建返回目录sheet的超链接
        Hyperlink toIndexLink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        toIndexLink.setAddress(String.format("'%s'!A1", indexSheetName));

        // 创建表目录
        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            // 目录sheet创建一个cell超链接到表格Sheet
            HSSFRow indexRow = indexSheet.createRow(i + 3);
            HSSFCell indexRowCell = createCell(0, indexRow, tableInfo.getTableName(), linkCellStyle);
            Hyperlink toTableLink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
            toTableLink.setAddress(String.format("'%s'!A1", tableInfo.getTableName()));
            indexRowCell.setHyperlink(toTableLink);
            createCell(1, indexRow, tableInfo.getTableRemark(), linkCellStyle);
        }
    }

    private void createTableSheet(TableInfo tableInfo) {
        CreationHelper createHelper = workbook.getCreationHelper();
        Hyperlink toIndexLink = createHelper.createHyperlink(HyperlinkType.DOCUMENT);
        toIndexLink.setAddress(String.format("'%s'!A1", indexSheetName));
        // 创建表格sheet,sheet名称长度最大32个字符
        HSSFSheet tableSheet = workbook.createSheet(tableInfo.getTableName());
        tableSheet.setDefaultColumnWidth(45);
        Integer rowIndex = new Integer(3);
        // 创建第一行，包含返回首页的按钮和表名
        HSSFRow tableSheetIndexRow = tableSheet.createRow(rowIndex++);
        HSSFCell tableSheetIndexRowFirstCell = createCell(0, tableSheetIndexRow, "返回目录", linkCellStyle);
        tableSheetIndexRowFirstCell.setHyperlink(toIndexLink);

        // 创建第二行，显示表名
        HSSFRow tableSheetTableNameRow = tableSheet.createRow(rowIndex++);
        createCell(0, tableSheetTableNameRow, "表名：" + tableInfo.getTableName(), simpleCellStyle);

        // 创建第三行，注释
        HSSFRow tableSheetTableCommentRow = tableSheet.createRow(rowIndex++);
        createCell(0, tableSheetTableCommentRow, "注释：" + tableInfo.getTableRemark(), simpleCellStyle);

        // 创建field表格
        rowIndex = createFieldTable(tableInfo, tableSheet, rowIndex);
        // 空三行
        rowIndex = emptyRows(tableSheet, rowIndex, 3);
        // 创建key表格
        rowIndex = createKeyTable(tableInfo, tableSheet, rowIndex);
    }

    private Integer createFieldTable(TableInfo tableInfo, HSSFSheet tableSheet, Integer rowIndex) {
        // 创建表格头部
        HSSFRow tableSheetHeadRow = tableSheet.createRow(rowIndex++);

        createCell(0, tableSheetHeadRow, "字段", titleCellStyle);
        createCell(1, tableSheetHeadRow, "注释", titleCellStyle);
        createCell(2, tableSheetHeadRow, "类型", titleCellStyle);
        createCell(3, tableSheetHeadRow, "键", titleCellStyle);
        createCell(4, tableSheetHeadRow, "能否为空", titleCellStyle);
        createCell(5, tableSheetHeadRow, "默认值", titleCellStyle);

        // 创建表格内容
        for (int j = 0; j < tableInfo.getFields().size(); j++) {

            TableFieldInfo tableFieldInfo = tableInfo.getFields().get(j);
            HSSFRow fieldRow = tableSheet.createRow(rowIndex++);
            createCell(0, fieldRow, tableFieldInfo.getField(), simpleCellStyle);
            createCell(1, fieldRow, tableFieldInfo.getRemark(), simpleCellStyle);
            createCell(2, fieldRow, tableFieldInfo.getType(), simpleCellStyle);
            createCell(3, fieldRow, tableFieldInfo.getKey(), simpleCellStyle);
            createCell(4, fieldRow, tableFieldInfo.getNullAble(), simpleCellStyle);
            createCell(5, fieldRow, tableFieldInfo.getDefaultValue(), simpleCellStyle);
        }
        return rowIndex;
    }

    private Integer createKeyTable(TableInfo tableInfo, HSSFSheet tableSheet, Integer rowIndex) {
        //索引标题
        createCell(0, tableSheet.createRow(rowIndex++), "索引信息", simpleCellStyle);
        //索引头部
        HSSFRow indexSheetHeadRow = tableSheet.createRow(rowIndex++);
        createCell(0, indexSheetHeadRow, "名称", simpleCellStyle);
        createCell(1, indexSheetHeadRow, "栏位", simpleCellStyle);
        createCell(2, indexSheetHeadRow, "索引类型", simpleCellStyle);
        createCell(3, indexSheetHeadRow, "索引方式", simpleCellStyle);
        createCell(4, indexSheetHeadRow, "索引备注", simpleCellStyle);
        //创建索引内容
        for (int j = 0; j < tableInfo.getKeys().size(); j++) {
            TableKeyInfo tableKeyInfo = tableInfo.getKeys().get(j);
            HSSFRow keyRow = tableSheet.createRow(rowIndex++);
            createCell(0, keyRow, tableKeyInfo.getName(), simpleCellStyle);
            createCell(1, keyRow, tableKeyInfo.getColumnCombine(), simpleCellStyle);
            createCell(2, keyRow, tableKeyInfo.getUnique() ? "Unique" : "Normal", simpleCellStyle);
            createCell(3, keyRow, tableKeyInfo.getIndexType(), simpleCellStyle);
            createCell(4, keyRow, tableKeyInfo.getIndexComment(), simpleCellStyle);
        }
        return rowIndex;
    }

    /***
     * 创建单元格
     *
     * @param indexColumn
     *            第几个单元格
     * @param row
     *            HSSFRow
     * @param value
     *            值
     * @param style
     *            样式
     * @return
     */
    public HSSFCell createCell(int indexColumn, HSSFRow row, String value, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(indexColumn, CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(style);
        return cell;
    }

}
