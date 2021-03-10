package org.typroject.tyboot.demo.face.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("public_user_info")
public class PublicUserInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @TableField("USER_ID")
    private String userId;

    @TableField("AGENCY_CODE")
    private String agencyCode;
    /**
     * 昵称
     */
    @TableField("NICK_NAME")
    private String nickName;

    /**
     * 联系电话1
     */
    @TableField("MOBILE")
    private String mobile;
    /**
     * 性别
     */
    @TableField("GENDER")
    private String gender;

    /**
     * 注册日期
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 年龄
     */
    @TableField("USER_AGE")
    private Integer userAge;

    @TableField("EMAIL")
    private String email;


}
