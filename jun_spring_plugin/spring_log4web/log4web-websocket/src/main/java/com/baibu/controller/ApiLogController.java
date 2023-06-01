package com.baibu.controller;

import com.baibu.entity.*;
import com.alibaba.fastjson.JSON;
import com.baibu.util.DBUtils;
import com.baibu.util.SaveCurrentIP;
import com.baibu.websocket.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wenbin on 2017/6/27.
 */
@Controller
@CrossOrigin
@RequestMapping(value = "/api")
public class ApiLogController {

    //发送
    @RequestMapping(value = "/log")
    public void sendMsg(Log4web log4web,HttpServletRequest httpRequest){
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        WebSocketApiLog webSocketApiLog = new WebSocketApiLog();

        String msg = time+":"+JSON.toJSONString(log4web);
        String ip = SaveCurrentIP.currentIp(httpRequest);
        DBUtils db = DBUtils.getInstance();
        db.getConnection();
        String sql = "insert into message(message,ip) values('"+msg+"','"+ip+"')";
        try {
            db.executeUpdate(sql,null);
        }catch (Exception e){

        }

        webSocketApiLog.sendMsg(msg);
    }
}
