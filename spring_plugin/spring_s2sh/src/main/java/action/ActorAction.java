package action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import po.Actor;
import poi.WriteExcel;
import service.ActorService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ActorAction extends ActionSupport{
	private ActorService actorservice;
	private int id;
	private Actor actor;
	@JSON(serialize=false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}
	@JSON(serialize=false)
	public ActorService getActorservice() {
		return actorservice;
	}

	public void setActorservice(ActorService actorservice) {
		this.actorservice = actorservice;
	}
	
	public String execute(){
		actorservice.updateactor(actor);
		return Action.SUCCESS;
	}
	
	public String getActorInfo(){
		actor=actorservice.getActorByid(id);
		return "ok";
	}

	public InputStream getDownLoadFile() throws Exception{
	       InputStream is;
			is = actorservice.getInputStream();
			return is;
	}
	
	public String export(){
		return "download";
	}
}
