package com.springboot.springbootredis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Version: 1.0
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 662692455422902539L;
    private Long id;
    private String name;
    private int age;
}
