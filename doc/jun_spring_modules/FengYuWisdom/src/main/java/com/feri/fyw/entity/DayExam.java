package com.feri.fyw.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 09:56
 */
@Data
public class DayExam {
    private Integer id;
    @ExcelProperty("sid")
    private Integer sid;
    @ExcelProperty("score")
    private Integer score;
    @ExcelProperty("info")
    private String info;
    @ExcelProperty("cdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date cdate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;
}