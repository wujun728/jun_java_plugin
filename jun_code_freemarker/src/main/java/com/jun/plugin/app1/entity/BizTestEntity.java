package com.jun.plugin.app1.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jun.plugin.common.entity.BaseEntity;

/**
 * @description 客户信息
 * @author Wujun
 * @date 2024-03-08
 */
@Data
@TableName("biz_test")
public class BizTestEntity  extends BaseEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @TableId(value = "id" ,type = IdType.AUTO )
    private Long id;

    /**
    * 客户名称
    */
    @TableField(value = "cusname" )
    private String cusname;

    /**
    * 注册金额
    */
    @TableField(value = "money" )
    private String money;

    /**
    * 客户描述
    */
    @TableField(value = "cusdesc" )
    private String cusdesc;

    /**
    * 客户全称
    */
    @TableField(value = "fullname" )
    private String fullname;

    /**
    * 注册时间
    */
    @TableField(value = "create_date" )
    private Date createDate;

    /**
    * 客户类型
    */
    @TableField(value = "custype" )
    private String custype;

    /**
    * 
    */
    @TableField(value = "state" )
    private String state;

    /**
    * 备注
    */
    @TableField(value = "remark" )
    private String remark;

    public BizTestEntity() {}
}
