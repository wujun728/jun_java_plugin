package com.sanri.excel.poi;

import com.sanri.excel.ExcelException;
import com.sanri.excel.poi.annotation.ExcelImport;
import com.sanri.excel.poi.handler.ErrorRowHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 导入 Excel 工具类
 */
public class ExcelImportUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);
    /**
     *
     * 作者:sanri <br/>
     * 时间:2017-11-15下午12:28:58<br/>
     * 功能:简单数据导入 <br/>
     * @param in 输入流
     * @param clazz 导的数据是什么类型
     * @param whichCol 导哪一列数据 从 0 开始
     * @param startRow 从 0 开始
     * @return
     * 注:只支持第一个 sheet 页
     */
    public static <T> List<T> importListData(InputStream in,Class<T> clazz,int whichCol,int startRow){
        List<T> data = new ArrayList<T>();

        Workbook workbook = getWorkbookFromInputStream(in);
        Sheet sheet = workbook.getSheetAt(0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

        //起始行大于总行数,返回空数据
        if(startRow >= physicalNumberOfRows){
            return data;
        }

        CreationHelper creationHelper = workbook.getCreationHelper();
        //开始处理每一行数据
        for(int i=startRow;i<physicalNumberOfRows;i++){
            Row row = sheet.getRow(i);
            if(row == null){
                //处理行是绝对空的问题 add by sanri at 2017/12/12
                continue;
            }
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            if(whichCol >= physicalNumberOfCells){
                data.add(null);
                continue;
            }
            Cell cell = row.getCell(whichCol);
            //转换 cell 数据,并加入
            if(cell == null){
                continue;
            }
            T cellData = transferCellData(cell.getCellType(),cell,clazz,DateFormatUtils.ISO_DATE_FORMAT,decimalFormat,creationHelper);
            data.add(cellData);
        }
        return data;
    }

    /**
     *
     * 作者:sanri <br/>
     * 时间:2017-11-15下午12:31:59<br/>
     * 功能:导入  map 类型数据 <br/>
     * @param in
     * @param clazz
     * @param keyCol 键列 从 0 开始
     * @param valueCol 值列 从 0 开始
     * @param startRow 起始行 从 0 开始
     * @return
     */
    public static <V> Map<String,V> importMapData(InputStream in,Class<V> clazz,int keyCol,int valueCol,int startRow){
        Map<String,V> mapData = new HashMap<String, V>();

        Workbook workbook = getWorkbookFromInputStream(in);
        Sheet sheet = workbook.getSheetAt(0);
        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

        //起始行大于总行数,返回空数据
        if(startRow >= physicalNumberOfRows){
            return mapData;
        }

        CreationHelper creationHelper = workbook.getCreationHelper();

        //开始处理每一行数据
        for(int i=startRow;i<physicalNumberOfRows;i++){
            Row row = sheet.getRow(i);
            if(row == null){
                //处理行是绝对空的问题 add by sanri at 2017/12/12
                continue;
            }
            int physicalNumberOfCells = row.getPhysicalNumberOfCells();
            if(keyCol >= physicalNumberOfCells){
                throw new ExcelException("键列不能为空 [row:"+i+",col:"+keyCol+"]");
            }

            //获取键数据
            Cell keyCell = row.getCell(keyCol);
            if(keyCell == null){
                continue;
            }
            String key = transferCellData(keyCell.getCellType(),keyCell, String.class, DateFormatUtils.ISO_DATE_FORMAT, decimalFormat,creationHelper);

            //获取值数据
            V value = null;
            if(valueCol < physicalNumberOfCells){
                Cell valueCell = row.getCell(valueCol);
                if(valueCell == null){
                    continue;
                }
                value = transferCellData(valueCell.getCellType(),valueCell, clazz, DateFormatUtils.ISO_DATE_FORMAT, decimalFormat,creationHelper);
            }

            mapData.put(key, value);
        }

        return mapData;
    }

    /**
     * 从输入流导入 excel 数据,只支持一个 sheet 页
     * @param in 输入流
     * @param clazz 导入目标类
     * @param errorRowHandler 错误行处理器 {@link com.sanri.excel.poi.handler.CollectErrorRowHandler}
     * @param <T>
     * @return
     */
    public static <T> List<T> importData(InputStream in, Class<T> clazz, ErrorRowHandler<T> errorRowHandler){
        ExcelImport excelImport = clazz.getAnnotation(ExcelImport.class);
        if(excelImport == null){
            throw new ExcelException("需要在目标类加注解 ExcelImport 才可实现导入");
        }
        List<ColumnConfig> columnConfigs = ColumnConfigUtil.parseColumnConfig(clazz,false);

        List<T> data = new ArrayList<T>();
        try{
            Workbook workbook = getWorkbookFromInputStream(in);

            //真正解析 excel 流,只解析第一个 sheet 页
            int startRow = excelImport.startRow();
            CreationHelper creationHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.getSheetAt(0);
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
            for(int i = startRow;i<physicalNumberOfRows;i++){
                Row row = sheet.getRow(i);
                if(row == null){
                    //add by sanri at 2017/12/12 解决绝对空行问题
                    continue;
                }
                //判断每行的数据都为 null 跳过 at 2018/3/13
                boolean emptyRow = isEmptyRow(row);
                if(emptyRow){continue;}

                T dataItem = null;
                try {
                    dataItem = clazz.newInstance();
                    data.add(dataItem);
                } catch (Exception e) {
                    throw new ExcelException("构建新实例出错:"+e.getMessage(),e);
                }
                for (ColumnConfig columnConfig : columnConfigs) {
                    Cell cell = null;
                    Method writeMethod  = null;
                    try{
                        int index = columnConfig.getOrder();
                        writeMethod = columnConfig.getWriteMethod();
                        cell = row.getCell(index);
                        if(cell != null){		//add by sanri at 2017/09/08 解决单元格为空单元格的问题
                            invokeData(cell.getCellType(), cell, writeMethod, columnConfig, dataItem, creationHelper);
                        }
                    }catch(Exception e){
                        logger.error("数据注入出错:"+e.getMessage(),e);
                        //对注入出错的数据行进行处理,判断是否需要继续导入
                        boolean isContinue = errorRowHandler.handlerRow(cell,writeMethod,columnConfig);
                        if(isContinue){continue;}else{break;}
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(in);
        }
        return data;
    }

    /**
     *
     * 作者:sanri <br/>
     * 时间:2018-3-13下午3:18:12<br/>
     * 功能:判断当前行是否全为空,如果全为空,返回 true <br/>
     * @param row
     * @return
     */
    private static boolean isEmptyRow(Row row) {
        if (row == null) {
            return true;
        }
        short firstCellNum = row.getFirstCellNum();
        short lastCellNum = row.getLastCellNum();

        for (int i = firstCellNum; i < lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * 作者:sanri <br/>
     * 时间:2017-12-2下午4:11:26<br/>
     * 功能:从输入流获取工作薄 <br/>
     * @param in
     * @return
     */
    public static Workbook getWorkbookFromInputStream(InputStream in)throws ExcelException {
//		Workbook workbook = null;
//		if (!in.markSupported()) {
//			in = new PushbackInputStream(in, 8);
//		}
//		try {
//			if (POIFSFileSystem.hasPOIFSHeader(in)) {
//				workbook =  new HSSFWorkbook(in);
//			}
//			if (POIXMLDocument.hasOOXMLHeader(in)) {
//				workbook =  new XSSFWorkbook(OPCPackage.open(in));
//			}
//		}  catch (Exception e) {
//			throw new ParseException("excel 流解析失败", e);
//		}
//		return workbook;
        try {
            return WorkbookFactory.create(in);
        } catch (Exception e) {
            throw new ExcelException("excel 流解析失败 ",e);
        }
    }

    /**
     *
     * 作者:sanri <br/>
     * 时间:2017-12-2下午4:21:40<br/>
     * 功能:将单元格数据转换成指定类型 <br/>
     * @param cell
     * @param clazz
     * @param creationHelper CreationHelper creationHelper = workbook.getCreationHelper();
     * @return
     */
    public static <T> T transferCellData(int cellType,Cell cell,Class<T> dataType,FastDateFormat dateFormat,DecimalFormat numberFormat,CreationHelper creationHelper ){
        if(cell == null){
            // 处理单元格为绝对空的问题 add by sanri at 2017/12/12
            return null;
        }
        switch (cellType) {
            case Cell.CELL_TYPE_BOOLEAN:
                boolean booleanCellValue = cell.getBooleanCellValue();
                if(dataType == Boolean.class || dataType == boolean.class){
                    return dataType.cast(booleanCellValue);
                }
                logger.warn("boolean 单元格数据无法转换成类型:"+dataType+",在["+cell.getRowIndex()+"]行,["+cell.getColumnIndex()+"]列");
                return null;
            case Cell.CELL_TYPE_BLANK:
                //null 值无需写入
                return null;
            case Cell.CELL_TYPE_FORMULA:
                FormulaEvaluator formulaEvaluator = creationHelper.createFormulaEvaluator();
                CellValue cellValue = formulaEvaluator.evaluate(cell);
                int newCellType = cellValue.getCellType();
                return transferCellData(newCellType, cell, dataType, dateFormat, numberFormat, creationHelper);
            case Cell.CELL_TYPE_NUMERIC:
                //add by sanri at 2017/09/08 判断是否为日期单元格,使用 excel 日期进行转换
                double doubleValue = cell.getNumericCellValue();
                //根据目标类型来判断需要设置的值
                if(dataType == Date.class){
                    //目标类型为 Date
                    Date javaDate = DateUtil.getJavaDate(doubleValue);
                    return dataType.cast(javaDate);
                }

                if(dataType == String.class){
                    if( DateUtil.isCellDateFormatted(cell)){
                        //如果单元格是 date 类型,则转为 date 字符串
                        Date javaDate = DateUtil.getJavaDate(doubleValue);
                        return dataType.cast(dateFormat.format(javaDate));
                    }

                    String realValue = decimalFormat.format(doubleValue);
                    return dataType.cast(realValue);
                }

                if(dataType== int.class || dataType== Integer.class){
                    return dataType.cast(new Double(doubleValue).intValue());
                }

                if(dataType == Float.class || dataType == Float.class){
                    return dataType.cast(new Double(doubleValue).floatValue());
                }

                if(dataType == Double.class || dataType == double.class){
                    return dataType.cast(doubleValue);
                }

                if(dataType == Long.class || dataType == long.class){
                    return dataType.cast(new Double(doubleValue).longValue());
                }
                logger.error("不支持的数字类型转换,只支持[int,float,long,double]中的一种,在:"+cell.getRowIndex()+" 行,"+cell.getColumnIndex()+" 列");
                break;
            case Cell.CELL_TYPE_STRING:
                String stringCellValue = cell.getStringCellValue();

                if(ClassUtils.isPrimitiveOrWrapper(dataType)){
                    Object realData = stringCell2RealTypeValue(dataType, stringCellValue);
                    if(realData != null){
                        return dataType.cast(realData);
                    }
                    logger.error("当前列数据未注入,不支持的类型:"+dataType+",只支持[int,long,double,float,boolean,date,String]");
                    return null;
                }

                if(dataType == Date.class){
                    String pattern = dateFormat.getPattern();
                    try {
                        Date parseDate = DateUtils.parseDate(stringCellValue, new String []{pattern});
                        return dataType.cast(parseDate);
                    } catch (java.text.ParseException e) {
                        logger.error("单元格日期解析错误,给定的日期值为 :"+stringCellValue+",要求的日期格式为:"+pattern+"; 在:"+cell.getRowIndex()+" 行,"+cell.getColumnIndex()+" 列",e);
                    }
                }

                return dataType.cast(stringCellValue);
            case Cell.CELL_TYPE_ERROR:
                //作为 null 值写入
                return null;
            default:
                break;
        }
        return null;
    }

    /**
     *
     * 功能:注入数据,这里可能会有很多问题,还有数据小数点问题<br/>
     * 创建时间:2017-8-12下午10:52:05<br/>
     * 作者：sanri<br/>
     * @param cellType
     * @param cell
     * @param writeMethod
     * @param columnConfig
     * @param dataItem
     * @param creationHelper
     */
    static final  DecimalFormat decimalFormat = new DecimalFormat("0");
    private static void invokeData(int cellType,Cell cell,Method writeMethod,ColumnConfig columnConfig,Object dataItem,CreationHelper creationHelper) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Class<?> dataType = columnConfig.getDataType();
        switch (cellType) {
            case Cell.CELL_TYPE_BOOLEAN:
                boolean booleanCellValue = cell.getBooleanCellValue();
                if(dataType == Boolean.class || dataType == boolean.class){
                    writeMethod.invoke(dataItem, booleanCellValue);
                }else{
                    writeMethod.invoke(dataItem, String.valueOf(booleanCellValue));
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                //null 值无需写入
                break;
            case Cell.CELL_TYPE_FORMULA:
                FormulaEvaluator formulaEvaluator = creationHelper.createFormulaEvaluator();
                CellValue cellValue = formulaEvaluator.evaluate(cell);
                int newCellType = cellValue.getCellType();
                invokeData(newCellType, cell, writeMethod, columnConfig, dataItem, creationHelper);
                break;
            case Cell.CELL_TYPE_NUMERIC:
                //add by sanri at 2017/09/08 判断是否为日期单元格,使用 excel 日期进行转换
                double doubleValue = cell.getNumericCellValue();
                //根据目标类型来判断需要设置的值
                if(dataType == Date.class){
                    //目标类型为 Date
                    Date javaDate = DateUtil.getJavaDate(doubleValue);
                    writeMethod.invoke(dataItem, javaDate);
                }else if(dataType == String.class){
                    if( DateUtil.isCellDateFormatted(cell)){
                        //如果单元格是 date 类型,则转为 date 字符串
                        Date javaDate = DateUtil.getJavaDate(doubleValue);
                        writeMethod.invoke(dataItem, DateFormatUtils.format(javaDate,columnConfig.getPattern()));
                    }else{
                        //add by sanri at 2017/09/08 解决读出科学计数法的字符串问题;  string 读数值单元格，是否需要取精度值
                        int precision = columnConfig.getPrecision();
                        if(precision != -1) {
                            String realValue = new BigDecimal(String.valueOf(doubleValue)).setScale(precision, RoundingMode.HALF_EVEN).toPlainString();
                            writeMethod.invoke(dataItem, realValue);
                        }else{
                            writeMethod.invoke(dataItem,String.valueOf(doubleValue));
                        }
                    }
                }else if(dataType== int.class || dataType== Integer.class){
                    writeMethod.invoke(dataItem, (int)doubleValue);
                }else if(dataType == Float.class || dataType == Float.class){
                    writeMethod.invoke(dataItem, (float)doubleValue);
                }else if(dataType == Double.class || dataType == double.class){
                    writeMethod.invoke(dataItem, doubleValue);
                }else if(dataType == Long.class || dataType == long.class){
                    writeMethod.invoke(dataItem, (long)doubleValue);
                }else{
                    logger.error("不支持的数字类型转换,只支持[int,float,long,double]中的一种,在:"+columnConfig.getChinese());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                String stringCellValue = cell.getStringCellValue();
                if(columnConfig.isTrim()){
                    stringCellValue = StringUtils.trim(stringCellValue);
                }
                if(ClassUtils.isPrimitiveOrWrapper(dataType) ){
                    Object realValue = stringCell2RealTypeValue(dataType, stringCellValue);
                    if(realValue == null){
                        logger.warn("列["+columnConfig.getPropertyName()+"]数据未注入,不支持的类型:"+dataType+",只支持[int,long,double,float,boolean,date,String]");
                    }else {
                        writeMethod.invoke(dataItem, realValue);
                    }
                }else if(dataType == Date.class){
                    try {
                        writeMethod.invoke(dataItem, DateUtils.parseDate(stringCellValue, new String[]{columnConfig.getPattern()}));
                    } catch (java.text.ParseException e) {
                        logger.error("单元格日期解析错误,给定的日期值为 :"+stringCellValue+",要求的日期格式为:"+columnConfig.getPattern(),e);
                    }
                }else{
                    writeMethod.invoke(dataItem, stringCellValue);
                }
                break;
            case Cell.CELL_TYPE_ERROR:
                //作为 null 值写入
                break;
            default:
                break;
        }
    }

    /**
     * 字符串单元格，解析实际类型数据
     * @param dataType
     * @param stringCellValue
     * @return
     */
    private static Object stringCell2RealTypeValue(Class<?> dataType, String stringCellValue) {
        Object realValue = null;
        if(dataType == int.class || dataType == Integer.class){
            realValue = NumberUtils.toInt(stringCellValue);
        }else if(dataType == long.class || dataType == Long.class){
            realValue =  NumberUtils.toLong(stringCellValue);
        }else if(dataType == double.class || dataType == Double.class){
            realValue = NumberUtils.toDouble(stringCellValue);
        }else if(dataType == float.class || dataType == Float.class){
            realValue = NumberUtils.toFloat(stringCellValue);
        }else if(dataType == boolean.class || dataType == Boolean.class){
            realValue = Boolean.parseBoolean(stringCellValue);
        }
        return realValue;
    }

}
