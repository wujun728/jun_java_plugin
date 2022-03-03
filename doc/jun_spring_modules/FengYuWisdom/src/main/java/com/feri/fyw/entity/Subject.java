package com.feri.fyw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:52
 */
@Data
public class Subject {
    private Integer id;
    private String name;
    private Integer weeks;
    private String syllabus; //课程大纲的版本号
    private Integer flag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;
}
