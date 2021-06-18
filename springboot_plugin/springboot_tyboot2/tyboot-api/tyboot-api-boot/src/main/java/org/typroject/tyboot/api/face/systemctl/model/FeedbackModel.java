package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

/**
 * <p>
 * 用户反馈 model
 * </p>
 *
 * @author Wujun
 * @since 2017-12-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FeedbackModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    private String content;
    /**
     * 联系方式
     */
    private String contact;

    private String userId;

}
