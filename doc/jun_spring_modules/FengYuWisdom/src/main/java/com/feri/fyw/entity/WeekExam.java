package com.feri.fyw.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 09:56
 */
@Data
public class WeekExam {
    private Integer id;
    @ExcelProperty("sid")
    private Integer sid;
    @ExcelProperty("score")
    private Integer score;
    @ExcelProperty("week")
    private Integer week;
    @ExcelProperty("info")
    private String info;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;
}
