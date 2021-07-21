package cn.springmvc.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.model.Archivemessages;
import cn.springmvc.service.IMService;

@Controller("im")
public class IMController {

    private static final Logger log = LoggerFactory.getLogger(IMController.class);

    @Autowired
    private IMService iMService;

    @RequestMapping("/message")
    public String message(Model model) {
        log.info("# 查看聊天记录");

        List<Archivemessages> msgs = iMService.findArchivemessagesAll();
        model.addAttribute("msgs", msgs);
        return "message";
    }

}
