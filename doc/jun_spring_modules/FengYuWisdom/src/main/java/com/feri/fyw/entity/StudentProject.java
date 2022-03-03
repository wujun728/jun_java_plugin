package com.feri.fyw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-22 09:15
 */
@Data
public class StudentProject {
    private Integer id;
    private Integer sid;
    private Integer pid;
    private String info;
    private Integer score;
    private Integer type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;
}