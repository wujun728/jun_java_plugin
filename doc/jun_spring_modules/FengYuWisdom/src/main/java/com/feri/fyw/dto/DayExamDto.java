package com.feri.fyw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:00
 */
@Data
public class DayExamDto {
    private Integer id;
    private Integer sid;
    private String sname;
    private Integer score;
    private String info;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date cdate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ctime;
}
