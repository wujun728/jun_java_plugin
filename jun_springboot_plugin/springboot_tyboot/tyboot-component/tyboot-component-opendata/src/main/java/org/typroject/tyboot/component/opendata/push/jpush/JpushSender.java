package org.typroject.tyboot.component.opendata.push.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

/**
 * Created by yaohelang on 2017/10/1.
 */
public class JpushSender {


    private String masterSecret;
    private String appKey;

    private static  JPushClient jpushClient;



    public JpushSender (String masterSecret,String appKey)
    {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        jpushClient = new JPushClient(masterSecret, appKey, null, ClientConfig.getInstance());
    }



    public  void sendPush(PushPayload payload) throws Exception
    {
        PushResult result = jpushClient.sendPush(payload);
        if(ValidationUtil.isEmpty(result))
        {

        }
    }
}
