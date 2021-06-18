package schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import po.Actor;
import service.ActorService;

public class AddActor extends QuartzJobBean{
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
				System.out.println("开始自动调度添加演员");
				// 调用业务逻辑方法
				Actor a=new Actor();
				a.setFirst_name("AUTO");
				a.setLast_name("hehe");
				a.setLast_update("2011-1-1");
				actorservice.addactor(a);
		}

}
