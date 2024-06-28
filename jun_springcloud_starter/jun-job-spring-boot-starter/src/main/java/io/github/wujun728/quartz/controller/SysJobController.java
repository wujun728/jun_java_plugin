package io.github.wujun728.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.wujun728.common.Result;
import io.github.wujun728.common.exception.code.BaseResponseCode;
import io.github.wujun728.quartz.utils.ScheduleJob;
import io.github.wujun728.quartz.entity.SysJobEntity;
import io.github.wujun728.quartz.service.SysJobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 定时任务
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Api(tags = "定时任务")
@RestController
@RequestMapping("/sysJob")
public class SysJobController {
    @Resource
    private SysJobService sysJobService;

    @ApiOperation(value = "新增")
    @PostMapping("/add")
    //@RequiresPermissions("sysJob:add")
    public Result add(@RequestBody SysJobEntity sysJob) {
        if (isValidExpression(sysJob.getCronExpression())) {
            return Result.fail("cron表达式有误");
        }
        Result result = ScheduleJob.judgeBean(sysJob.getBeanName());
        sysJobService.saveJob(sysJob);
        return Result.success(result);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    //@RequiresPermissions("sysJob:delete")
    public Result delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysJobService.delete(ids);
        return Result.success();
    }

    @ApiOperation(value = "更新")
    @PutMapping("/update")
    //@RequiresPermissions("sysJob:update")
    public Result update(@RequestBody SysJobEntity sysJob) {
        if (isValidExpression(sysJob.getCronExpression())) {
            return Result.fail("cron表达式有误");
        }
        Result result = ScheduleJob.judgeBean(sysJob.getBeanName());
        sysJobService.updateJobById(sysJob);
        return Result.success(result);
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    //@RequiresPermissions("sysJob:list")
    public Result findListByPage(@RequestBody SysJobEntity sysJob) {
        Page page = new Page(sysJob.getPage(), sysJob.getLimit());
        LambdaQueryWrapper<SysJobEntity> queryWrapper = Wrappers.lambdaQuery();
        //查询条件示例
        if (!StringUtils.isEmpty(sysJob.getBeanName())) {
            queryWrapper.like(SysJobEntity::getBeanName, sysJob.getBeanName());
        }
        IPage<SysJobEntity> iPage = sysJobService.page(page, queryWrapper);
        return Result.success(iPage);
    }


    /**
     * 立即执行任务
     */
    @ApiOperation(value = "立即执行任务")
    @PostMapping("/run")
    //@RequiresPermissions("sysJob:run")
    public Result run(@RequestBody List<String> ids) {
        sysJobService.run(ids);

        return Result.success();
    }

    /**
     * 暂停定时任务
     */
    @ApiOperation(value = "暂停定时任务")
    @PostMapping("/pause")
    //@RequiresPermissions("sysJob:pause")
    public Result pause(@RequestBody List<String> ids) {
        sysJobService.pause(ids);

        return Result.success();
    }

    /**
     * 恢复定时任务
     */
    @ApiOperation(value = "恢复定时任务")
    @PostMapping("/resume")
    //@RequiresPermissions("sysJob:resume")
    public Result resume(@RequestBody List<String> ids) {
        sysJobService.resume(ids);
        return Result.success();
    }

    /**
     * 判断cron表达式
     *
     * @param cronExpression cron表达式
     * @return 是否有误
     */
    public static boolean isValidExpression(String cronExpression) {
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setCronExpression(cronExpression);
            Date date = trigger.computeFirstFireTime(null);
            return date == null || !date.after(new Date());
        } catch (Exception e) {
            return true;
        }
    }


    @ApiOperation(value = "获取运行时间")
    @PostMapping("/getRecentTriggerTime")
    //@RequiresPermissions("sysJob:add")
    public Result getRecentTriggerTime(String cron) {
        List<String> list = new ArrayList<>();
        try {
            CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
            cronTriggerImpl.setCronExpression(cron);
            // 这个是重点，一行代码搞定
            List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, 5);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Date date : dates) {
                list.add(dateFormat.format(date));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Result.success(list);
    }


}
