package io.github.wujun728.snakerflow.process;

import java.util.Arrays;
import java.util.List;

import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.WorkItem;
import org.springframework.beans.factory.annotation.Autowired;

//import io.github.wujun728.module.ext.mapper.BizCommonMapper;
import io.github.wujun728.common.base.BaseFlowEntity;
//import io.github.wujun728.system.mapper.SysUserMapper;
//import io.github.wujun728.system.service.HttpSessionService;

//import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

public class BaseFlowController {
//    @Resource
//    HttpSessionService sessionService;
    @Autowired
    private SnakerEngineFacets snakerEngineFacets;
//    @Autowired
//    private UserService sysUserService;
//    @Autowired
//	private SysUserMapper sysuer;

//    @Autowired
//    private BizCommonMapper bizCommonMapper;

    protected void setFlowStatusInfo(BaseFlowStatus baseFlowStatus) {
        String orderId = baseFlowStatus.getOrderId();
        // 流程实例状态
        HistoryOrder histOrder = snakerEngineFacets.getEngine().query().getHistOrder(orderId);
        if(histOrder!=null) {
        	baseFlowStatus.setOrderState(histOrder.getOrderState());
        }
        List<WorkItem> workItems = snakerEngineFacets.getEngine().query().getWorkItems(null, new QueryFilter().setOrderId(orderId));
        if (CollUtil.isNotEmpty(workItems)) {
            WorkItem workItem = workItems.get(0);
            // 当前流程实例任务节点名称
            baseFlowStatus.setTaskName(workItem.getTaskName());
//            List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query().getHistoryTasks(new QueryFilter()
//                    .setOrderId(orderId)
//                    .setOperator(sessionService.getCurrentUsername())
//                    .orderBy("create_time")
//                    .order(QueryFilter.DESC));
//            if (CollUtil.isNotEmpty(historyTasks)) {
//                baseFlowStatus.setCreateTaskId(historyTasks.get(0).getId());
//            }
            // 当前流程实例任务操作人
            String[] actors = snakerEngineFacets.getEngine().query().getTaskActorsByTaskId(workItem.getTaskId());
            if (ArrayUtil.isNotEmpty(actors)) {
//                List<String> res = new ArrayList<>();
//                for (String actor : actors) {
//                    //SysUser user = sysUserService.getById(Long.valueOf(actor));
//                    SysUser user = sysuer.getUserByName(actor);
//                    if (user != null) {
//                        res.add(user.getRealName());
//                    }
//                }
                String taskOperatorName = StrUtil.join(",", Arrays.asList(actors));
                baseFlowStatus.setTaskOperatorName(taskOperatorName);
                if(taskOperatorName.contains("sessionService.getCurrentUserRealname())")) {
                	baseFlowStatus.setIsOwner(1);
                }
            }
        }
    }
    
    protected void setFlowStatusInfo(BaseFlowEntity baseFlowEntity) {
    	String orderId = baseFlowEntity.getOrderId();
    	// 流程实例状态
    	HistoryOrder histOrder = snakerEngineFacets.getEngine().query().getHistOrder(orderId);
    	if(histOrder!=null) {
    		baseFlowEntity.setOrderState(histOrder.getOrderState());
    	}
    	List<WorkItem> workItems = snakerEngineFacets.getEngine().query().getWorkItems(null, new QueryFilter().setOrderId(orderId));
    	if (CollUtil.isNotEmpty(workItems)) {
    		WorkItem workItem = workItems.get(0);
    		// 当前流程实例任务节点名称
    		baseFlowEntity.setTaskName(workItem.getTaskName());
//    		List<HistoryTask> historyTasks = snakerEngineFacets.getEngine().query().getHistoryTasks(new QueryFilter()
//    				.setOrderId(orderId)
//    				.setOperator(sessionService.getCurrentUsername())
//    				.orderBy("create_time")
//    				.order(QueryFilter.DESC));
//    		if (CollUtil.isNotEmpty(historyTasks)) {
//    			baseFlowEntity.setCreateTaskId(historyTasks.get(0).getId());
//    		}
    		// 当前流程实例任务操作人
    		String[] actors = snakerEngineFacets.getEngine().query().getTaskActorsByTaskId(workItem.getTaskId());
    		if (ArrayUtil.isNotEmpty(actors)) {
//    			List<String> res = new ArrayList<>();
//    			for (String actor : actors) {
//    				//SysUser user = sysUserService.getById(Long.valueOf(actor));
//    				SysUser user = sysuer.getUserByName(actor);
//    				if (user != null) {
//    					res.add(user.getRealName());
//    				}
//    			}
    			String taskOperatorName = StrUtil.join(",", Arrays.asList(actors));
    			baseFlowEntity.setTaskOperatorName(taskOperatorName);
                if(taskOperatorName.contains("sessionService.getCurrentUserRealname()")) {  // 这个可能有问题，必须要是自己创建的数据
//                	baseFlowEntity.setIsOwner(1);
                }
    		}
    	}
    }
}
