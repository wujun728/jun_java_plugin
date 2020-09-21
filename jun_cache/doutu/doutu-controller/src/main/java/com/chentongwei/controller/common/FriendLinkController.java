package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.service.common.IFriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 友情链接接口
 *
 * @author TongWei.Chen 2017-5-31 20:03:25
 */
@RestController
@RequestMapping("/common/friendLink")
public class FriendLinkController {

    @Autowired
    private IFriendLinkService friendLinkService;

    /**
     * 友链列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {
        return ResultCreator.getSuccess(friendLinkService.list());
    }
}
