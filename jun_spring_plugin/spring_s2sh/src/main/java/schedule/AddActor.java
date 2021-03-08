package schedule;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import po.Actor;

import service.ActorService;

public class AddActor extends QuartzJobBean{
	// 判断作业是否执行的旗标
	private boolean isRunning = false;
	private ActorService actorservice;
	
	public ActorService getActorservice() {
		return actorservice;
	}

	public void setActorservice(ActorService actorservice) {
		this.actorservice = actorservice;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		if (!isRunning)
		{
			System.out.println("开始自动调度添加演员");
			isRunning = true;
			// 调用业务逻辑方法
			List<Actor> l=actorservice.getAllActors();
			int actornum=l.size();
			System.out.println(actorservice.hashCode());
			actorservice.addactor((short)actornum);
			isRunning = false;
		}
		
	}

}
