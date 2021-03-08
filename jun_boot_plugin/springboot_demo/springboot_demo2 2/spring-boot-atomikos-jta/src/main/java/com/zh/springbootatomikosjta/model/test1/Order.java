package com.zh.springbootatomikosjta.model.test1;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wujun
 * @date 2019/6/6
 */
@Data
@ToString
public class Order {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Date createTime;

    private String rmk;

}