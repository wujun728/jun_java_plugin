package com.huan.study.cloud.alibaba.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author huan.fu 2020/10/23 - 14:22
 */
@Data
@Builder
@AllArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private Long price;
    private Date craeteTime;
}
