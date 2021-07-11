package cc.mrbird.febs.job.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.job.entity.Job;
import cc.mrbird.febs.job.mapper.JobMapper;
import cc.mrbird.febs.job.service.IJobService;
import cc.mrbird.febs.job.utils.ScheduleUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author MrBird
 */
@Slf4j
@Service("JobService")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

    private final Scheduler scheduler;


    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<Job> scheduleJobList = baseMapper.queryList();
        // 如果不存在，则创建
        scheduleJobList.forEach(scheduleJob -> {
            CronTrigger cronTrigger = ScheduleUtil.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
            }
        });
    }

    @Override
    public Job findJob(Long jobId) {
        return getById(jobId);
    }

    @Override
    public IPage<Job> findJobs(QueryRequest request, Job job) {
        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(job.getBeanName())) {
            queryWrapper.eq(Job::getBeanName, job.getBeanName());
        }
        if (StringUtils.isNotBlank(job.getMethodName())) {
            queryWrapper.eq(Job::getMethodName, job.getMethodName());
        }
        if (StringUtils.isNotBlank(job.getParams())) {
            queryWrapper.like(Job::getParams, job.getParams());
        }
        if (StringUtils.isNotBlank(job.getRemark())) {
            queryWrapper.like(Job::getRemark, job.getRemark());
        }
        if (StringUtils.isNotBlank(job.getStatus())) {
            queryWrapper.eq(Job::getStatus, job.getStatus());
        }

        if (StringUtils.isNotBlank(job.getCreateTimeFrom()) && StringUtils.isNotBlank(job.getCreateTimeTo())) {
            queryWrapper
                    .ge(Job::getCreateTime, job.getCreateTimeFrom())
                    .le(Job::getCreateTime, job.getCreateTimeTo());
        }
        Page<Job> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, true);
        return page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createJob(Job job) {
        job.setCreateTime(new Date());
        job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        save(job);
        ScheduleUtil.createScheduleJob(scheduler, job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJob(Job job) {
        ScheduleUtil.updateScheduleJob(scheduler, job);
        baseMapper.updateById(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJobs(String[] jobIds) {
        List<String> list = Arrays.asList(jobIds);
        list.forEach(jobId -> ScheduleUtil.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(jobIds.split(Strings.COMMA));
        Job job = new Job();
        job.setStatus(status);
        baseMapper.update(job, new LambdaQueryWrapper<Job>().in(Job::getJobId, list));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String jobIds) {
        String[] list = jobIds.split(Strings.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtil.run(scheduler, findJob(Long.valueOf(jobId))));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(String jobIds) {
        String[] list = jobIds.split(Strings.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtil.pauseJob(scheduler, Long.valueOf(jobId)));
        updateBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(String jobIds) {
        String[] list = jobIds.split(Strings.COMMA);
        Arrays.stream(list).forEach(jobId -> ScheduleUtil.resumeJob(scheduler, Long.valueOf(jobId)));
        updateBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
    }
}
