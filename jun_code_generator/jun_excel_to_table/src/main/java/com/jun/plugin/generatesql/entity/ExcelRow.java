package com.jun.plugin.generatesql.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class ExcelRow {

    @Excel(name = "No")
    private String no;

    @Excel(name = "Logical Name")
    private String desc;

    @Excel(name = "Physical Name")
    private String field;

    @Excel(name = "Data Type")
    private String dataType;

    @Excel(name = "Key")
    private String key;

    @Excel(name = "Not Null")
    private String notNull;

    @Excel(name = "Default")
    private String defaultVal;

}
