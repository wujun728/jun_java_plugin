package com.buxiaoxia.client;

import com.buxiaoxia.entity.Staff;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xw on 2017/9/15.
 * 2017-09-15 10:43
 */
@Component
@FeignClient(value = "avatar", url = "127.0.0.2:8081")
public interface JobClient {

    @RequestMapping(value = "/api/v1.0/job/{staffId}", method = RequestMethod.GET)
    Staff.Job getJobInfoByStaffId(@PathVariable("staffId") Long staffId);

}


@Component
class JobClientFallback implements JobClient {

    @Override
    public Staff.Job getJobInfoByStaffId(Long staffId) {
        return null;
    }
}