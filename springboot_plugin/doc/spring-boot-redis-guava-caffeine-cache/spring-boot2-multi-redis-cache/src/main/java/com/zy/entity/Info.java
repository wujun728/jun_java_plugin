package com.zy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/22.
 */
@Data
@AllArgsConstructor
public class Info implements Serializable {
    private String phone;
    private String address;

}
