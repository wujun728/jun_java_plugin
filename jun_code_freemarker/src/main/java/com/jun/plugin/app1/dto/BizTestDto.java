package com.jun.plugin.app1.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.jun.plugin.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @description 客户信息
 * @author Wujun
 * @date 2024-03-08
 */
@Data
@ApiModel("客户信息")
public class BizTestDto  extends BaseEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 
    */
    @ApiModelProperty("") 
    private Long id;

    /**
    * 客户名称
    */
    @ApiModelProperty("客户名称") 
    private String cusname;

    /**
    * 注册金额
    */
    @ApiModelProperty("注册金额") 
    private String money;

    /**
    * 客户描述
    */
    @ApiModelProperty("客户描述") 
    private String cusdesc;

    /**
    * 客户全称
    */
    @ApiModelProperty("客户全称") 
    private String fullname;

    /**
    * 注册时间
    */
    @ApiModelProperty("注册时间") 
    private Date createDate;

    /**
    * 客户类型
    */
    @ApiModelProperty("客户类型") 
    private String custype;

    /**
    * 
    */
    @ApiModelProperty("") 
    private String state;

    /**
    * 备注
    */
    @ApiModelProperty("备注") 
    private String remark;

    public BizTestDto() {}
}
