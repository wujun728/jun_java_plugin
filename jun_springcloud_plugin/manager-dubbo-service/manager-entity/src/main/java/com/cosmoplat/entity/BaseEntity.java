package com.cosmoplat.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * BaseEntity
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class BaseEntity implements Serializable {

    @JSONField(serialize = false)
    @TableField(exist = false)
    private int page = 1;

    @JSONField(serialize = false)
    @TableField(exist = false)
    private int limit = 10;
}
