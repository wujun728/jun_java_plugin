package com.chentongwei.controller.common;

import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.common.io.FriendLinkIO;
import com.chentongwei.service.common.IFriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 友情链接接口
 *
 * @author TongWei.Chen 2017-05-31 19:15:55
 */
@RestController
@RequestMapping("/common/friendLink")
public class FriendLinkController {

    @Autowired
    private IFriendLinkService friendLinkService;

    /**
     * 查看友链详情
     *
     * @param id：友链id
     * @return
     */
    @RequestMapping(value = "/friendLink/{id}", method = RequestMethod.GET)
    public Result getByID(@PathVariable Integer id) {
        return ResultCreator.getSuccess(friendLinkService.getByID(id));
    }

    /**
     * 友链列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {
        return ResultCreator.getSuccess(friendLinkService.list());
    }

    /**
     * 新增友情链接
     *
     * @param friendLinkIO：友情链接IO
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@Valid FriendLinkIO friendLinkIO) {
        return ResultCreator.getSuccess(friendLinkService.save(friendLinkIO));
    }

    /**
     * 编辑友情链接
     *
     * @param friendLinkIO：友情链接IO
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result edit(@Valid FriendLinkIO friendLinkIO) {
        return ResultCreator.getSuccess(friendLinkService.edit(friendLinkIO));
    }

    /**
     * 删除友情链接
     *
     * @param id：友情链接id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable Integer id) {
        return ResultCreator.getSuccess(friendLinkService.delete(id));
    }
}