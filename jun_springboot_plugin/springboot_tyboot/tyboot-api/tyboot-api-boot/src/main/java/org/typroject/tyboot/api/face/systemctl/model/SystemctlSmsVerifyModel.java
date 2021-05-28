package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 验证码发送记录 model
 * </p>
 *
 * @author Wujun
 * @since 2018-12-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemctlSmsVerifyModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 短信编号（可以自己生成，也可以第三方复返回，或者模板编号）
     */
    private String smsCode;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 短信类型;登录验证码短信，修改密码的短信
     */
    private String smsType;
    /**
     * '验证码'
     */
    private String smsVerify;
    /**
     * 发送时间
     */
    private Date sendTime;
}
