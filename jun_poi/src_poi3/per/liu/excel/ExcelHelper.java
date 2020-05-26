package per.liu.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cdliujian1 on 2015/7/28.
 */
public class ExcelHelper<T> {

    private final Map<String, Integer> idxMap = new HashMap<String, Integer>();

    /**
     * 从文件中解析excel
     *
     * @param filename
     * @param clazz         准备读出的对象类型class
     * @param excelResolver 根据准备读出的对象类型 自定义一个excelResolver
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<T> readExcel(String filename, Class<T> clazz, ExcelResolver<T> excelResolver) throws IOException, InvalidFormatException {
        InputStream is = new FileInputStream(filename);
        Workbook workbook;
        try {
            if (filename.endsWith("xls")) {
                //未测试 excel 2003
                workbook = new HSSFWorkbook(is);
            } else {
                workbook = WorkbookFactory.create(is);
            }
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return null;
            }
            //赋值idxMap
            resolveHeader(sheet);
            setObjectField(excelResolver, "idxMap", idxMap);
            return genResultList(clazz, excelResolver, sheet);
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                //no op
            }
        }
    }

    /**
     * 导出使用xlsx格式
     *
     * @param filename
     * @param dataList
     */
    public void writeExcel(String filename, List<T> dataList, ExcelWriter<T> excelWriter) {
        if (dataList == null) {
            return;
        }
        Workbook wb = writeExcel(dataList, excelWriter);
        // 最后一步，将文件存到指定位置
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(filename);
            wb.write(fout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fout != null)
                    fout.close();
            } catch (Exception e) {
                //no op
            }
        }
    }

    /**
     * 导出使用xls格式，由于ie8的兼容性问题
     *
     * @param dataList
     */
    public Workbook writeExcel(List<T> dataList, ExcelWriter<T> excelWriter) {
        if (dataList == null) {
            return null;
        }
        //1.创建Excel工作薄对象
        Workbook wb = new HSSFWorkbook();
        //2.创建Excel工作表对象
        Sheet sheet = wb.createSheet("new Sheet");
        //3.创建Excel工作表的行
        int headerRowIdx = 0;
        //从第一行开始写内容
        int contentRowIdx = headerRowIdx + 1;
        //从第一行开始写数据
        for (T data : dataList) {
            excelWriter.setRow(sheet.createRow(contentRowIdx));
            excelWriter.writeRow(data);
            contentRowIdx++;
        }
        //最后写header
        Row header = sheet.createRow(headerRowIdx);
        excelWriter.setRow(header);
        for (String headerValue : excelWriter.getIdxMap().keySet()) {
            excelWriter.addCell(headerValue, headerValue);
        }
        //设置sheet名称和单元格内容
        wb.setSheetName(0, "Sheet1");
        //设置单元格内容   cell.setCellValue("单元格内容");
        return wb;
    }


    /**
     * 解析header头，获取每个header对应的位置
     *
     * @param sheet
     */
    private void resolveHeader(Sheet sheet) {
        Row header = sheet.getRow(sheet.getFirstRowNum());
        for (int i = 0; i < header.getLastCellNum(); i++) {
            Cell cell = header.getCell(i);
            String h = cell.getStringCellValue();
            idxMap.put(h.trim(), i);
        }
    }

    private List<T> genResultList(Class<T> clazz, ExcelResolver<T> excelTranslator, Sheet sheet) {
        List<T> results = new ArrayList<T>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            try {
                T obj = clazz.newInstance();
                setObjectField(excelTranslator, "row", row);
                if (excelTranslator.resolve(obj)) {
                    results.add(obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private void setObjectField(Object excelUtil, String filedName, Object fieldValue) {
        Field field = null;
        try {
            field = excelUtil.getClass().getSuperclass()
                    .getDeclaredField(filedName);
            field.setAccessible(true);
            field.set(excelUtil, fieldValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
