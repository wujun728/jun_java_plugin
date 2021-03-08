package org.typroject.tyboot.component.opendata.push.jpush;

import cn.jpush.api.push.model.*;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.Map;

/**
 * Created by yaohelang on 2017/10/1.
 */
public class PayLoadBuilder {


    public static PushPayload buildPushObject_all_alias_alert(String [] alias,String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(content))
                .build();
    }

    public static PushPayload buildPushObject_all_alia_alert(String []  alia, String content, Map<String,String> exr) {
        Message message = null;
        if(!ValidationUtil.isEmpty(exr))
        {
            Message.Builder builder = Message.newBuilder();
            for(String key:exr.keySet())
            {
                builder.addExtra(key,exr.get(key));
            }
            builder.setMsgContent(content);
            message = builder.build();
        }


        PushPayload.Builder pushPayloadBuilder =  PushPayload.newBuilder();

        pushPayloadBuilder.setPlatform(Platform.all());
        pushPayloadBuilder.setAudience(Audience.alias(alia));
        pushPayloadBuilder.setNotification(Notification.alert(content));

        if(!ValidationUtil.isEmpty(message))
        {
            pushPayloadBuilder.setMessage(message);
        }

        PushPayload pushPayload = pushPayloadBuilder.build();


        return pushPayload;
    }
}
