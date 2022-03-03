package com.feri.fyw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-21 10:33
 */
@Data
public class Complaint {
    private Integer id;
    private Integer sid;
    private String info;
    private Integer flag;//标记位 1.未处理 2.已处理
    private Integer aid;
    private String handle;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;
}
