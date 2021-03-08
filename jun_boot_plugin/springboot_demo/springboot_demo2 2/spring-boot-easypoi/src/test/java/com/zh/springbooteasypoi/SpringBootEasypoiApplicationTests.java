package com.zh.springbooteasypoi;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import com.zh.springbooteasypoi.model.User;
import com.zh.springbooteasypoi.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootEasypoiApplicationTests {

    private static final String WIKI = "http://easypoi.mydoc.io";

    private static List<User> dataList = new ArrayList<>();

    static {
        dataList.add(new User(1,"张三",1, DateUtil.parse("1992-02-17")));
        dataList.add(new User(2,"李四",1, DateUtil.parse("1994-09-10")));
        dataList.add(new User(3,"王五",2, DateUtil.parse("1992-08-12")));
        dataList.add(new User(4,"赵六",2, DateUtil.parse("1995-06-25")));
        dataList.add(new User(5,"孙七",2, DateUtil.parse("1991-12-30")));
        dataList.add(new User(6,"周八",1, DateUtil.parse("1990-11-02")));
        dataList.add(new User(7,"吴九",1, DateUtil.parse("1999-03-11")));
        dataList.add(new User(7,"郑十",2, DateUtil.parse("1990-04-07")));
    }

    @Test
    public void exportSimple07ExcelTest() {
        try {
            ExcelUtil.exportSimple07Excel("D:\\工作\\资料\\资料\\测试1.xlsx",User.class,dataList);
            log.info("====================Excel导出成功=====================");
        } catch (IOException e) {
            log.error("====================excel导出失败=====================");
            log.error(e.getMessage(),e);
        }
    }

    @Test
    public void exportExcelWithParamTest() {
        try {
            ExportParams params = new ExportParams("用户列","用户", ExcelType.HSSF);
            ExcelUtil.exportExcelWithParam("D:\\工作\\资料\\资料\\测试2.xls",params,User.class,dataList);
            log.info("====================Excel导出成功=====================");
        } catch (IOException e) {
            log.error("====================excel导出失败=====================");
            log.error(e.getMessage(),e);
        }
    }

    @Test
    public void exportBigExcelWithParamTest() {
        try {
            dataList.clear();
            for (int i=0;i<= 100000;i++){
                dataList.add(new User(i,"张" + i,(i & 2) == 2 ? 1 : 2, DateUtil.parse("1992-02-17")));
            }
            ExcelUtil.exportBigExcelWithParam("D:\\工作\\资料\\资料\\测试3.xls",null,User.class,dataList);
            log.info("====================Excel导出成功=====================");
        } catch (IOException e) {
            log.error("====================excel导出失败=====================");
            log.error(e.getMessage(),e);
        }
    }

    @Test
    public void readExcelTest() {
        List<User> list = ExcelUtil.readExcel("D:\\工作\\资料\\资料\\测试1.xlsx",User.class);
        log.info("====================Excel导出成功=====================");
        log.info("===================={}=====================",list);
    }

    @Test
    public void readExcelByParamTest() {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        List<User> list = ExcelUtil.readExcelByParam("D:\\工作\\资料\\资料\\测试2.xls",User.class,params);
        log.info("====================Excel导出成功=====================");
        log.info("===================={}=====================",list);
    }
}
