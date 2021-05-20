package com.wang.controller;

import com.wang.domain.OauthClientDetailsEntity;
import com.wang.service.IClientDetailsService;
import com.wang.utils.ResultJson;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangxiangyun on 2017/2/14.
 */
@Controller
@RequestMapping("/client")
public class ClientDetailsController {
    @Autowired
    private IClientDetailsService iClientDetailsService;

    @GetMapping("/ui")
    @ApiOperation(value = "添加客户端detailUi", notes = "添加客户端detailUi")
    public String addClientUi() {
        return "clientUi";
    }

    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "添加客户端detail", notes = "添加客户端detail")
    public ResultJson addClient(OauthClientDetailsEntity client) {
        iClientDetailsService.save(client);
        return new ResultJson("添加成功");
    }

}
