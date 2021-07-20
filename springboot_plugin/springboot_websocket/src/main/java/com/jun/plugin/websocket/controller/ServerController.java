package com.jun.plugin.websocket.controller;

import cn.hutool.core.lang.Dict;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.websocket.model.Server;
import com.jun.plugin.websocket.payload.ServerVO;
import com.jun.plugin.websocket.util.ServerUtil;

/**
 * <p>
 * 服务器监控Controller
 * </p>
 *
 * @package: com.xkcoding.websocket.controller
 * @description: 服务器监控Controller
 * @author: yangkai.shen
 * @date: Created in 2018-12-17 10:22
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @GetMapping
    public Dict serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        return ServerUtil.wrapServerDict(serverVO);
    }

}
