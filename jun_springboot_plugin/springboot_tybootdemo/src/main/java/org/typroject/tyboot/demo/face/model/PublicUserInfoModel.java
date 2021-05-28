package org.typroject.tyboot.demo.face.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class PublicUserInfoModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String agencyCode;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 联系电话1
     */
    private String mobile;
    /**
     * 性别
     */
    private String gender;

    /**
     * 注册日期
     */
    private Date createTime;


    /**
     * 年龄
     */
    private Integer userAge;


    private String email;

}
