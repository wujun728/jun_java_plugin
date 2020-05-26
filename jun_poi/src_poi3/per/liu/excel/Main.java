package per.liu.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdliujian1 on 2015/7/30.
 */
public class Main {

    public static final String filename = "E:\\students12.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException {
        //读支持.xls和.xlsx
        testRead();
        //写入的是.xls文件，因为.xlsx兼容.xls
//        testWrite();
    }

    private static void testWrite() {
        ExcelHelper<ExcelObj> excelHelper = new ExcelHelper<ExcelObj>();
        excelHelper.writeExcel(filename, new ArrayList<ExcelObj>(), new ExcelWriter<ExcelObj>() {
            @Override
            public void writeRow(ExcelObj obj) {
                addCell("内容", obj.getContent());
                addCell("数字", obj.getNum());
                addCell("链接", obj.getUrl());
            }
        });
    }

    private static void testRead() throws IOException, InvalidFormatException {
        ExcelHelper<ExcelObj> excelHelper = new ExcelHelper<ExcelObj>();
        List<ExcelObj> list = excelHelper.readExcel(filename, ExcelObj.class, new ExcelResolver<ExcelObj>() {
            @Override
            public boolean resolve(ExcelObj obj) {
                String value = getCellValue("数字");
                if (value != null && !value.trim().equals("")) {
                    obj.setNum((int) (double) Double.valueOf(value));
                }
                obj.setContent(getCellValue("内容"));
                obj.setUrl(getCellValue("链接"));
                return true;
            }
        });
        System.out.println(list.size());
    }


}
