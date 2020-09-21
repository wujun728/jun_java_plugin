/*   
 * Project: OSMP
 * FileName: MessageManager.java
 * version: V1.0
 */
package com.osmp.http.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osmp.intf.define.service.MessageService;
/**
 * 消息服务管理
 * @author heyu
 *
 */
public class MessageManager {
    private Logger logger = LoggerFactory.getLogger(ServiceFactoryManager.class);
    //存储所有dataService服务
    private MessageService messageService;
    
    //监听服务
    public void bind(MessageService messageService,Map<Object,Object> props){
        if(messageService == null) return;
        this.messageService = messageService;
        logger.info("绑定消息服务");
    }
    
    //移除服务
    public void unbind(MessageService messageService,Map<Object,Object> props){
        if(messageService == null) return;
        this.messageService = null;
        logger.info("解绑消息服务");
    }
    
    public void warnSMS(String content) {
        if(content == null || "".equals(content.trim())) return;
        if(messageService != null){
            messageService.mmsWarn(content);
        }
    }
    
    public void warnMail(String title,String content) {
        if(content == null || title == null || "".equals(title.trim()) || "".equals(content.trim())) return;
        if(messageService != null){
            messageService.emailWarn(title, content);
        }
    }
}
