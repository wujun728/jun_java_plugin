
package org.typroject.tyboot.api.face.systemctl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.api.face.systemctl.model.DictionarieValueModel;
import org.typroject.tyboot.api.face.systemctl.model.SmsRecordModel;
import org.typroject.tyboot.api.face.systemctl.model.SmsTemplate;
import org.typroject.tyboot.api.face.systemctl.orm.dao.SmsRecordMapper;
import org.typroject.tyboot.api.face.systemctl.orm.entity.SmsRecord;
import org.typroject.tyboot.component.activemq.ActiveMqConfig;
import org.typroject.tyboot.component.activemq.JMSSender;
import org.typroject.tyboot.component.activemq.JmsMessage;
import org.typroject.tyboot.core.foundation.constans.CoreConstans;
import org.typroject.tyboot.core.foundation.utils.Bean;
import org.typroject.tyboot.core.foundation.utils.Sequence;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.core.rdbms.service.BaseService;
import org.typroject.tyboot.core.restful.exception.instance.BadRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 验证码发送记录 服务类
 * </p>
 *
 * @author Wujun
 * @since 2017-09-09
 */
@Component
public class SmsRecordService extends BaseService<SmsRecordModel, SmsRecord, SmsRecordMapper>
{
    private final Logger logger = LogManager.getLogger(SmsRecordService.class);

    public static final String DICT_SMS_TEMPLATE            = "SMS_TEMPLATE";//短信模板字典code
    public static final String VERIFICATION_CODE            = "code";//统一的验证码参数名
    public static final long  VERIFICATION_CODE_EXPIRATION  = 10 * 60 * 1000;//验证码过期时间默认10分钟
    public static final String SMS_QUEUE                    = ActiveMqConfig.DEFAULT_QUEUE;//发送短信的默认队列
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JMSSender jmsSender;
    @Autowired
    private DictionarieValueService dictionarieValueService;





    public SmsRecordModel createSms(String smsType,String mobile,String smsParams,String smsCode) throws Exception
    {
        SmsRecordModel model = new SmsRecordModel();
        model.setMobile(mobile);
        model.setSendTime(new Date());
        model.setSmsCode(smsCode);
        model.setSmsType(smsType);
        model.setSmsParams(smsParams);
       return  this.createWithModel(model);
    }





    public SmsRecordModel sendSMS(SmsTemplate smsTemplate)throws Exception
    {
        SmsRecordModel returnModel = null;

        //配置短信参数
        if(!ValidationUtil.isEmpty(smsTemplate))
        {
            //保存短信記錄
            String paramsJson   = objectMapper.writeValueAsString(smsTemplate.getParams());
            returnModel         = this.createSms(smsTemplate.getSmsType(),smsTemplate.getMobile(),paramsJson,smsTemplate.getTemplateId());

            //組裝发送amp消息内容
            sendToQueue(smsTemplate);
        }else{
            throw new Exception("短信发送失败请稍后重试.");
        }
        return returnModel;
    }


    public SmsRecordModel sendVerificationCodeSms(String mobile,String smsType,String messageHandler) throws Exception
    {
        DictionarieValueModel valueModel = dictionarieValueService.queryValueByCodeAndKey(CoreConstans.CODE_SUPER_ADMIN, DICT_SMS_TEMPLATE, smsType);

        SmsRecordModel smsRecordModel   = null;
        SmsTemplate smsTemplate         = null;
        if (!ValidationUtil.isEmpty(valueModel)) {
            HashMap<String, String> smsParmas   = genVerifyCode();
            smsTemplate                         = new SmsTemplate(smsParmas, valueModel.getDictDataValue(),mobile,smsType ,  messageHandler);
            smsRecordModel                      = this.sendSMS(smsTemplate);

        }else{
            throw new Exception("短信类型配置有误.");
        }
        return  smsRecordModel;
    }



    public boolean isVerifyCodeEnable(String mobile, String verificationCode, String smsType) throws Exception {
        boolean flag = false;
        //短信有效时间写死5分钟
        SmsRecordModel smsRecordModel = this.queryLastVerificationCode(mobile, smsType, verificationCode);
        if (!ValidationUtil.isEmpty(smsRecordModel)) {

            Long now = System.currentTimeMillis();

            //校验过期时间
            flag = (now - smsRecordModel.getSendTime().getTime()) < VERIFICATION_CODE_EXPIRATION;
        }
        return flag;
    }



    /**
     * 查询验证码短信记录，同样的手机号和短信类型，验证码可能会有重复，只取最后一条用来验证
     * @param mobile
     * @param smsType
     * @param verificationCode
     * @return
     * @throws Exception
     */
    public SmsRecordModel queryLastVerificationCode(String mobile,String smsType,String verificationCode)throws Exception
    {
        SmsRecordModel returnModel = null;
        List<SmsRecordModel> list = this.queryForList("rec_date",false,mobile,smsType,verificationCode);
        if(!ValidationUtil.isEmpty(list))
            returnModel = list.get(0);
        return returnModel;
    }






    private HashMap<String,String> genVerifyCode() throws Exception
    {
        HashMap<String,String> smsParams    = new HashMap<>();
        String verifyCode                   =  Sequence.generatorSmsVerifyCode4();
        smsParams.put(VERIFICATION_CODE,verifyCode);
        return smsParams;
    }


    private void sendToQueue(SmsTemplate smsTemplate) throws Exception
    {
        HashMap<String,Object> body = new HashMap<>(Bean.BeantoMap(smsTemplate));
        JmsMessage message = ActiveMqConfig.buildMessage("",smsTemplate.getMessageHandler(),body);
        jmsSender.sendQueueMessage(message);
    }

}
