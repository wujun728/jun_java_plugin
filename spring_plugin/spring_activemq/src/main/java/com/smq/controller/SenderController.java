package com.smq.controller;

import com.smq.entity.Email;
import com.smq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jms.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by madong on 16/10/18.
 */
@Controller
public class SenderController {

    private static final int SEND_NUMBER = 5;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    ProducerService producerservice;

    @Autowired
    @Qualifier("queueDestination")
    private Destination destination;

    @Autowired
    @Qualifier("sessionAwareQueue")
    private Destination destinationsessionAwareQueue;
    @Autowired
    @Qualifier("adapterDestination")
    private Destination adapterDestination;

//    @Autowired
//    @Qualifier("topicDestination")
//    private Destination topicDestination ;

    @RequestMapping("/sendMessage")
    public String sendMessage(){
        Email email = new Email("zhangsan@xxx.com", "主题", "内容");
        HashMap map = new HashMap();
        map.put("qq","qq");
        producerservice.sendMessage(adapterDestination, map);
        return "33333";
    }

    @RequestMapping("/sendTopicMessage")
    public String sendTopicMessage(){
//            producerservice.sendMessage(topicDestination, "wolailetopic2");

        return "4444";
    }

}
