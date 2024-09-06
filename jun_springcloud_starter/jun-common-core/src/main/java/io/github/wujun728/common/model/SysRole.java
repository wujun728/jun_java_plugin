package io.github.wujun728.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.wujun728.common.enums.DataScope;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRole extends SuperEntity {
    private static final long serialVersionUID = 4497149010220586111L;
    private String code;
    private String name;
    @TableField(exist = false)
    private Long userId;
    /**
     * 数据权限字段
     */
    private DataScope dataScope;
    private Long creatorId;
}
