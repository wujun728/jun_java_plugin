package com.jun.plugin.generatesql.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.hutool.core.io.FileUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.jun.plugin.generatesql.entity.ExcelRow;
import com.jun.plugin.generatesql.entity.GenerateProperties;
import com.jun.plugin.generatesql.entity.SqlTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadExcel {


    private GenerateProperties properties;

    private List<SqlTable> tables;

    private int sheetIndex = 1;

    private int maxSheet = 1;

    public ReadExcel(GenerateProperties properties) {
        this.properties = properties;
        this.tables = new ArrayList<>();
        this.sheetIndex = 1;
        readExcel();
    }

    private void readExcel() {
        while (maxSheet >= sheetIndex) {
            SqlTable table = new SqlTable();
            if (readTableName(table) == null) {
                throw new RuntimeException("读取不到表的定义," + sheetIndex);
            }
            List<ExcelRow> excelRows = readFiled();
            if (CollectionUtils.isEmpty(excelRows)) {
                throw new RuntimeException("读取不到表字段," + sheetIndex);
            }
            table.setRows(excelRows);
            tables.add(table);
            this.sheetIndex++;
        }
    }

    /**
     * 输出到文件
     */
    public void outputFile() {
        if (StringUtils.isBlank(properties.getExportSqlDir())) {
            throw new RuntimeException("输出目录为空");
        }
        FileUtil.writeString(toScriptStr(), new File(properties.getExportSqlDir(), "mysql_script.sql"), "UTF-8");
    }


    /**
     * 生成多个表字符串表示形式 list
     * @return
     */
    public List<String> getScriptList() {
        List<String> script = new ArrayList<>();

        for (SqlTable item : tables) {
            StringBuffer sb = new StringBuffer();
            sb.append("CREATE TABLE `").append(item.getTableName()).append("` (")
                    .append("\n");
            String key = null;
            for (ExcelRow row : item.getRows()) {
                sb.append("`").append(row.getField()).append("` ").append(row.getDataType()).append(" ");
                //Not null
                if (StringUtils.equals(StringUtils.lowerCase(row.getNotNull()), "yes")) {
                    sb.append(" NOT NULL ");
                }
                //获取主键
                if (StringUtils.equals(StringUtils.lowerCase(row.getKey()), "yes")
                        || StringUtils.equals(StringUtils.lowerCase(row.getKey()), "pk")) {
                    key = row.getField();
                }
                //是否有默认值
                if (StringUtils.isNotBlank(row.getDefaultVal())) {
                    //是否是主键
                    if (StringUtils.equals(key, row.getField())) {
                        sb.append(" ").append(row.getDefaultVal()).append(" ");
                    } else {
                        sb.append(" DEFAULT ").append(row.getDefaultVal());
                    }
                }
                //注释
                sb.append(" COMMENT '").append(row.getDesc()).append("',").append("\n");
            }
            sb.append("PRIMARY KEY (`").append(key).append("`)").append("\n").append(")");
            sb.append(" ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COMMENT='").append(item.getTableDesc())
                    .append("';");
            script.add(sb.toString());
        }
        return script;
    }

    /**
     * 返回字符串的形式
     * @return
     */
    public String toScriptStr() {
        List<String> script = getScriptList();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < script.size(); i++) {
            sb.append("-- 数据表").append(i + 1).append("\n");
            sb.append(script.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * 获取表名
     * @param table
     * @return
     */
    private SqlTable readTableName(SqlTable table) {
        ImportParams params = new ImportParams();
        params.setKeyMark("Name");
        params.setReadSingleCell(true);
        params.setTitleRows(6);
        params.setStartSheetIndex(sheetIndex - 1);
        params.setLastOfInvalidRow(7);
        ExcelImportResult<Map> result = ExcelImportUtil.importExcelMore(
                new File(properties.getExcelPath()),
                Map.class, params);
        System.err.println("result = " +result);
        this.maxSheet = result.getWorkbook().getNumberOfSheets();

        Map<String, Object> map = result.getMap();
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        String tableName = MapUtils.getString(map, "Physical Entity Name");
        String tableDesc = MapUtils.getString(map, "Logical Entity Name");
        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        table.setTableName(tableName.trim());
        table.setTableDesc(tableDesc.trim());
        return table;
    }


    /**
     * 获取字段
     * @return
     */
    private List<ExcelRow> readFiled () {
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setTitleRows(12);
        params.setStartSheetIndex(sheetIndex - 1);

        params.setVerifyHandler(obj -> {
            ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult();
            ExcelRow row = (ExcelRow) obj;
            if (StringUtils.isNotBlank(row.getField()) && StringUtils.isNotBlank(row.getDesc())
                    && StringUtils.isNotBlank(row.getDataType())) {
                result.setSuccess(true);
                //去除空格
                row.setNo(StringUtils.trim(row.getNo()));
                row.setDesc(StringUtils.trim(row.getDesc()));
                row.setField(StringUtils.trim(row.getField()));
                row.setDataType(StringUtils.trim(row.getDataType()));
                row.setKey(StringUtils.trim(row.getKey()));
                row.setNotNull(StringUtils.trim(row.getNotNull()));
                row.setDefaultVal(StringUtils.trim(row.getDefaultVal()));
            } else {
                result.setSuccess(false);
                result.setMsg("数据为空");
            }
            return result;
        });
        List<ExcelRow> list = ExcelImportUtil.importExcel(
                new File(properties.getExcelPath()),
                ExcelRow.class, params);
        return list;
    }
}
