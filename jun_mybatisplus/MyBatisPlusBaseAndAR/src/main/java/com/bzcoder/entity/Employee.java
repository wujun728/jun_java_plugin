package com.bzcoder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author BaoZhou
 * @date 2018/10/1
 */
@Data
public class Employee {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer age;
}


