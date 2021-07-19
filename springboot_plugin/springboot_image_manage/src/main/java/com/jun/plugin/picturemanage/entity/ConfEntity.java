package com.jun.plugin.picturemanage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/1 14:35
 */
@Data
@TableName("conf")
public class ConfEntity {

    @TableId(type = IdType.INPUT)
    private String key;

    private String value;

    @JSONField(format = "yyyy/MM/dd HH:mm")
    private Date createTime;

}
