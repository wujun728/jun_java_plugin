package org.typroject.tyboot.api.face.systemctl.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wujun
 * @since 2018-12-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("systemctl_operation_record")
public class OperationRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

	@TableField("OPERATION_TYPE")
	private String operationType;




	
    /**
     * 关联对象类型(用户头像，帖子，帖子评论，帖子语音....）
     */
	@TableField("ENTITY_TYPE")
	private String entityType;
    /**
     * 关联实体id
     */
	@TableField("ENTITY_ID")
	private String entityId;
	@TableField("CREATE_TIME")
	private Date createTime;

	@TableField("USER_ID")
	private String userId;

}
