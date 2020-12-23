package com.thinkingcao.silence.ssm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private Integer age;
    private Date bornDate;
    private String headImg;
}