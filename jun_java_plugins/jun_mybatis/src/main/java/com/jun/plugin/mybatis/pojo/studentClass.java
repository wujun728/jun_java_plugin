package com.jun.plugin.mybatis.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/23 21:08
 */
@Data
public class studentClass {
    private Integer classId;
    private String className;
    //通过该字段将student和studentClass之间建立起来联系
    private List<student> students;
}
