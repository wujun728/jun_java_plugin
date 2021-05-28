package org.typroject.tyboot.api.face.systemctl.enumeration;


import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.restful.exception.instance.BadRequest;

public enum  SmsType {



    LOGIN_VERIFYCODE("登陆验证码","aliSmsMessageHandler"),

    REPAYMENT_SUCCESS("还款成功","huaxinSmsMessageHandler");


    SmsType(String typeName, String messageHandler)
    {
        this.typeName = typeName;
        this.messageHandler = messageHandler;
    }

    private String typeName;
    private String messageHandler;



    public String getName()
    {
        return this.typeName;
    }

    public String getMessageHandler()
    {
        return this.messageHandler;
    }



    public static SmsType getInstance(String smsTypeStr) throws Exception {
        SmsType smsType = null;
        for(SmsType type:SmsType.values())
            if(type.name().equals(smsTypeStr))
                smsType = type;

        if(ValidationUtil.isEmpty(smsType))
            throw new BadRequest("短信类型有误.");
        return smsType;
    }
}
