package io.github.wujun728.snakerflow.ext.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.wujun728.snakerflow.process.BaseFlowStatus;
//import io.github.wujun728.system.entity.SysUser;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * 
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExtLeave extends BaseFlowStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "leave_id", type = IdType.AUTO)
    private Long leaveId;

    /**
     * 请假天数
     */
    private Integer leaveDay;

    /**
     * 请假原因
     */
    private String leaveReason;

    /**
     * 请假人id
     */
    private String leaveUserId;

//    @TableField(exist = false)
//    private SysUser createUser;

    /**
     * 创建人
     */
    @TableField(value = "create_by" ,fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建人部门
     */
    @TableField(value = "create_dept_id" ,fill = FieldFill.INSERT)
    private Long createDeptId;

    /**
     * 创建时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time" ,fill = FieldFill.INSERT)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
