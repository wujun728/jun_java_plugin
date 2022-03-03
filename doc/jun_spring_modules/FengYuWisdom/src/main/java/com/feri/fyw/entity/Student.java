package com.feri.fyw.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 11:36
 */
@Data
@NoArgsConstructor
public class Student {
    private Integer id;
    /**
     * no
     */
    @ExcelProperty("no")
    private String no;

    /**
     * name
     */
    @ExcelProperty("name")
    private String name;

    /**
     * sex
     */
    @ExcelProperty("sex")
    private Integer sex;

    /**
     * hometown
     */
    @ExcelProperty("hometown")
    private String hometown;
    /**
     * education
     */
    @ExcelProperty("education")
    private String education;

    /**
     * gid
     */
    @ExcelProperty("gid")
    private Integer gid;

    /**
     * ctime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;

    public Student(String no, String name, Integer sex, String hometown, String education, Integer gid) {
        this.no = no;
        this.name = name;
        this.sex = sex;
        this.hometown = hometown;
        this.education = education;
        this.gid = gid;
    }
}
