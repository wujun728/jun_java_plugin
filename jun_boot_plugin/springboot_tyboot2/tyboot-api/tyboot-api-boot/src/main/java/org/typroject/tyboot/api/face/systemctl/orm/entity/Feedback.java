package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

/**
 * <p>
 * 用户反馈
 * </p>
 *
 * @author Wujun
 * @since 2017-12-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("systemctl_feedback")
public class Feedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

	@TableField("CONTENT")
	private String content;
    /**
     * 联系方式
     */
	@TableField("CONTACT")
	private String contact;

	@TableField("USER_ID")
	private String userId;
}
