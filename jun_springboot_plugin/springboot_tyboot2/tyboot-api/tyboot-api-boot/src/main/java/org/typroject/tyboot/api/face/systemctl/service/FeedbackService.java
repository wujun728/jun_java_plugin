package org.typroject.tyboot.api.face.systemctl.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.api.face.systemctl.model.FeedbackModel;
import org.typroject.tyboot.api.face.systemctl.orm.dao.FeedbackMapper;
import org.typroject.tyboot.api.face.systemctl.orm.entity.Feedback;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.rdbms.service.BaseService;


/**
 * <p>
 * 用户反馈 服务类
 * </p>
 *
 * @author Wujun
 * @since 2017-12-01
 */
@Component
public class FeedbackService extends BaseService<FeedbackModel, Feedback, FeedbackMapper> {

    public Page<FeedbackModel> queryForfeedPage(Page page, String userId, String contact) throws Exception {
        return this.queryForPage(page, "REC_DATE", false, userId, contact);
    }

}
