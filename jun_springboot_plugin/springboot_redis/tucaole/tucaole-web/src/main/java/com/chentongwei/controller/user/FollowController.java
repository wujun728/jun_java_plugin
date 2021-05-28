package com.chentongwei.controller.user;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import com.chentongwei.common.entity.Result;
import com.chentongwei.core.user.biz.follow.FollowBiz;
import com.chentongwei.core.user.entity.io.follow.FollowListIO;
import com.chentongwei.core.user.entity.io.follow.FollowSaveIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 关注接口
 */
@RestController
@RequestMapping("/user/follow")
@CategoryLog(menu = "用户模块")
public class FollowController {

    @Autowired
    private FollowBiz followBiz;

    /**
     * 我关注的人列表
     *
     * @param followListIO：参数
     * @return
     */
    @GetMapping("/listMyFollowers")
    public Result listMyFollowers(@Valid FollowListIO followListIO) {
        return followBiz.listMyFollowers(followListIO);
    }

    /**
     * 关注我的人列表
     *
     * @param followListIO：参数
     * @return
     */
    @GetMapping("/listMyFans")
    public Result listMyFans(@Valid FollowListIO followListIO) {
        return followBiz.listMyFans(followListIO);
    }

    /**
     * 保存用户关注记录
     *
     * @param followSaveIO：参数
     * @return
     */
    @PostMapping("/save")
    @DescLog("用户关注/取消关注")
    public Result save(@Valid FollowSaveIO followSaveIO) {
        return followBiz.save(followSaveIO);
    }

}
