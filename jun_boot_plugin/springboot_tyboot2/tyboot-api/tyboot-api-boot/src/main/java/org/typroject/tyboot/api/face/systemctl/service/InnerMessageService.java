package org.typroject.tyboot.api.face.systemctl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.api.face.systemctl.model.InnerMessageModel;
import org.typroject.tyboot.api.face.systemctl.orm.dao.InnerMessageMapper;
import org.typroject.tyboot.api.face.systemctl.orm.entity.InnerMessage;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * 简单内部消息 服务类
 * </p>
 *
 * @author Wujun
 * @since 2018-12-12
 */
@Component
public class InnerMessageService extends BaseService<InnerMessageModel, InnerMessage, InnerMessageMapper>  {


    //已读RECREAD 未读UNREAD
    public static final String STATUS_RECREAD = "RECREAD";//已读
    public static final String STATUS_UNREAD = "UNREAD";//未读UNREAD

    public static final String INCOMING_MESSAGE = "INCOMING_MESSAGE";//有新消息
    public static final String NO_MESSAGE = "NO_MESSAGE";//无新消息


    public InnerMessageModel createMessage(String msgContent, String targetUserId, String messageType) throws Exception {
        InnerMessageModel messageModel = new InnerMessageModel();
        messageModel.setUserId(RequestContext.getExeUserId());
        messageModel.setMsgContent(msgContent);
        messageModel.setMessageType(messageType);
        messageModel.setTargetUserId(targetUserId);
        messageModel.setMessageStatus(STATUS_UNREAD);
        messageModel.setCreateTime(new Date());
        return this.createWithModel(messageModel);
    }


    public Page queryForInnerMessagePage(Page page, String targetUserId, String messageType) throws Exception {
        Page rePage = this.queryForPage(page, "CREATE_TIME", false, targetUserId, messageType);

        List<InnerMessageModel> msgList = rePage.getRecords();
        if (!ValidationUtil.isEmpty(msgList)) {
            List<InnerMessage> entitis = new ArrayList<>();
            for (InnerMessageModel msgModel : msgList) {
                msgModel.setMessageStatus(STATUS_RECREAD);
                entitis.add(Bean.toPo(msgModel, new InnerMessage()));
            }
            //将状态更新为已读@TODO 挪到事件机制中处理
            this.updateBatchById(entitis);
        }
        return rePage;
    }

    /**
     * 获取当前用户新消息个数
     *
     * @param targetUserId
     * @return
     */
    public Integer queryNewMsgCount(String targetUserId, String messageStatus) throws Exception {
        return this.queryCount(targetUserId, messageStatus);
    }


    private List<InnerMessageModel> queryForMessageList(String targetUserId, String messageStatus) throws Exception {
        return this.queryForList(null, false, targetUserId, messageStatus);
    }


}
