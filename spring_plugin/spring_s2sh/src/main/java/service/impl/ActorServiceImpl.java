package service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.Actor;
import poi.WriteExcel;
import service.ActorService;
import dao.ActorDao;
@Service("actorservice")
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
public class ActorServiceImpl implements ActorService{
	@Autowired
	private ActorDao actorDao;
	public ActorDao getActorDao() {
		return actorDao;
	}

	public void setActorDao(ActorDao actorDao) {
		this.actorDao = actorDao;
	}

	public List<Actor> getAllActors() {
		List<Actor> list=actorDao.find("from Actor");
		return list;
	}

	public List<Actor> getpageActors(int page, int pagesize) {
		List<Actor> list=actorDao.findByPage("from Actor", page, pagesize);
		return list;
	}

	public Actor getActorByid(int id) {
		List<Actor> list=actorDao.find("from Actor where id="+id);
		if(list.size()==1)
		return list.get(0);
		else
		return null;
	}

	public void updateactor(Actor a) {
		actorDao.update(a);
	}

	public void addactor(short id) {
		Actor a=new Actor();
		a.setFirst_name("wsz");
		a.setLast_name("pythonwang");
		a.setLast_update(new Date());
		id++;
		a.setId(id);
		actorDao.save(a);
	}

	public InputStream getInputStream() throws Exception {
		String[] title=new String[]{"id","first_name","last_name","last_update"};
		List<Actor> plist=actorDao.find("from Actor");
		List<Object[]>  dataList = new ArrayList<Object[]>();  
		for(int i=0;i<plist.size();i++){
			Object[] obj=new Object[4];
			obj[0]=plist.get(i).getId();
			obj[1]=plist.get(i).getFirst_name();
			obj[2]=plist.get(i).getLast_name();
			obj[3]=plist.get(i).getLast_update();
			dataList.add(obj);
		}
		WriteExcel ex = new WriteExcel(title, dataList);  
		InputStream in;
		in = ex.export();
		return in;
		
	}

}
