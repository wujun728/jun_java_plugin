package com.chentongwei.controller.common;

import javax.validation.Valid;

import com.chentongwei.common.annotation.CategoryLog;
import com.chentongwei.common.annotation.DescLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chentongwei.cache.redis.ISetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.common.creator.ResultCreator;
import com.chentongwei.common.entity.Result;
import com.chentongwei.entity.common.io.DownZIPIO;
import com.chentongwei.util.QiniuUtil;

/**
 * 文件管理控制器
 * 比如文件上传、下载zip等
 *
 * @author TongWei.Chen 2017-05-18 12:37:29
 */
@RestController
@RequestMapping("/common/file")
@CategoryLog(menu = "公用部分", subMenu = "文件管理")
public class FileController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private QiniuUtil qiniuUtil;
    @Autowired
    private ISetCache setCache;

    /**
     * 根据资源文件路径下载ZIP包
     *
     * @param downZIPIO：资源文件路径集合
     */
    @RequestMapping(value = "/downZIP", method = RequestMethod.POST)
    @DescLog("下载ZIP表情包")
    public Result downZIP(@Valid @RequestBody DownZIPIO downZIPIO) {
        LOG.info("开始下载ZIP表情包");
        //在七牛上生成zip
        String zipName = qiniuUtil.mkzip(downZIPIO.getUrls());
        //存到redis，ZIP开头都代表是压缩的ZIP表情包
        setCache.cacheSet(RedisEnum.MKZIP.getKey(), zipName);
        LOG.info("下载ZIP表情包成功");
        return ResultCreator.getSuccess(zipName);
    }
}