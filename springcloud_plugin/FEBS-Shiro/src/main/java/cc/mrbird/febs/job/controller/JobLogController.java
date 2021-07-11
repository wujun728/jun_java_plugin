package cc.mrbird.febs.job.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.job.entity.JobLog;
import cc.mrbird.febs.job.service.IJobLogService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * @author MrBird
 */
@Slf4j
@Validated
@RestController
@RequestMapping("jobLog")
@RequiredArgsConstructor
public class JobLogController extends BaseController {

    private final IJobLogService jobLogService;

    @GetMapping
    @RequiresPermissions("job:log:view")
    public FebsResponse jobLogList(QueryRequest request, JobLog log) {
        return new FebsResponse().success()
                .data(getDataTable(jobLogService.findJobLogs(request, log)));
    }

    @GetMapping("delete/{jobIds}")
    @RequiresPermissions("job:log:delete")
    @ControllerEndpoint(exceptionMessage = "删除调度日志失败")
    public FebsResponse deleteJobLog(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        jobLogService.deleteJobLogs(StringUtils.split(jobIds, Strings.COMMA));
        return new FebsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("job:log:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) {
        ExcelKit.$Export(JobLog.class, response)
                .downXlsx(jobLogService.findJobLogs(request, jobLog).getRecords(), false);
    }
}
