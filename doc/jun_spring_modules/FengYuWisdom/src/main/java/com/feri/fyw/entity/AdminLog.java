package com.feri.fyw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:45
 */
@Data
@NoArgsConstructor
public class AdminLog {
    private Integer id;
    private Integer aid;
    private Integer type;
    private String info;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date ctime;

    public AdminLog(Integer aid, Integer type, String info) {
        this.aid = aid;
        this.type = type;
        this.info = info;
    }
}
