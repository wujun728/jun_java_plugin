package com.feri.fyw.entity;

import lombok.Data;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-11 16:32
 */
@Data
public class Admin {
    private Integer id;
    /**
     * name
     */
    private String name;

    /**
     * no
     */
    private String no;

    /**
     * password
     */
    private String password;

    /**
     * 标记位 1.有效 2.无效
     */
    private Integer flag;
}
