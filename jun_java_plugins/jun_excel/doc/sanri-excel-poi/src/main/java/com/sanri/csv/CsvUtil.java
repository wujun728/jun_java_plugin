package com.sanri.csv;

import com.sanri.excel.poi.ColumnConfig;
import com.sanri.excel.poi.ColumnConfigUtil;
import com.sanri.excel.poi.annotation.ExcelExport;
import com.sanri.excel.poi.annotation.ExcelImport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * csv 工具类
 * @param <T>
 */
public class CsvUtil<T> {
    //CSV文件分隔符
    private static final String NEW_LINE_SEPARATOR="\n";
    //初始化csvformat
    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

    private static Logger logger = LoggerFactory.getLogger(CsvUtil.class);

    /**
     * 导出数据到文件;  支持 list<Bean> 导出
     * 需要 Bean 有 ExcelExport 注解 列有 ExcelColumn 注解
     * 只需要列有 中文注解信息和顺序信息即可
     * @param filePath
     * @param data
     * @param charset 建议使用  gbk 编码,用 excel 打开默认是 gbk 编码; 用 utf-8 编码用于程序解析
     */
    public static <T> File export(String filePath, List<T> data, Charset charset) throws IOException {
        if(data == null || data.isEmpty()){
            return null;
        }

        T firstData = data.get(0);
        Class<?> dataTypeClass = firstData.getClass();
        ExcelExport excelExport = dataTypeClass.getAnnotation(ExcelExport.class);
        if(excelExport == null ){
            throw new IllegalArgumentException("导出类 "+dataTypeClass.getName()+" 需要配置 "+ExcelExport.class.getName()+" 注解才可以导出");
        }

        CSVPrinter csvPrinter = null;
        File csvFile = null;
        try {
            List<ColumnConfig> columnConfigs = ColumnConfigUtil.parseColumnConfig(dataTypeClass, false);
            if(columnConfigs.isEmpty()){
                throw new IllegalArgumentException(dataTypeClass.getName()+" 请配置需要导出的列");
            }

            //对属性进行排序
            Collections.sort(columnConfigs);

            //得得数据列的头部
            String [] heads = new String[columnConfigs.size()];
            for (int i=0;i<columnConfigs.size();i++) {
                ColumnConfig columnConfig =  columnConfigs.get(i);
                String chinese = columnConfig.getChinese();
                String propertyName = columnConfig.getPropertyName();

                //中文列为空,使用属性名称
                if(StringUtils.isBlank(chinese)){
                    chinese = propertyName;
                }

                heads[i] = chinese;
            }

            Map<String, PropertyDescriptor> propertyMap = ColumnConfigUtil.getPropertyMap(dataTypeClass);
            //遍历获取数据行
            List<String []> records = new ArrayList<String []>();
            for (int j=0 ;j<data.size();j++) {
                T currentData =  data.get(j);
                String [] values = new String[columnConfigs.size()];
                records.add(values);
                //遍历每一数据列
                for (int i=0;i<columnConfigs.size();i++) {
                    ColumnConfig columnConfig  = columnConfigs.get(i);
                    String propertyName = columnConfig.getPropertyName();
                    PropertyDescriptor propertyDescriptor = propertyMap.get(propertyName);
                    Object propertyValue = ColumnConfigUtil.getPropertyValue(propertyDescriptor, currentData);
                    if(propertyValue == null){
                        values[i] = null;
                    }else {
                        Class<?> dataType = columnConfig.getDataType();
                        if(dataType == Date.class){
                            Date propertyDate = (Date) propertyValue;
                            String pattern = columnConfig.getPattern();
                            if(StringUtils.isNotEmpty(pattern)){
                                try{
                                    values[i] = DateFormatUtils.format(propertyDate,pattern);
                                }catch (Exception e){
                                    logger.error("第 "+(j + 1)+" 行日期格式化出错,列为:"+columnConfig.getPropertyName());
                                }
                            }else{
                                //格式化为 long 型值
                                values[i] = propertyDate.getTime()+"";
                            }

                        }else{
                            values [i] = ObjectUtils.toString(propertyValue);
                        }
                    }
                }
            }


            csvFile = checkFilePath(filePath);

            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(csvFile),charset);
            csvPrinter = new CSVPrinter(fileWriter, CSV_FORMAT);

            //写入头部
            csvPrinter.printRecord(heads);
            //循环写入数据
            for (String[] record : records) {
                csvPrinter.printRecord(record);
            }

        } finally {
            IOUtils.closeQuietly(csvPrinter);
        }

        return csvFile;
    }


    /**
     * 导出 csv 文件生成 List<Bean> 需要要 ExcelImport 标志,需要设置起始行以忽略头
     * @param in
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> importData(InputStream in, Class<T> clazz,Charset charset) throws IOException {
        ExcelImport excelImport = clazz.getAnnotation(ExcelImport.class);
        if(excelImport == null){
            throw new IllegalArgumentException("导出类 "+clazz.getName()+" 需要配置 "+ExcelImport.class.getName()+" 注解才可以导入");
        }
        int startRow = excelImport.startRow();

        CSVParser csvParser = null;
        try {
            List<ColumnConfig> columnConfigs = ColumnConfigUtil.parseColumnConfig(clazz, true);
            String [] headers = new String[columnConfigs.size()];

            //设置头部不读
            for (int i =0;i<headers.length;i++) {
                headers[i] = columnConfigs.get(i).getChinese();
                if(StringUtils.isBlank(headers[i])){
                    headers[i] = columnConfigs.get(i).getPropertyName();
                }
            }

            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(headers);
            //创建CSVParser对象
            InputStreamReader inputStreamReader = new InputStreamReader(in,charset);
            csvParser = new CSVParser(inputStreamReader,csvFormat);
            List<CSVRecord> records = csvParser.getRecords();

            List<T> data = new ArrayList<T>();
            //数据注入
            try {
                Map<String, PropertyDescriptor> propertyMap = ColumnConfigUtil.getPropertyMap(clazz);

                if(records != null && !records.isEmpty()){
                    for (int i=startRow;i<records.size();i++) {
                        CSVRecord csvRecord  = records.get(i);
                        T item = clazz.newInstance();
                        data.add(item);

                        //注入数据列
                        for (ColumnConfig columnConfig : columnConfigs) {
                            String chinese = columnConfig.getChinese();
                            if(StringUtils.isBlank(chinese)){
                                chinese = columnConfig.getPropertyName();
                            }
                            String value = csvRecord.get(chinese);
                            if(value == null){
                                // 空列时继续下一列
                                continue;
                            }
                            PropertyDescriptor propertyDescriptor = propertyMap.get(columnConfig.getPropertyName());

                            Class<?> dataType = columnConfig.getDataType();
                            if(dataType == Date.class){
                                Date dateValue = null;
                                String pattern = columnConfig.getPattern();
                                if(StringUtils.isNotEmpty(pattern)){
                                    try{
                                        dateValue = DateUtils.parseDate(value,new String[]{pattern});
                                    }catch (ParseException e){
                                        logger.error("第 行日期解析失败,当前日期格式为 "+pattern);
                                    }
                                }else{
                                    long timeValue = NumberUtils.toLong(value);
                                    dateValue = new Date(timeValue);
                                }

                                if(dateValue != null){
                                    ColumnConfigUtil.setPropertyValue(propertyDescriptor,item,dateValue);
                                }
                            }else if(dataType == String.class){
                                ColumnConfigUtil.setPropertyValue(propertyDescriptor,item,value);
                            }else if(dataType == Integer.class || dataType == int.class){
                                ColumnConfigUtil.setPropertyValue(propertyDescriptor,item,NumberUtils.toInt(value));
                            }else if(dataType == Long.class || dataType == long.class){
                                ColumnConfigUtil.setPropertyValue(propertyDescriptor,item,NumberUtils.toLong(value));
                            }else if(dataType == Float.class || dataType == float.class){
                                ColumnConfigUtil.setPropertyValue(propertyDescriptor,item,NumberUtils.toFloat(value));
                            }else if(dataType == Double.class || dataType == double.class){
                                ColumnConfigUtil.setPropertyValue(propertyDescriptor,item,NumberUtils.toDouble(value));
                            }else{
                                logger.error("当前列数据类型"+dataType.getName()+"不支持自动注入,只支持[int,float,long,double]中的一种,在:"+columnConfig.getChinese());
                            }
                        }
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return data;
        } finally {
            if(csvParser != null){
                csvParser.close();
            }
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * 检查文件路径 ,并创建父路径
     * @param filePath
     * @return
     */
    private static File checkFilePath(String filePath) {
        File csvFile = new File(filePath);
        if(csvFile.exists()){
            String extension = FilenameUtils.getExtension(filePath);
            // 如果文件存在,则创建一个指定时间戳的文件
            return new File(filePath + "-"+System.currentTimeMillis()+"."+extension);

        }

        File parentFile = csvFile.getParentFile();
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }
        return csvFile;
    }
}
