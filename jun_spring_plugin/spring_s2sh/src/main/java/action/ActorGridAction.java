package action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import po.Actor;
import service.ActorService;

import com.opensymphony.xwork2.ActionSupport;

public class ActorGridAction extends ActionSupport {
	private int current;//当前页面号
	private int rowCount;//每页行数
	private int total;//总行数
	private String searchPhrase;
	private List<Actor> rows;
	private ActorService actorservice;
	
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getSearchPhrase() {
		return searchPhrase;
	}

	public void setSearchPhrase(String searchPhrase) {
		this.searchPhrase = searchPhrase;
	}

	public List<Actor> getRows() {
		return rows;
	}

	public void setRows(List<Actor> rows) {
		this.rows = rows;
	}
	@JSON(serialize=false)
	public ActorService getActorservice() {
		return actorservice;
	}

	public void setActorservice(ActorService actorservice) {
		this.actorservice = actorservice;
	}

	
	public String execute(){
		rows=actorservice.getpageActors(current, rowCount);
		total=actorservice.getAllActors().size();
		return "success";
	}
}
