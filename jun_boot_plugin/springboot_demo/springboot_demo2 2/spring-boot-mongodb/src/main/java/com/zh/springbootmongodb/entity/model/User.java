package com.zh.springbootmongodb.entity.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @date 2019/5/29
 */
@Data
public class User {

    private Integer id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotNull(message = "年龄不能为空")
    private Integer age;

}
