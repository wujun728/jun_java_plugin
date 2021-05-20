package org.itcast.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author CDHong
 * @since 2019-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tbl_user")
@ApiModel(value="User对象", description="用户表实体对象")
public class User implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "真实名")
    private String relName;

    @ApiModelProperty(value = "登陆名")
    private String logName;

    @ApiModelProperty(value = "密码")
    private String logPwd;

    @ApiModelProperty(value = "登陆状态，0启用，1禁用")
    private Integer logStatus;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    private LocalDateTime createTime;


}
