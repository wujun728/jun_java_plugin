package io.github.wujun728.snakerflow.ext.controller;

import java.util.List;

import io.github.wujun728.snakerflow.ext.entity.ExtLog;
import io.github.wujun728.snakerflow.ext.service.IExtLogService;
import io.github.wujun728.snakerflow.module.PageResponse;
import io.github.wujun728.snakerflow.module.Response;
import io.github.wujun728.snakerflow.ext.mapper.ExtLogMapper;
import io.github.wujun728.snakerflow.ext.vo.LogStatisticsTop10Vo;
import io.github.wujun728.snakerflow.ext.vo.LogStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * 
 * @since 2021-08-16
 */
@RestController
@RequestMapping("/ext/log")
//@Metrics
public class ExtLogController {
    @Autowired
    IExtLogService extLogService;

    @Autowired
    ExtLogMapper extLogMapper;

//    @Autowired
//    ISysUserService sysUserService;

    @GetMapping
    @ApiOperation(value = "日志分页查询")
    //@SaCheckPermission("log.list")
    public PageResponse pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                                @RequestParam(required = false, defaultValue = "10") long limit,
                                String keyWord) {
        Page roadPage = new Page<>(page, limit);
        LambdaQueryWrapper<ExtLog> queryWrapper = new QueryWrapper().lambda();
        if (StrUtil.isNotBlank(keyWord)) {
            queryWrapper.like(ExtLog::getRequest, keyWord);
        }
        queryWrapper.orderByDesc(ExtLog::getCreateTime);
        Page pageList = extLogService.page(roadPage, queryWrapper);
        List<ExtLog> records = pageList.getRecords();
        records.forEach(extLog -> {
            if (extLog.getUserId() != null) {
//            	设置日志用户名称
//                SysUser sysUser = sysUserService.getById(extLog.getUserId());
//                extLog.setUser(sysUser);
            }

        });
        return PageResponse.ok(records, pageList.getTotal());
    }


    @GetMapping("/visits7day")
    @ApiOperation(value = "7天访问量")
    public Response visits7day() {
        List<LogStatisticsVo> logStatisticsVo = extLogMapper.selectStatistics7Day();
        return Response.ok(logStatisticsVo);
    }

    @GetMapping("/visitsTop10IP")
    @ApiOperation(value = "visitsTop10IP")
    public PageResponse visitsTop10IP() {
        List<LogStatisticsTop10Vo> logStatisticsVo = extLogMapper.selectStatisticsVisitsTop10IP();
        return PageResponse.ok(logStatisticsVo, 10L);
    }
}