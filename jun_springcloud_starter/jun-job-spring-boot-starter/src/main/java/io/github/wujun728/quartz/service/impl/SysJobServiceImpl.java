package io.github.wujun728.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wujun728.common.constant.Constant;
import io.github.wujun728.common.exception.BusinessException;
import io.github.wujun728.quartz.utils.ScheduleUtils;
import io.github.wujun728.quartz.entity.SysJobEntity;
import io.github.wujun728.quartz.mapper.SysJobMapper;
import io.github.wujun728.quartz.service.SysJobService;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务 服务类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
@Service("sysJobService")
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJobEntity> implements SysJobService {
    @Resource
    private Scheduler scheduler;
    @Resource
    private SysJobMapper sysJobMapper;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<SysJobEntity> scheduleJobList = this.list();
        for (SysJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public void saveJob(SysJobEntity sysJob) {
        sysJob.setStatus(Constant.SCHEDULER_STATUS_NORMAL);
        this.save(sysJob);

        ScheduleUtils.createScheduleJob(scheduler, sysJob);
    }

    @Override
    public void updateJobById(SysJobEntity sysJob) {
        SysJobEntity sysJobEntity = this.getById(sysJob.getId());
        if (sysJobEntity == null) {
            throw new BusinessException("获取定时任务异常");
        }
        sysJob.setStatus(sysJobEntity.getStatus());
        ScheduleUtils.updateScheduleJob(scheduler, sysJob);

        this.updateById(sysJob);
    }

    @Override
    public void delete(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }
        sysJobMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.run(scheduler, this.getById(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(ids, Constant.SCHEDULER_STATUS_PAUSE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(List<String> ids) {
        for (String jobId : ids) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(ids, Constant.SCHEDULER_STATUS_NORMAL);
    }

    @Override
    public void updateBatch(List<String> ids, int status) {
        ids.parallelStream().forEach(id -> {
            SysJobEntity sysJobEntity = new SysJobEntity();
            sysJobEntity.setId(id);
            sysJobEntity.setStatus(status);
            baseMapper.updateById(sysJobEntity);
        });
    }
}