package cn.springmvc.mybatis.service.simple.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.springmvc.mybatis.common.utils.DateUtil;
import cn.springmvc.mybatis.common.utils.UUIDUtil;
import cn.springmvc.mybatis.common.utils.excel.ExcelUtil;
import cn.springmvc.mybatis.entity.simple.ImportExcel;
import cn.springmvc.mybatis.mapper.simple.ImportExcelMapper;
import cn.springmvc.mybatis.service.simple.ImportExcelService;

@Service
public class ImportExcelServiceImpl implements ImportExcelService {

    private static final Logger log = LoggerFactory.getLogger(ImportExcelServiceImpl.class);

    @Autowired
    private ImportExcelMapper importExcelMapper;

    @Override
    public void toImport(InputStream stream) {
        try {
            log.info("#import excel data...");
            Workbook wb = new HSSFWorkbook(stream);//v.2007 office
            //Workbook wb = new XSSFWorkbook(stream);// v.2007+ office
            List<Map<String, ImportExcel>> list = new ArrayList<Map<String, ImportExcel>>();
            int sheetNum = wb.getNumberOfSheets();
            for (int sn = 0; sn < sheetNum; sn++) {
                Sheet sheet = wb.getSheetAt(sn);// 获取当前sheet 
                String year = sheet.getSheetName();//把sheet名称当月份了
                Map<String, ImportExcel> map = new LinkedHashMap<String, ImportExcel>();
                int rows = sheet.getPhysicalNumberOfRows();// 获取总行号
                if (rows > 0) {
                    for (int i = 1; i < rows; i++) {
                        Row row = sheet.getRow(i);

                        String userName = ExcelUtil.getStringCellValue(row, 0);//员工名称
                        Date time = ExcelUtil.getDateCellValue(row, 1);//日期时间
                        String date = DateUtil.dateToString(time, DateUtil.fm_yyyy_MM_dd);//日期
                        String remark = ExcelUtil.getStringCellValue(row, 2);//备注
                        String key = userName + date;
                        boolean am = false;
                        GregorianCalendar ca = new GregorianCalendar();
                        ca.setTime(time);
                        int ampm = ca.get(GregorianCalendar.AM_PM);// 结果为“0”是上午 结果为“1”是下午
                        if (ampm == 0)
                            am = true;
                        log.info("# year={} , key={} , am={} , time={} , date={} , remark={}", year, key, am, DateUtil.dateToString(time, DateUtil.fm_yyyy_MM_dd_HHmmss), date, remark);
                        ImportExcel excel = null;
                        if (map.containsKey(key)) {
                            excel = map.get(key);
                        } else {
                            excel = new ImportExcel();
                            excel.setId(UUIDUtil.getRandom32PK());
                        }
                        excel.setUserName(userName);
                        excel.setYear(year);
                        excel.setRemark(remark);
                        if (am) {
                            excel.setStartDate(date);
                            excel.setStartTime(time);
                        } else {
                            excel.setEndDate(date);
                            excel.setEndTime(time);
                        }
                        map.put(key, excel);
                    }

                }
                list.add(map);
            }
            wb.close();
            addImportExcel(list);
        } catch (Exception e) {
            log.error("## import excel fail , error message={}", e.getLocalizedMessage());
        }
    }

    public void addImportExcel(List<Map<String, ImportExcel>> list) {
        try {
            for (Map<String, ImportExcel> map : list) {
                for (Entry<String, ImportExcel> entry : map.entrySet()) {
                    log.info("# ImportExcel={}", JSON.toJSONString(entry.getValue()));
                    importExcelMapper.insert(entry.getValue());
                }
            }

            importExcelMapper.updateStartTime();
            importExcelMapper.updateEndTime();
            importExcelMapper.updateEndTimeByWeekend();
        } catch (Exception e) {
            log.error("## addImportExcel fail , error message={}", e.getLocalizedMessage());
        }
    }

}
