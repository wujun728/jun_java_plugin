package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.util.Date;

/**
 * <p>
 * 内部消息
 * </p>
 *
 * @author Wujun
 * @since 2018-12-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("systemctl_inner_message")
public class InnerMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 目标用户id
     */
	@TableField("TARGET_USER_ID")
	private String targetUserId;
    /**
     * 消息内容
     */
	@TableField("MSG_CONTENT")
	private String msgContent;
    /**
     * 消息类型：点赞,评论
     */
	@TableField("MESSAGE_TYPE")
	private String messageType;

	@TableField("USER_ID")
	private String userId;


	@TableField("MESSAGE_STATUS")
	private String messageStatus;



	@TableField("CREATE_TIME")
	private Date createTime;
}
