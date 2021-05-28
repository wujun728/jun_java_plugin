package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 操作计数 model
 * </p>
 *
 * @author Wujun
 * @since 2018-12-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperationTimesModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 操作计数
     */
    private int operationTimes;
    /**
     * 关联对象类型(商户，商品。。。.）
     */
    private String entityType;
    /**
     * 关联实体id
     */
    private String entityId;
    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 操作用户
     */
    private String UserId;
}
