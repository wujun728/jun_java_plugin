package com.xxl.apm.admin.service;

import com.xxl.apm.client.message.XxlApmMsg;

import java.util.List;

/**
 * @author xuxueli 2019-01-17
 */
public interface XxlApmStoreService {

    public boolean processMsg(List<XxlApmMsg> messageList);

    public boolean cleanMsg(int msglogStorageDay);

}
