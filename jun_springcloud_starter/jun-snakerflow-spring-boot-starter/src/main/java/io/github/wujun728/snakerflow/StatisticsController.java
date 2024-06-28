package io.github.wujun728.snakerflow;

import java.util.List;

import io.github.wujun728.snakerflow.module.Response;
import io.github.wujun728.snakerflow.process.SnakerEngineFacets;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.laker.admin.module.ext.mapper.ExtLogMapper;
//import io.github.wujun728.system.service.HttpSessionService;

//import cn.dev33.satoken.stp.StpUtil;
//import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Dict;

@RestController
@RequestMapping("/sys/statistics")
public class StatisticsController {
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;
//    @Resource
//    HttpSessionService sessionService;
//    @Autowired
//    ExtLogMapper extLogMapper;


    @GetMapping("/console")
    public Response get() {
    	//获取当前登陆人
        String userId = "admin";//sessionService.getCurrentUsername();
        List<Task> activeTasks = snakerEngineFacets.getEngine().query()
                .getActiveTasks(new QueryFilter().setOperator(userId));
        List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query()
                .getHistoryTasks(new QueryFilter().setOperator(userId));
        Dict res = Dict.create().set("todo", activeTasks.size())
                .set("done", historyTasks.size())
                .set("ip", "IP地址")
                .set("online", 0);
        return Response.ok(res);
    }
}
