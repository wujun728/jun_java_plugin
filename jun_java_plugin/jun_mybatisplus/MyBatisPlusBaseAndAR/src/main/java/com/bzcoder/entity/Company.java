package com.bzcoder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动模式
 * @author BaoZhou
 * @date 2018/10/1
 */
@Data
public class Company extends Model<Company> {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String owner;
    private String location;
    private Integer status;
    /**
     * @return 返回当前表的主键
     */
    @Override
    protected Serializable pkVal() {
        return id;
    }
}


