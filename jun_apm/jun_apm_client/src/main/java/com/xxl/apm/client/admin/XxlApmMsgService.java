package com.xxl.apm.client.admin;

import com.xxl.apm.client.message.XxlApmMsg;

import java.util.List;

/**
 * @author xuxueli 2018-12-29 17:16:33
 */
public interface XxlApmMsgService {

    public boolean beat();

    public boolean report(List<XxlApmMsg> msgList);

}
