package com.sanri.excel.poi;

import com.sanri.excel.ExcelException;
import com.sanri.excel.poi.annotation.ExcelExport;
import com.sanri.excel.poi.converter.DefaultBooleanStringConverter;
import com.sanri.excel.poi.converter.ExcelConverter;
import com.sanri.excel.poi.enums.CellType;
import com.sanri.excel.poi.enums.ExcelVersion;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 导出 Excel
 */
public class ExcelExportWriter<T> {
    public static final float BASE_WIDTH_1_PX = 35.7f;
    public static final float BASE_HEIGHT_1_PX = 15.625f;
    public static final float BASE_CHINESE = 2 * 256;

    private Logger logger = LoggerFactory.getLogger(ExcelExportWriter.class);

    private Workbook workbook = null;
    private ExcelExport excelExport;
    private Class<T> clazz;

    public ExcelExportWriter(Class<T> clazz) {
        this.clazz = clazz;
        ExcelExport excelExport = clazz.getAnnotation(ExcelExport.class);
        if (excelExport == null) {
            throw new ExcelException("导出类[" + clazz.getName() + "]需要注解 @ExcelExport");
        }
        this.excelExport = excelExport;

        ExcelVersion excelVersion = excelExport.version();
        switch (excelVersion) {
            case EXCEL2003:
                workbook = new HSSFWorkbook();
            case EXCEL2007:
                boolean fastModel = excelExport.fastModel();
                if (fastModel) {
                    int rowAccessWindowSize = excelExport.rowAccessWindowSize();
                    workbook = new SXSSFWorkbook(rowAccessWindowSize);
                } else {
                    workbook = new XSSFWorkbook();
                }
        }
    }

    /**
     * @param excelVersion
     * @param tips    提示文本,调用导出为空时可使用此方法返回一个带提示文本的工作薄
     * @作者: sanri
     * @时间: 2017/8/12 21:32
     * @功能: 创建空的工作薄
     */
    public static Workbook createEmptyWorkbook(String tips,ExcelVersion excelVersion) {
        Workbook workbook = null;
        if(excelVersion == ExcelVersion.EXCEL2003){
            workbook = new HSSFWorkbook();
        }else {
            workbook = new XSSFWorkbook();
        }
        if (StringUtils.isNotBlank(tips)) {
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(tips);
            //设置列宽为 tips中文列宽
            sheet.setColumnWidth(0, (int) (tips.length() * BASE_CHINESE));
        }
        return workbook;
    }

    /**
     * 功能:创建单元格样式<br/>
     * 创建时间:2017-8-13上午7:46:59<br/>
     * 作者：sanri<br/>
     *
     * @param workbook
     * @param font       字体设置
     * @param background 背景色
     * @param center     是否居中
     * @param wrapText   是否自动换行
     * @return<br/>
     */
    public CellStyle createCellStyle(Font font, IndexedColors background, boolean center, boolean wrapText) {
        CellStyle createCellStyle = workbook.createCellStyle();
        if (font != null) {
            createCellStyle.setFont(font);
        }
        createCellStyle.setFillForegroundColor(background.getIndex());
        createCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        createCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        createCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        createCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        createCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        if (center) {//水平居中,垂直居中
            createCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            createCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        }
        createCellStyle.setWrapText(wrapText);
        return createCellStyle;
    }

    /**
     * 功能:创建字体 <br/>
     * 创建时间:2017-8-13上午7:37:34<br/>
     * 作者：sanri<br/>
     *
     * @param workbook
     * @param family   字体种类
     * @param color    字体颜色
     * @param size     字体大小
     * @param b        是否加粗
     * @return<br/>
     */
    public Font createCellFont(String family, IndexedColors color, short size, boolean b) {
        Font createFont = workbook.createFont();
        createFont.setCharSet(Font.DEFAULT_CHARSET);
        createFont.setColor(color.getIndex());
        createFont.setFontName(family);
//		createFont.setFontHeight(size);
        //modify by sanri at 2017/09/19 字体使用点数
        createFont.setFontHeightInPoints(size);
        if (b) {
            createFont.setBoldweight((short) 700);
        }
        return createFont;
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-9-19下午4:59:04<br/>
     * 功能:创建字体  <br/>
     *
     * @param family 字体种类
     * @param color  字体颜色
     * @param size   字体大小
     * @param b      是否加粗
     * @param i      是否斜体
     * @param u      是否加下划线
     * @return
     */
    public Font createCellFont(String family, IndexedColors color, short size, boolean b, boolean i, boolean u) {
        Font createFont = createCellFont(family, color, size, b);
        createFont.setItalic(i);
        if (u) {
            createFont.setUnderline((byte) 1);
        }
        return createFont;
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-8-12下午1:53:58<br/>
     * 功能: 导出 excel <br/>
     *
     * @param title      标题
     * @param data       数据列表
     * @param titleStyle 标题样式
     * @param headStyle  头部样式
     * @param bodyStyle  主体数据样式
     * @return
     */
    public Workbook export(String title, List<T> data, CellStyle titleStyle, CellStyle headStyle, CellStyle bodyStyle) {
        if (data == null || data.size() == 0) {
            //无数据直接反回空
            return null;
        }
        //解析列配置
        List<ColumnConfig> columnConfigs = ColumnConfigUtil.parseColumnConfig(clazz, true);

        ExcelVersion version = excelExport.version();
        int sheetMaxRow = -1;
        if (excelExport.sheetMaxRow() == -1 && version == ExcelVersion.EXCEL2003) {
            //设置配置为最大行数
            sheetMaxRow = 60000;
        }
        try {
            //计算数据是否超量,是否需要创建多个 sheet
            List<Sheet> sheets = new ArrayList<Sheet>();
            if (sheetMaxRow == -1 || data.size() <= sheetMaxRow) {
                //只会创建一个 sheet
                Sheet createSheet = workbook.createSheet("全部数据");
                sheets.add(createSheet);
            } else {
                int sheetCount = (data.size() - 1) / sheetMaxRow + 1;
                for (int i = 0; i < sheetCount; i++) {
                    Sheet createSheet = workbook.createSheet("部分数据_part" + i);
                    sheets.add(createSheet);
                }
            }
            //正式添加数据
            if (sheets.size() == 1) {        //添加全部数据到一张 sheet 页中,如果只有一张 sheet 页的话
                Sheet sheet = sheets.get(0);
                int startRow = createSheetTitle(title, titleStyle, columnConfigs, sheet);
                insertDataToSheet(sheet, data, columnConfigs, startRow, headStyle, bodyStyle);
            } else {
                for (int i = 0; i < sheets.size(); i++) {
                    Sheet sheet = sheets.get(i);
                    //如果有标题,添加标题
                    int startRow = createSheetTitle(title, titleStyle, columnConfigs, sheet);
                    //复制截断的数据,到数据表 sheet 页
                    int startDataIndex = i * sheetMaxRow;
                    int endDataIndex = (i + 1) * sheetMaxRow;
                    if (endDataIndex > data.size()) {
                        endDataIndex = data.size();
                    }
                    List<T> partData = new ArrayList<T>();
                    for (int j = startDataIndex; j < endDataIndex; j++) {
                        partData.add(data.get(j));
                    }
                    insertDataToSheet(sheet, partData, columnConfigs, startRow, headStyle, bodyStyle);
                }
            }
        } catch (Exception e){
            throw new ExcelException("注入数据出错 insertDataToSheet "+e.getMessage(),e);
        }
        return workbook;
    }

    /**
     * 功能:下面都是各种需要的导出方法重载<br/>
     * 创建时间:2017-8-13上午8:57:04<br/>
     * 作者：sanri<br/>
     */
    public Workbook export(List<T> data, CellStyle headStyle, CellStyle bodyStyle) {
        return export("", data, null, headStyle, bodyStyle);
    }

    public Workbook export(List<T> data) {
        return export(data, null, null);
    }

    public Workbook export(String title, List<T> data) {
        return export(title, data, null);
    }

    public Workbook export(String title, List<T> data, CellStyle titleStyle) {
        return export(title, data, titleStyle, null, null);
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-9-1下午2:31:33<br/>
     * 功能:写到输入流 <br/>
     * 此方法只能用于导出<br/>
     *
     * @param outputStream
     * @throws IOException
     */
    public void writeTo(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-9-1下午2:27:29<br/>
     * 功能:将当前的 workbook 转为输入流,此方法只能用于导出 <br/>
     *
     * @return
     * @throws IOException
     */
    public InputStream toInputStream() throws IOException {
        return toInputStream(workbook);
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-9-1下午2:30:33<br/>
     * 功能:将任何一个 workbook 转为输入流 <br/>
     *
     * @param workbook
     * @return
     * @throws IOException
     */
    public static InputStream toInputStream(Workbook workbook) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            return byteArrayInputStream;
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-9-19下午5:06:41<br/>
     * 功能:以默认样式导出 <br/>
     *
     * @param title
     * @param data
     * @return
     */
    public Workbook exportDefaultStyle(String title, List<T> data) {
//		createCellFont("宋体", IndexedColors.BLACK, 15, b, i, u)
        CellStyle titleStyle = createCellStyle(null, IndexedColors.WHITE, true, false);
        CellStyle headStyle = createCellStyle(null, IndexedColors.LIGHT_GREEN, true, false);
        CellStyle bodyStyle = createCellStyle(null, IndexedColors.LIGHT_YELLOW, true, false);
        return export(title, data, titleStyle, headStyle, bodyStyle);
    }

    public Workbook exportDefaultStyle(List<T> data) {
        return exportDefaultStyle("", data);
    }

    /**
     * 作者: sanri
     * 时间 : 2017/08/12
     * 功能 : 创建 sheet 标题,如果存在的话
     *
     * @param title
     * @param titleStyle
     * @param excelExport
     * @param columnConfigs
     * @param sheet
     * @return 返回当前 sheet 起始行
     */
    private int createSheetTitle(String title, CellStyle titleStyle, List<ColumnConfig> columnConfigs, Sheet sheet) {
        int startRow = 0;
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(startRow++);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue(title);
            if (titleStyle != null) {
                titleCell.setCellStyle(titleStyle);
            }
            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnConfigs.size() - 1));
            short titleRowHeight = excelExport.titleRowHeight();
            titleRowHeight = (short) (titleRowHeight * BASE_HEIGHT_1_PX);
            titleRow.setHeight(titleRowHeight);
        }
        return startRow;
    }

    /**
     * 作者:sanri <br/>
     * 时间:2017-8-12下午6:35:29<br/>
     * 功能:添加数据到 sheet 页 <br/>
     *
     * @param <T>
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private <T> void insertDataToSheet(Sheet sheet, List<T> partData, List<ColumnConfig> columnConfigs, int startRow, CellStyle headStyle, CellStyle bodyStyle) throws Exception {
        Row headRow = sheet.createRow(startRow++);
        headRow.setHeight((short) (excelExport.headRowHeight() * BASE_HEIGHT_1_PX));
        //创建标题列
        for (int i = 0; i < columnConfigs.size(); i++) {
            ColumnConfig columnConfig = columnConfigs.get(i);
            String chinese = columnConfig.getChinese();
            Cell headCell = headRow.createCell(i);
            headCell.setCellValue(chinese);
            if (headStyle != null) {
                headCell.setCellStyle(headStyle);
            }
        }

        //创建数据列
        for (int i = 0; i < partData.size(); i++) {
            Row bodyRow = sheet.createRow(startRow++);
            bodyRow.setHeight((short) (excelExport.bodyRowHeight() * BASE_HEIGHT_1_PX));
            T dataItem = partData.get(i);
            for (int j = 0; j < columnConfigs.size(); j++) {
                ColumnConfig columnConfig = columnConfigs.get(j);
                Method readMethod = columnConfig.getReadMethod();
                Cell bodyCell = bodyRow.createCell(j);
                if (bodyStyle != null) {
                    bodyCell.setCellStyle(bodyStyle);
                }
                //add by sanri at 2018/11/05 设置 bodyCell 单元格格式
                CellType cellType = columnConfig.getCellType();
                bodyCell.setCellType(cellType.getValue());

                Object cellData = readMethod.invoke(dataItem);
                ExcelConverter excelConverter = columnConfig.getExcelConverter();
                if(excelConverter != null){     //如果存在转换器，则转换数据
                    cellData = excelConverter.convert(cellData);
                }
                Class<?> dataType = columnConfig.getDataType();

                //解决数据为 null 的情况，设置对应的空值
                if (cellData == null) {
                    switch (cellType) {
                        case CELL_TYPE_STRING:
                            bodyCell.setCellType(Cell.CELL_TYPE_STRING);
                            bodyCell.setCellValue("");
                            break;
                        case CELL_TYPE_BOOLEAN:
                            bodyCell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                            bodyCell.setCellValue(false);
                            break;
                        case CELL_TYPE_NUMERIC:
                            bodyCell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            bodyCell.setCellValue(0);
                            break;
                        case CELL_TYPE_BLANK:
                    }
                    continue;
                }

                //后面的数据都是在有值的情况下
                //如果数据类型是 double 或 Double 需要设置精度值
                if (dataType == double.class || dataType == Double.class || dataType == float.class || dataType == Float.class) {
                    String cellDataString = ObjectUtils.toString(cellData);
                    int precision = columnConfig.getPrecision();
                    if (precision != -1) {
                        BigDecimal bigDecimal = new BigDecimal(cellDataString);
                        if(dataType == double.class || dataType == Double.class) {
                            //TODO bigDecimal 对于超大数无法设置小数，比如 RandomUtils.nextDouble() 方法生成的值
                            cellData = bigDecimal.setScale(precision, RoundingMode.HALF_EVEN).doubleValue();
                        }else if(dataType == float.class || dataType == Float.class){
                            cellData = bigDecimal.setScale(precision, RoundingMode.HALF_EVEN).floatValue();
                        }
                    }
                }

                switch (cellType) {
                    case CELL_TYPE_STRING:
                        bodyCell.setCellType(Cell.CELL_TYPE_STRING);

                        if (dataType == Date.class) {
                            //获取日期对象数据
                            Date cellDataReal = null;
                            if (cellData != null) {
                                cellDataReal = (Date) cellData;
                            }

                            //如果是日期类型,则调用转换规则进行转换
                            String pattern = columnConfig.getPattern();
                            if (StringUtils.isBlank(pattern)) {
                                //如果是空格式,直接设置日期数据
                                bodyCell.setCellValue(cellDataReal);
                            } else {
                                bodyCell.setCellValue(DateFormatUtils.format(cellDataReal, pattern));
                            }
                        } else {
                            bodyCell.setCellValue(ObjectUtils.toString(cellData));
                        }
                        break;
                    case CELL_TYPE_NUMERIC:
                        bodyCell.setCellType(Cell.CELL_TYPE_NUMERIC);

                        String cellDataString = ObjectUtils.toString(cellData, "0");
                        boolean primitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(dataType);
                        if(primitiveOrWrapper){
                            if(dataType == int.class || dataType == Integer.class){
                                bodyCell.setCellValue(NumberUtils.toInt(cellDataString));
                            }else if(dataType == long.class || dataType == Long.class){
                                bodyCell.setCellValue(NumberUtils.toLong(cellDataString));
                            }else if(dataType == double.class || dataType == Double.class){
                                bodyCell.setCellValue(NumberUtils.toDouble(cellDataString));
                            }else if(dataType == float.class || dataType == Float.class){
                                bodyCell.setCellValue(NumberUtils.toFloat(cellDataString));
                            }else {
                                logger.warn("单元格类型设置 numeric ,但数据类型不支持; 列为:" + columnConfig.getPropertyName() + ",类型为:" + dataType + "，支持的类型有[int,long,double,float]");
                            }
                        }else if(dataType == Date.class){       //如果日期需要设置进数值单元格，强转化为毫秒值
                            Date cellDataReal = (Date) cellData;
                            bodyCell.setCellValue(cellDataReal.getTime());
                        }else {
                            logger.warn("单元格类型设置 numeric ,但数据类型不是 numeric ; 列为:" + columnConfig.getPropertyName() + ",类型为:" + dataType);
                        }
                        break;
                    case CELL_TYPE_BOOLEAN:
                        if (dataType == Boolean.TYPE || dataType == Boolean.class) {
                            bodyCell.setCellValue((Boolean) cellData);
                        } else {
                            logger.warn("单元格类型设置 boolean ,但数据类型不是 Boolean ; 列为:" + columnConfig.getPropertyName()+"，类型为:"+dataType);
                        }
                        break;
                    case CELL_TYPE_BLANK:
                        //空值不设置数据
                        break;
                }

            }
        }

        //设置列宽
        boolean autoWidth = excelExport.autoWidth();
        if (autoWidth) {
            //自动列宽后使用两倍自动列宽
            for (int i = 0; i < columnConfigs.size(); i++) {
                sheet.autoSizeColumn(i);
                ColumnConfig columnConfig = columnConfigs.get(i);

                int width = sheet.getColumnWidth(i);
                String titleChinese = columnConfig.getChinese();
                float titleWidth = titleChinese.length() * BASE_CHINESE;
                if (width < titleWidth) {
                    width = (int) titleWidth;
                    sheet.setColumnWidth(i, width);
                }

                if (columnConfig.isChineseWidth()) {
                    // 宽度设置为原来两倍,并且加一个中文字宽度
                    int width_2 = (int) (width * 2 + 1 * BASE_CHINESE);
                    //add by sanri at 2017/12/02 解决最大宽度超出限制问题
                    if (width_2 > 65280) {
                        width_2 = 65280;
                    }
                    sheet.setColumnWidth(i, width_2);
                }
            }
        } else {
            //宽度配置策略 如果没有配置任何宽度,则取标题中文字宽度,如果有配置,则使用配置
            for (int i = 0; i < columnConfigs.size(); i++) {
                ColumnConfig columnConfig = columnConfigs.get(i);
                //begin modify by sanri at 2017/09/19 增加列宽配置策略
                int width = columnConfig.getWidth();
                int charWidth = columnConfig.getCharWidth();
                int pxWidth = columnConfig.getPxWidth();
                int finalWidth = -1;
                if (width == -1 && charWidth == -1 && pxWidth == -1) {
                    //没有配置任何宽度,使用标题中文字宽度
                    finalWidth = (int) (columnConfig.getChinese().length() * BASE_CHINESE);
                } else {
                    if (width != -1) {
                        finalWidth = width;
                    } else if (charWidth != -1) {
                        finalWidth = (int) (charWidth * BASE_CHINESE);
                    } else {
                        finalWidth = (int) (pxWidth * BASE_WIDTH_1_PX);
                    }
                }
//				if(width < columnConfig.getChinese().length()){
//					//如果默认宽度是小于了中文字的宽度,则取中文字的宽度
//					width = (int) (columnConfig.getChinese().length() * BASE_CHINESE);
//				}
                //add by sanri at 2017/12/02 解决最大宽度超出限制问题
                if (finalWidth > 65280) {
                    finalWidth = 65280;
                }
                sheet.setColumnWidth(i, finalWidth);
                //end modify by sanri at 2017/09/19 增加列宽配置策略
            }
        }

        //隐藏列配置
        for (int i = 0; i < columnConfigs.size(); i++) {
            ColumnConfig columnConfig = columnConfigs.get(i);
            boolean hidden = columnConfig.isHidden();
            sheet.setColumnHidden(i, hidden);
        }
    }

}
