package com.jun.plugin.app1.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class BizTestVo  extends BaseEntity  implements Serializable {

    public interface Retrieve{}
    public interface Delete {}
    public interface Update {}
    public interface Create {}

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
    @NotNull(message = "注册金额不能为空", groups = {Create.class,Update.class,Delete.class})
    @Size( max = 22,message = "注册金额长度限制22位")
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
    @NotNull(message = "客户全称不能为空", groups = {Create.class,Update.class,Delete.class})
    @Size( max = 255,message = "客户全称长度限制255位")
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
    @NotNull(message = "客户类型不能为空", groups = {Create.class,Update.class,Delete.class})
    @Size( max = 255,message = "客户类型长度限制255位")
    private String custype;

    /**
    * 
    */
    @ApiModelProperty("") 
    @NotNull(message = "不能为空", groups = {Create.class,Update.class,Delete.class})
    @Size( max = 255,message = "长度限制255位")
    private String state;

    /**
    * 备注
    */
    @ApiModelProperty("备注") 
    @NotNull(message = "备注不能为空", groups = {Create.class,Update.class,Delete.class})
    @Size( max = 65535,message = "备注长度限制65535位")
    private String remark;

    public BizTestVo() {}
}
