package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * model
 * </p>
 *
 * @author Wujun
 * @since 2018-12-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperationRecordModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String operationType;
    /**
     * /**
     * 关联对象类型(用户头像，帖子，帖子评论，帖子语音....）
     */
    private String entityType;
    /**
     * 关联实体id
     */
    private String entityId;
    private Date createTime;


    private String userId;

}
