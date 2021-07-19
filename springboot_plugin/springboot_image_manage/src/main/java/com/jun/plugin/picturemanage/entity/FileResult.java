package com.jun.plugin.picturemanage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.jun.plugin.picturemanage.conf.Constant;

import lombok.Data;

import java.sql.Date;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/11/4 11:21
 */
@Data
public class FileResult {

    private String fileName;

    private String fileType;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    private String size;

    private String url;

    private Constant.FileType type;
}
