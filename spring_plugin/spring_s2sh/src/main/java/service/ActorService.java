package service;

import java.io.InputStream;
import java.util.List;

import po.Actor;


public interface ActorService {
	List<Actor> getAllActors();
	List<Actor> getpageActors(int page,int pagesize);
	Actor getActorByid(int id);
	void updateactor(Actor a);
	void addactor(short id);
	InputStream getInputStream() throws Exception;
}
