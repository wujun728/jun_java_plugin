package com.github.andyczy.java.excel;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * @author Chenzy
 * @date 2018-05-03
 * @description: Excel 导入导出操作相关工具类
 * @Email: 649954910@qq.com
 */
public class ExcelUtils {

    private static final ThreadLocal<SimpleDateFormat> fmt = new ThreadLocal<>();
    private static final String MESSAGE_FORMAT = "yyyy-MM-dd";
    private static final String DataValidationError1 = "提示信息：";
    private static final String DataValidationError2 = "数据不规范，请选择表格下拉列表中的数据！";

    private static final SimpleDateFormat getDateFormat() {
        SimpleDateFormat format = fmt.get();
        if (format == null) {
            format = new SimpleDateFormat(MESSAGE_FORMAT, Locale.getDefault());
            fmt.set(format);
        }
        return format;
    }

    private static final ThreadLocal<DecimalFormat> df = new ThreadLocal<>();
    private static final String MESSAGE_FORMAT_df = "#.###";

    private static final DecimalFormat getDecimalFormat() {
        DecimalFormat format = df.get();
        if (format == null) {
            format = new DecimalFormat(MESSAGE_FORMAT_df);
            df.set(format);
        }
        return format;
    }


    private static final ThreadLocal<ExcelUtils> UTILS_THREAD_LOCAL = new ThreadLocal<>();

    public static final ExcelUtils setExcelUtils(ExcelPojo excelPojo) {
        ExcelUtils excelUtils = UTILS_THREAD_LOCAL.get();
        if (excelUtils == null) {
            excelUtils = new ExcelUtils(excelPojo);
            UTILS_THREAD_LOCAL.set(excelUtils);
        }
        return excelUtils;
    }

    private static String filePath = null;
    private static List<List<String[]>> dataLists = null;
    private static HttpServletResponse response = null;
    private static HashMap notBorderMap = null;
    private static HashMap regionMap = null;
    private static HashMap columnMap = null;
    private static HashMap styles = null;
    private static HashMap paneMap = null;
    private static String fileName = null;
    private static String[] sheetName = null;
    private static String[] labelName = null;
    private static HashMap rowStyles = null;
    private static HashMap columnStyles = null;
    private static HashMap dropDownMap = null;

    public ExcelUtils(ExcelPojo excelPojo) {
        filePath = excelPojo.getFilePath();
        dataLists = excelPojo.getDataLists();
        response = excelPojo.getResponse();
        notBorderMap = excelPojo.getNotBorderMap();
        regionMap = excelPojo.getRegionMap();
        columnMap = excelPojo.getColumnMap();
        styles = excelPojo.getStyles();
        paneMap = excelPojo.getPaneMap();
        fileName = excelPojo.getFileName();
        sheetName = excelPojo.getSheetName();
        labelName = excelPojo.getLabelName();
        rowStyles = excelPojo.getRowStyles();
        columnStyles = excelPojo.getColumnStyles();
        dropDownMap = excelPojo.getDropDownMap();
    }


    /**
     * 面向对象编程
     *
     * @return
     */
    public Boolean exportForExcelsOptimize() {
        long startTime = System.currentTimeMillis();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
        OutputStream outputStream = null;
        SXSSFRow sxssfRow = null;
        try {
            // 设置数据
            setDataList(sxssfWorkbook, sxssfRow, dataLists, notBorderMap, regionMap, columnMap, styles, paneMap, sheetName, labelName, rowStyles, columnStyles, dropDownMap);
            // io 响应
            setIo(sxssfWorkbook, outputStream, fileName, sheetName, response, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======= Excel 工具类导出运行时间:  " + (System.currentTimeMillis() - startTime) + " ms");
        return true;
    }


    /**
     * 功能描述: excel 数据导出、导出模板
     * <p>
     * 更新日志:
     * 1.response.reset();注释掉reset，否在会出现跨域错误。[2018-05-18]
     * 2.新增导出多个单元。[2018-08-08]
     * 3.poi官方建议大数据量解决方案：SXSSFWorkbook。[2018-08-08]
     * 4.自定义下拉列表：对每个单元格自定义下拉列表。[2018-08-08]
     * 5.数据遍历方式换成数组(效率较高)。[2018-08-08]
     * 6.可提供模板下载。[2018-08-08]
     * 7.每个表格的大标题[2018-09-14]
     * 8.自定义列宽：对每个单元格自定义列宽[2018-09-18]
     * 9.自定义样式：对每个单元格自定义样式[2018-10-22]
     * 10.自定义单元格合并：对每个单元格合并[2018-10-22]
     * 11.固定表头[2018-10-23]
     * 12.自定义样式：单元格自定义某一列或者某一行样式[2018-11-12]
     * 13.忽略边框(默认是有边框)[2018-11-15]
     * 14.函数式编程换成面向对象编程[2018-12-06-5]
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.apache poi-ooxml  3.17
     *
     * @param response
     * @param dataLists    导出的数据(不可为空：如果只有标题就导出模板)
     * @param sheetName    sheet名称（不可为空）
     * @param columnMap    自定义：对每个单元格自定义列宽（可为空）
     * @param dropDownMap  自定义：对每个单元格自定义下拉列表（可为空）
     * @param styles       自定义：每一个单元格样式（可为空）
     * @param rowStyles    自定义：某一行样式（可为空）
     * @param columnStyles 自定义：某一列样式（可为空）
     * @param regionMap    自定义：单元格合并（可为空）
     * @param paneMap      固定表头（可为空）
     * @param labelName    每个表格的大标题（可为空）
     * @param fileName     文件名称(可为空，默认是：sheet 第一个名称)
     * @param notBorderMap 忽略边框(默认是有边框)
     * @return
     */
    public static Boolean exportForExcel(HttpServletResponse response, List<List<String[]>> dataLists, HashMap notBorderMap,
                                         HashMap regionMap, HashMap columnMap, HashMap styles, HashMap paneMap, String fileName,
                                         String[] sheetName, String[] labelName, HashMap rowStyles, HashMap columnStyles, HashMap dropDownMap) {
        long startTime = System.currentTimeMillis();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(1000);
        OutputStream outputStream = null;
        SXSSFRow sxssfRow = null;
        try {
            // 设置数据
            setDataList(sxssfWorkbook, sxssfRow, dataLists, notBorderMap, regionMap, columnMap, styles, paneMap, sheetName, labelName, rowStyles, columnStyles, dropDownMap);
            // io 响应
            setIo(sxssfWorkbook, outputStream, fileName, sheetName, response, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("======= Excel 工具类导出运行时间:  " + (System.currentTimeMillis() - startTime) + " ms");
        return true;
    }


    /**
     * 功能描述:
     * 1.excel 模板数据导入。
     * <p>
     * 更新日志:
     * 1.共用获取Excel表格数据。
     * 2.多单元数据获取。
     * 3.多单元从第几行开始获取数据[2018-09-20]
     * 4.多单元根据那些列为空来忽略行数据[2018-10-22]
     * <p>
     * 版  本:
     * 1.apache poi 3.17
     * 2.apache poi-ooxml  3.17
     *
     * @param book           Workbook对象（不可为空）
     * @param sheetName      多单元数据获取（不可为空）
     * @param indexMap       多单元从第几行开始获取数据，默认从第二行开始获取（可为空，如 hashMapIndex.put(1,3); 第一个表格从第三行开始获取）
     * @param continueRowMap 多单元根据那些列为空来忽略行数据（可为空，如 mapContinueRow.put(1,new Integer[]{1, 3}); 第一个表格从1、3列为空就忽略）
     * @return
     */
    @SuppressWarnings({"deprecation", "rawtypes"})
    public static List<List<LinkedHashMap<String, String>>> importForExcelData(Workbook book, String[] sheetName, HashMap indexMap, HashMap continueRowMap) {
        long startTime = System.currentTimeMillis();
        try {
            List<List<LinkedHashMap<String, String>>> returnDataList = new ArrayList<>();
            for (int k = 0; k <= sheetName.length - 1; k++) {
                //  得到第K个工作表对象、得到第K个工作表中的总行数。
                Sheet sheet = book.getSheetAt(k);
                int rowCount = sheet.getLastRowNum() + 1;
                Row valueRow = null;

                List<LinkedHashMap<String, String>> rowListValue = new ArrayList<>();
                LinkedHashMap<String, String> cellHashMap = null;

                int irow = 1;
                //  第n个工作表:从开始获取数据、默认第二行开始获取。
                if (indexMap != null && indexMap.get(k + 1) != null) {
                    irow = Integer.valueOf(indexMap.get(k + 1).toString()) - 1;
                }
                //  第n个工作表:数据获取。
                for (int i = irow <= 0 ? 1 : irow; i < rowCount; i++) {
                    valueRow = sheet.getRow(i);
                    if (valueRow == null) {
                        continue;
                    }

                    //  第n个工作表:从开始列忽略数据、为空就跳过。
                    if (continueRowMap != null && continueRowMap.get(k + 1) != null) {
                        int continueRowCount = 0;
                        Integer[] continueRow = (Integer[]) continueRowMap.get(k + 1);
                        for (int w = 0; w <= continueRow.length - 1; w++) {
                            Cell valueRowCell = valueRow.getCell(continueRow[w] - 1);
                            if (valueRowCell == null || isBlank(valueRowCell.toString())) {
                                continueRowCount = continueRowCount + 1;
                            }
                        }
                        if (continueRowCount == continueRow.length) {
                            continue;
                        }
                    }

                    cellHashMap = new LinkedHashMap<>();

                    //  第n个工作表:获取列数据。
                    for (int j = 0; j < valueRow.getLastCellNum(); j++) {
                        cellHashMap.put(Integer.toString(j), getCellVal(valueRow.getCell(j)));
                    }
                    if (cellHashMap.size() > 0) {
                        rowListValue.add(cellHashMap);
                    }
                }
                returnDataList.add(rowListValue);
            }
            System.out.println("======= Excel 工具类导入运行时间:  " + (System.currentTimeMillis() - startTime) + " ms");
            return returnDataList;
        } catch (Exception e) {
            System.out.println("======= Excel 工具类导入异常");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置数据
     *
     * @param sxssfWorkbook
     * @param sxssfRow
     * @param dataLists
     * @param notBorderMap
     * @param regionMap
     * @param columnMap
     * @param styles
     * @param paneMap
     * @param sheetName
     * @param labelName
     * @param rowStyles
     * @param columnStyles
     * @param dropDownMap
     */
    private static void setDataList(SXSSFWorkbook sxssfWorkbook, SXSSFRow sxssfRow, List<List<String[]>> dataLists, HashMap notBorderMap,
                                    HashMap regionMap, HashMap columnMap, HashMap styles, HashMap paneMap,
                                    String[] sheetName, String[] labelName, HashMap rowStyles, HashMap columnStyles, HashMap dropDownMap) throws Exception {
        if (dataLists == null) {
            throw new IOException("Andyczy ExcelUtils Exception Message：Export data(type:List<List<String[]>>) cannot be empty ！");
        }
        if (sheetName == null) {
            throw new IOException("Andyczy ExcelUtils Exception Message：Export sheet(type:String[]) name cannot be empty ！");
        }
        int k = 0;
        for (List<String[]> list : dataLists) {
            SXSSFSheet sxssfSheet = sxssfWorkbook.createSheet();
            sxssfSheet.setDefaultColumnWidth((short) 16);
            sxssfWorkbook.setSheetName(k, sheetName[k]);

            int jRow = 0;
            //  自定义：大标题和样式。参数说明：new String[]{"表格数据一", "表格数据二", "表格数据三"}
            if (labelName != null) {
                sxssfRow = sxssfSheet.createRow(jRow);
                Cell cell = createCell(sxssfRow, jRow, labelName[k]);
                setMergedRegion(sxssfSheet, 0, 0, 0, list.get(0).length - 1);
                setExcelStyles(cell, sxssfWorkbook, sxssfRow, 16, true, true, false, false, false, null, 399);
                jRow = 1;
            }
            //  自定义：每个单元格自定义合并单元格：对每个单元格自定义合并单元格（看该方法说明）。
            if (regionMap != null) {
                setMergedRegion(sxssfSheet, (ArrayList<Integer[]>) regionMap.get(k + 1));
            }
            //  自定义：每个单元格自定义下拉列表：对每个单元格自定义下拉列表（看该方法说明）。
            if (dropDownMap != null) {
                setDataValidation(sxssfSheet, (List<String[]>) dropDownMap.get(k + 1), list.size());
            }
            //  自定义：每个表格自定义列宽：对每个单元格自定义列宽（看该方法说明）。
            if (columnMap != null) {
                setColumnWidth(sxssfSheet, (HashMap) columnMap.get(k + 1));
            }
            //  自定义：每个表格固定表头（看该方法说明）。
            Integer pane = 1;
            if (paneMap != null) {
                pane = (Integer) paneMap.get(k + 1) + (labelName != null ? 1 : 0);
                createFreezePane(sxssfSheet, pane);
            }

            //  写入标题与数据。
            for (String[] listValue : list) {
                int columnIndex = 0;
                sxssfRow = sxssfSheet.createRow(jRow);
                for (int j = 0; j < listValue.length; j++) {
                    Cell cells = sxssfRow.createCell(j);
                    Cell cell = createCell(sxssfRow, columnIndex, listValue[j]);
                    columnIndex++;
                    //  所有单元格默认样式。
                    setExcelStyles(notBorderMap, cell, sxssfWorkbook, sxssfRow, k, jRow);
                    //  自定义：每个表格每一列的样式（看该方法说明）。
                    if (columnStyles != null && jRow >= pane) {
                        setExcelCellStyles(cell, sxssfWorkbook, sxssfRow, (List) columnStyles.get(k + 1), j);
                    }
                    //  自定义：每个表格每一行的样式（看该方法说明）。
                    if (rowStyles != null) {
                        setExcelCellStyles(cell, sxssfWorkbook, sxssfRow, (List) rowStyles.get(k + 1), jRow);
                    }
                    //  自定义：每一个单元格样式（看该方法说明）。
                    if (styles != null) {
                        setExcelStyles(cells, sxssfWorkbook, sxssfRow, (List<List<Object[]>>) styles.get(k + 1), j, jRow);
                    }
                }
                jRow++;
            }
            k++;
        }
    }

    /**
     * 输出本地地址 或者 response 响应
     *
     * @param sxssfWorkbook
     * @param outputStream
     * @param fileName
     * @param sheetName
     * @param response
     * @param filePath
     * @throws Exception
     */
    private static void setIo(SXSSFWorkbook sxssfWorkbook, OutputStream outputStream, String fileName, String[] sheetName, HttpServletResponse response, String filePath) throws Exception {
        try {
            if (response != null) {
                response.setHeader("Charset", "UTF-8");
                response.setHeader("Content-Type", "application/force-download");
                response.setHeader("Content-Type", "application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName == null ? sheetName[0] : fileName, "utf8") + ".xlsx");
                response.flushBuffer();
                outputStream = response.getOutputStream();
            } else if (filePath != null) {
                outputStream = new FileOutputStream(filePath);
            }
            if (sxssfWorkbook != null) {
                sxssfWorkbook.write(outputStream);
                sxssfWorkbook.dispose();
            }
        } catch (Exception e) {
            e.getSuppressed();
        }
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    private static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    /**
     * 功能描述: 锁定行（固定表头）
     * 使用的方法：
     * HashMap setPaneMap = new HashMap();
     * setPaneMap.put(1,3);  //第一个表格、第三行开始固定表头
     *
     * @param sxssfSheet
     * @param row
     */
    private static void createFreezePane(SXSSFSheet sxssfSheet, Integer row) {
        if (row != null && row > 0) {
            sxssfSheet.createFreezePane(0, row, 0, 1);
        }
    }

    /**
     * 功能描述: 自定义列宽
     * 使用的方法：
     * HashMap<Integer,HashMap<Integer,Integer>>  mapSheet = new HashMap();
     * HashMap<Integer,Integer> mapColumn = new HashMap();
     * mapColumn.put(0,5);             //第一列，列宽为5
     * mapColumn.put(3,5);             //第四列，列宽为5
     * mapSheet.put(1, mapColumn);     //第一个元格列宽
     *
     * @param sxssfSheet
     * @param map
     */
    private static void setColumnWidth(SXSSFSheet sxssfSheet, HashMap map) {
        if (map != null) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                sxssfSheet.setColumnWidth((int) key, (int) val * 512);
            }
        }
    }


    /**
     * 功能描述: excel 合并单元格
     * 使用的方法：
     * List<List<Integer[]>> setMergedRegion = new ArrayList<>();
     * List<Integer[]> sheet1 = new ArrayList<>();                  //第一个表格设置。
     * Integer[] sheetColumn1 = new Integer[]{0, 1, 0, 2}           //代表起始行号，终止行号， 起始列号，终止列号进行合并。（excel从零行开始数）
     * setMergedRegion.add(sheet1);
     *
     * @param sheet
     * @param rowColList
     */
    private static void setMergedRegion(SXSSFSheet sheet, ArrayList<Integer[]> rowColList) {
        if (rowColList != null && rowColList.size() > 0) {
            for (int i = 0; i < rowColList.size(); i++) {
                Integer[] str = rowColList.get(i);
                if (str.length > 0 && str.length == 4) {
                    Integer firstRow = str[0];
                    Integer lastRow = str[1];
                    Integer firstCol = str[2];
                    Integer lastCol = str[3];
                    setMergedRegion(sheet, firstRow, lastRow, firstCol, lastCol);
                }
            }
        }
    }

    /**
     * 功能描述: 合并单元格
     *
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    private static void setMergedRegion(SXSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }


    /**
     * 功能描述:下拉列表
     * 使用的方法：
     * HashMap hashMap = new HashMap();
     * List<String[]> sheet1 = new ArrayList<>();                  //第一个表格设置。
     * String[] sheetColumn1 = new String[]{"1", "2", "4"};        //必须放第一：设置下拉列表的列（excel从零行开始数）
     * String[] sex = {"男","女"};                                  //下拉的值放在 sheetColumn1 后面。
     * sheet1.add(sheetColumn1);
     * sheet1.add(sex);
     * hashMap.put(1,sheet1);                                      //第一个表格的下拉列表值
     *
     * @param sheet
     * @param dropDownListData
     * @param dataListSize
     */
    private static void setDataValidation(SXSSFSheet sheet, List<String[]> dropDownListData, int dataListSize) {
        if (dropDownListData.size() > 0) {
            for (int col = 0; col < dropDownListData.get(0).length; col++) {
                Integer colv = Integer.parseInt(dropDownListData.get(0)[col]);
                setDataValidation(sheet, dropDownListData.get(col + 1), 1, dataListSize < 100 ? 500 : dataListSize, colv, colv);
            }
        }
    }


    /**
     * 功能描述:下拉列表
     *
     * @param xssfWsheet
     * @param list
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     */
    private static void setDataValidation(SXSSFSheet xssfWsheet, String[] list, Integer firstRow, Integer lastRow, Integer firstCol, Integer lastCol) {
        DataValidationHelper helper = xssfWsheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        DataValidationConstraint constraint = helper.createExplicitListConstraint(list);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);
        dataValidation.createErrorBox(DataValidationError1, DataValidationError2);
        //  处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }
        xssfWsheet.addValidationData(dataValidation);
    }


    /**
     * 样式
     *
     * @param notBorderMap
     * @param cell
     * @param wb
     * @param sxssfRow
     * @param k
     * @param jRow
     */
    private static void setExcelStyles(HashMap notBorderMap, Cell cell, SXSSFWorkbook wb, SXSSFRow sxssfRow, int k, int jRow) {
        Boolean border = true;
        if (notBorderMap != null) {
            Integer[] borderInt = (Integer[]) notBorderMap.get(k + 1);
            for (int i = 0; i < borderInt.length; i++) {
                if (borderInt[i] == jRow) {
                    border = false;
                }
            }
        }
        setExcelStyles(cell, wb, sxssfRow, null, null, true, border, false, false, null, null);
    }

    /**
     * @param cell         Cell对象。
     * @param wb           SXSSFWorkbook对象。
     * @param fontSize     字体大小。
     * @param bold         是否加粗。
     * @param center       是否左右上下居中。
     * @param isBorder     是否加边框
     * @param leftBoolean  左对齐
     * @param rightBoolean 右对齐
     * @param height       行高
     */
    private static void setExcelStyles(Cell cell, SXSSFWorkbook wb, SXSSFRow sxssfRow, Integer fontSize, Boolean bold, Boolean center, Boolean isBorder, Boolean leftBoolean,
                                       Boolean rightBoolean, Integer fontColor, Integer height) {
        CellStyle cellStyle = wb.createCellStyle();
        //左右居中、上下居中
        if (center != null && center) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        //右对齐
        if (rightBoolean != null && rightBoolean) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        }
        //左对齐
        if (leftBoolean != null && leftBoolean) {
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        }
        //边框
        if (isBorder != null && isBorder) {
            setBorder(cellStyle, isBorder);
        }
        //设置单元格字体样式
        XSSFFont font = (XSSFFont) wb.createFont();
        if (bold != null && bold) {
            font.setBold(bold);
        }
        //行高
        if (height != null) {
            sxssfRow.setHeight((short) (height * 2));
        }
        font.setFontName("宋体");
        font.setFontHeight(fontSize == null ? 12 : fontSize);
        cellStyle.setFont(font);
        //   点击可查看颜色对应的值： BLACK(8), WHITE(9), RED(10),
        font.setColor(IndexedColors.fromInt(fontColor == null ? 8 : fontColor).index);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 功能描述：自定义某一行、列的样式
     * 使用的方法：是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，是否有边框？ —— 颜色、字体、行高？
     * HashMap hashMap = new HashMap();
     * List list = new ArrayList();
     * list.add(new Boolean[]{true, false, false, false, true});                //1、样式
     * list.add(new Integer[]{1, 3});                                           //2、第几行或者是第几列
     * list.add(new Integer[]{10,14,null});                                     //3、颜色值 、字体大小、行高（可不设置）
     * hashMap.put(1,list);                                                     //第一表格
     *
     * @param cell
     * @param wb
     * @param rowstyleList
     * @param rowIndex
     */
    private static void setExcelCellStyles(Cell cell, SXSSFWorkbook wb, SXSSFRow sxssfRow, List<Object[]> rowstyleList, int rowIndex) {
        if (rowstyleList != null && rowstyleList.size() > 0) {
            Integer[] rowstyle = (Integer[]) rowstyleList.get(1);
            for (int i = 0; i < rowstyle.length; i++) {
                if (rowIndex == rowstyle[i]) {
                    Boolean[] bool = (Boolean[]) rowstyleList.get(0);
                    Integer fontColor = null;
                    Integer fontSize = null;
                    Integer height = null;
                    //当有设置颜色值 、字体大小、行高才获取值
                    if (rowstyleList.size() >= 3) {
                        int leng = rowstyleList.get(2).length;
                        fontColor = (Integer) rowstyleList.get(2)[0];
                        if (leng >= 2) {
                            fontSize = (Integer) rowstyleList.get(2)[1];
                        }
                        if (leng >= 3) {
                            height = (Integer) rowstyleList.get(2)[2];
                        }
                    }
                    setExcelStyles(cell, wb, sxssfRow, fontSize, Boolean.valueOf(bool[3]), Boolean.valueOf(bool[0]), Boolean.valueOf(bool[4]), Boolean.valueOf(bool[2]), Boolean.valueOf(bool[1]), fontColor, height);
                }
            }
        }
    }

    /**
     * 功能描述：所有自定义单元格样式
     * 使用的方法：是否居中？，是否右对齐？，是否左对齐？， 是否加粗？，是否有边框？  —— 颜色、字体、行高？
     * HashMap cellStyles = new HashMap();
     * List< List<Object[]>> list = new ArrayList<>();
     * List<Object[]> objectsList = new ArrayList<>();
     * List<Object[]> objectsListTwo = new ArrayList<>();
     * objectsList.add(new Boolean[]{true, false, false, false, true});      //1、样式一（必须放第一）
     * objectsList.add(new Integer[]{10, 12});                               //1、颜色值 、字体大小、行高（必须放第二）
     * <p>
     * objectsListTwo.add(new Boolean[]{false, false, false, true, true});   //2、样式二（必须放第一）
     * objectsListTwo.add(new Integer[]{10, 12,null});                       //2、颜色值 、字体大小、行高（必须放第二）
     * <p>
     * objectsList.add(new Integer[]{5, 1});                                 //1、第五行第一列
     * objectsList.add(new Integer[]{6, 1});                                 //1、第六行第一列
     * <p>
     * objectsListTwo.add(new Integer[]{2, 1});                              //2、第二行第一列
     * <p>
     * cellStyles.put(1, list);                                              //第一个表格所有自定义单元格样式
     *
     * @param cell
     * @param wb
     * @param styles
     */
    private static void setExcelStyles(Cell cell, SXSSFWorkbook wb, SXSSFRow sxssfRow, List<List<Object[]>> styles, int cellIndex, int rowIndex) {
        if (styles != null) {
            for (int z = 0; z < styles.size(); z++) {
                List<Object[]> stylesList = styles.get(z);
                if (stylesList != null) {
                    //样式
                    Boolean[] bool = (Boolean[]) stylesList.get(0);
                    //颜色和字体
                    Integer fontColor = null;
                    Integer fontSize = null;
                    Integer height = null;
                    //当有设置颜色值 、字体大小、行高才获取值
                    if (stylesList.size() >= 2) {
                        int leng = stylesList.get(1).length;
                        fontColor = (Integer) stylesList.get(1)[0];
                        if (leng >= 2) {
                            fontSize = (Integer) stylesList.get(1)[1];
                        }
                        if (leng >= 3) {
                            height = (Integer) stylesList.get(1)[2];
                        }
                    }
                    //第几行第几列
                    for (int m = 2; m < stylesList.size(); m++) {
                        Integer[] str = (Integer[]) stylesList.get(m);
                        if (cellIndex + 1 == (str[1]) && rowIndex + 1 == (str[0])) {
                            setExcelStyles(cell, wb, sxssfRow, fontSize, Boolean.valueOf(bool[3]), Boolean.valueOf(bool[0]), Boolean.valueOf(bool[4]), Boolean.valueOf(bool[2]), Boolean.valueOf(bool[1]), fontColor, height);
                        }
                    }
                }
            }
        }
    }


    /**
     * 功能描述：设置边框
     *
     * @param cellStyle
     * @param isBorder
     */
    private static void setBorder(CellStyle cellStyle, Boolean isBorder) {
        if (isBorder) {
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
    }


    /**
     * 功能描述: 获取Excel单元格中的值并且转换java类型格式
     *
     * @param cell
     * @return
     */
    private static String getCellVal(Cell cell) {
        String val = null;
        if (cell != null) {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        val = getDateFormat().format(cell.getDateCellValue());
                    } else {
                        val = getDecimalFormat().format(cell.getNumericCellValue());
                    }
                    break;
                case STRING:
                    val = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    val = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    val = cell.getStringCellValue();
                    break;
                case ERROR:
                    val = "错误";
                    break;
                case FORMULA:
                    try {
                        val = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        val = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                default:
                    val = cell.getRichStringCellValue() == null ? null : cell.getRichStringCellValue().toString();
            }
        } else {
            val = "";
        }
        return val;
    }

}

