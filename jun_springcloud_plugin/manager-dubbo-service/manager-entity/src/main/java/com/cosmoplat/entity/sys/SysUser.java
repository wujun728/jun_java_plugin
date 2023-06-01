package com.cosmoplat.entity.sys;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.cosmoplat.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SysUser extends BaseEntity implements Serializable {
    @TableId
    private String id;

    @Excel(name = "账号名称", orderNum = "2")
    @NotBlank(message = "账号不能为空")
    private String username;
    @JSONField(serialize = false)
    private String salt;

    @NotBlank(message = "密码不能为空")
    @JSONField(serialize = false)
    private String password;

    private String phone;

    //    @NotBlank(message = "所属机构不能为空")
    private String deptId;

//    @TableField(exist = false)
//    private String deptName;

//    @TableField(exist = false)
//    private String deptNo;


    @Excel(name = "真实名称", orderNum = "4")
    private String realName;

    @Excel(name = "昵称", orderNum = "6")
    private String nickName;

    @Excel(name = "邮箱", orderNum = "8")
    private String email;

    private Integer sex;

    @Excel(name = "是否禁用", orderNum = "10", replace = {"否_0", "是_1"})
    @TableField(fill = FieldFill.INSERT)
    private Integer unableFlag;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    private String createId;

    private String updateId;

    private Integer createWhere;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private List<String> roleIds;
}