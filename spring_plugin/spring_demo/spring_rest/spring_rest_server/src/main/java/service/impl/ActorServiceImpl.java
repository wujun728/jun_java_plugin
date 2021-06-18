package service.impl;

import java.util.List;

import mapper.ActorMapper;
import mapper.FileMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import po.Actor;
import po.File;
import service.ActorService;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,timeout=5)
@Service("actorservice")
public class ActorServiceImpl implements ActorService{
	@Autowired
	public ActorMapper actorMapper;
	
	@Autowired
	public FileMapper filemapper;
	    
	public Actor getActorByid(int id) {
		Actor a=actorMapper.getactorbyid(id);
		return a;
	}

	public List<Actor> getActors() {
		List<Actor> l=actorMapper.getAllactors();
		return l;
	}

	public Actor UpdateActor(Actor a) {
		actorMapper.updateActor(a);
		return a;
	}

	public Actor SaveActor(Actor a) {
		actorMapper.insertActor(a);
		return a;
	}

	public void Delete(int id) {
		actorMapper.delete(id);
	}
	
	public File insertfile(File f){
		filemapper.insert(f);
		return f;
	}

}
