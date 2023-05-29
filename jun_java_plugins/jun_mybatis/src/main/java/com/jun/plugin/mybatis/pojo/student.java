package com.jun.plugin.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LB
 * @version 1.0
 * @date 2020/12/22 17:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class student {
    private Integer id;
    private String name;
    private Boolean sex; //true男 false女
    private String classId;
}
