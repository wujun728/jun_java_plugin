package com.jun.plugin.task.quartz.mapper;

import org.springframework.stereotype.Component;

import com.jun.plugin.task.quartz.entity.domain.JobAndTrigger;

import java.util.List;

/**
 * <p>
 * Job Mapper
 * </p>
 *
 * @package: com.xkcoding.task.quartz.mapper
 * @description: Job Mapper
 * @author: yangkai.shen
 * @date: Created in 2018-11-26 15:12
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
public interface JobMapper {
    /**
     * 查询定时作业和触发器列表
     *
     * @return 定时作业和触发器列表
     */
    List<JobAndTrigger> list();
}
