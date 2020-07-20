package com.zccoder.space.interview.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体
 *
 * @author zc
 * @date 2020/05/02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 年龄
     */
    private Integer age;

}
