package com.company.project.web;

import com.company.project.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** * @author sixiaojie * @date 2020-03-30-20:08 */
@RestController
@RequestMapping("/push")
public class PushController {

    @Autowired
    private PushService pushService;

    @GetMapping("/pushAll")
    public void pushToAll(@RequestParam("msg") String msg){
        pushService.pushMsgToAll(msg);
    }
    @GetMapping("/pushOne")
    public void pushMsgToOne(@RequestParam("--") String userId,@RequestParam("msg") String msg){
        pushService.pushMsgToOne(userId,msg);
    }

}