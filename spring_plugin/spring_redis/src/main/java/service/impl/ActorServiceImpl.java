package service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mapper.ActorMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import po.Actor;
import poi.WriteExcel;
import service.ActorService;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("actorservice")
public class ActorServiceImpl implements ActorService{
	@Autowired
	public ActorMapper actorMapper;
	
	@Cacheable(value="getActorByid",key="#id")   //建立缓存  
	public Actor getActorByid(short id) {
		Actor a=actorMapper.getactorbyid(id);
		System.out.println("getActorByid方法执行，不走缓存");
		return a;
	}
	
	@CachePut(value = "getActorByid",key="#a.getId()")  //更新缓存
	public Actor updateactor(Actor a) {
		actorMapper.updateActorbyid(a);
		return a;
	}

	public List<Actor> getpageActors(int pagenum, int pagesize) {
		PageHelper.startPage(pagenum,pagesize);  
		List<Actor> l=actorMapper.getAllactors();
		return l;
	}

	public int getactornum() {
		List<Actor> l=actorMapper.getAllactors();
		return l.size();
	}

	@CachePut(value = "getActorByid", key="#a.id")  //添加缓存
	public Actor addactor(Actor a) {
		actorMapper.insertActor(a);
		return a;
	}

	@CacheEvict(value = {"getActorByid"}, key="#id")  //清理缓存
	public void delete(short id) {
		actorMapper.delete(id);
	}

	public InputStream getInputStream() throws Exception {
		String[] title=new String[]{"id","first_name","last_name","last_update"};
		List<Actor> plist=actorMapper.getAllactors();
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
