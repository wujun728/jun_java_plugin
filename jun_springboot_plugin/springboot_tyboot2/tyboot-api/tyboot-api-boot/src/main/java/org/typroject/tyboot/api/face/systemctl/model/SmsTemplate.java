package org.typroject.tyboot.api.face.systemctl.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yaohelang on 2017/9/18.
 */
@Data
public class SmsTemplate implements Serializable{

       private HashMap<String,String> params;
       private String templateId;
       private String mobile;
       private String templateContent;
       private boolean multiMobile = false;
       private String  smsType;
       private String messageHandler;


    public SmsTemplate(HashMap<String, String> params, String templateId, String mobile, String smsType, String messageHandler) {
        this.params = params;
        this.templateId = templateId;
        this.mobile = mobile;
        this.smsType = smsType;
        this.messageHandler = messageHandler;
    }





    public static  String replaceContentPrams(String templateContent, Map<String,String> smsParams)
    {

        for(String key:smsParams.keySet())
        {
            if(templateContent.contains(key))
            {
                templateContent = templateContent.replaceAll("\\$\\{"+key+"\\}",smsParams.get(key));
            }
        }

        return templateContent;
    }




}