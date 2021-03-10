package com.kind.mq.controller;

import com.google.gson.GsonBuilder;
import com.kind.mq.domain.Mail;
import com.kind.mq.domain.Message;
import com.kind.mq.service.impl.ProducerImpl;
import com.kind.mq.service.impl.TopicImpl;
import com.kind.mq.websocket.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MessageController {

    MyWebSocketHandler handler = new MyWebSocketHandler();

    @Autowired
    ProducerImpl producer;
    @Autowired
    TopicImpl topic;

    @RequestMapping(value = "/produce", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void produce(@ModelAttribute("mail") Mail mail) throws Exception {
        Message msg = new Message();
        msg.setText("向队列myquene添加一条消息:" + mail.toString());
        handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
        producer.sendMail(mail);
    }

    @RequestMapping(value = "/topic", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void topic(@ModelAttribute("mail") Mail mail) throws Exception {
        Message msg = new Message();
        msg.setText("向话题mytopic发布一条消息:" + mail.toString());
        handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
        topic.sendMail(mail);
    }

    @RequestMapping("message")
    public String message(HttpServletRequest request) {
        request.getSession().setAttribute("uid", (long) 11);
        return "message";
    }

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        return message(request);
    }


}
