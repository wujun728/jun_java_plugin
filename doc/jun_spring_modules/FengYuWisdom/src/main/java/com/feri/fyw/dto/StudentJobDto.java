package com.feri.fyw.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-22 11:35
 */
@Data
public class StudentJobDto {
    private Integer id;
    private String sname;
    private String salary;
    private String edu;
    private String company;
    private String city;
    private String school;
    private String major;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date jobdate;
}
