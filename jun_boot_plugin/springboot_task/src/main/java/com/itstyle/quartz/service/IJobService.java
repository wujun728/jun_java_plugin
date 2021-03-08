package com.itstyle.quartz.service;

import java.util.List;
import com.itstyle.quartz.entity.QuartzEntity;
import com.itstyle.quartz.entity.Result;
import org.quartz.SchedulerException;

public interface IJobService {

    Result listQuartzEntity(QuartzEntity quartz, Integer pageNo, Integer pageSize) throws SchedulerException;
    
    Long listQuartzEntity(QuartzEntity quartz);

    void save(QuartzEntity quartz) throws Exception;
}
