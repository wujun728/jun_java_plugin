package com.cosmoplat.common.config;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import com.cosmoplat.entity.sys.SysUser;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出时：转换字典数据（可以拉取字典数据）
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Service
@Slf4j
public class GlobalExcelDictHandler implements IExcelDictHandler {

    @Override
    public String toName(String dict, Object obj, String name, Object value) {//导出
        return getDictCache(dict).get(value);
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {//导入
        return getDictCache(dict).inverse().get(name).toString();
    }

    public BiMap<Integer, String> getDictCache(String catalog) {
        BiMap<Integer, String> nameMap = HashBiMap.create();
        if (catalog.equals("unableFlag")) {
            nameMap.put(1, "是");
            nameMap.put(0, "否");
        }
        return nameMap;
    }

    public static void main(String[] args) {
        try {
            ExportParams params = new ExportParams("导出转换字典", "测试", ExcelType.XSSF);
            // 指定单元格转换字典
            params.setDictHandler(new GlobalExcelDictHandler());
            List<SysUser> list = new ArrayList<>();
            list.add(new SysUser().setUsername("1").setUnableFlag(1));
            list.add(new SysUser().setUsername("2").setUnableFlag(1));
            list.add(new SysUser().setUsername("3").setUnableFlag(1));
            list.add(new SysUser().setUsername("4").setUnableFlag(1));
            list.add(new SysUser().setUsername("5").setUnableFlag(0));
            Workbook workbook = ExcelExportUtil.exportExcel(params, SysUser.class, list);
            File saveFile = new File("excel");
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream("d:/aa.xlsx");
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            log.error("异常",e);
        }
    }

}