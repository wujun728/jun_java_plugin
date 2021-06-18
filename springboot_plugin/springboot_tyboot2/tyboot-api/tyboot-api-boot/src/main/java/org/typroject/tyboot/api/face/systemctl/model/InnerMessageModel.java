package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 内部消息 model
 * </p>
 *
 * @author Wujun
 * @since 2018-12-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerMessageModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 目标用户id
     */
    private String targetUserId;
    /**
     * 消息内容
     */
    private String msgContent;
    /**
     * 消息类型：点赞,评论
     */
    private String messageType;

    private String userId;

    private String messageStatus;

    private Date createTime;
}
