package com.jun.plugin.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class blog {
    private String id;
    private String title;
    private String authOr;
    private String create_time;
    private Integer views;
    private Boolean sex;
}
