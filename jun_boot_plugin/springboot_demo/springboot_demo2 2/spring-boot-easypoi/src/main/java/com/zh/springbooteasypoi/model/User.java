package com.zh.springbooteasypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @date 2019/5/29
 */
@Data
public class User implements Serializable {

    @Excel(name = "编号", isImportField = "true_st")
    private Integer id;

    @Excel(name = "姓名", height = 20, width = 30, isImportField = "true_st")
    private String name;

    @Excel(name = "学生性别", replace = { "男_1", "女_2" }, suffix = "生", isImportField = "true_st")
    private Integer gender;

    @Excel(name = "出生日期", format = "yyyy-MM-dd", width = 20, isImportField = "true_st")
    private Date birthday;

    public User() {}

    public User(Integer id, String name, Integer gender, Date birthday) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
    }
}
