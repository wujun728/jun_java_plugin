package service;

import java.util.List;

import po.Actor;
import po.File;

public interface ActorService {
		List<Actor> getActors();
		Actor getActorByid(int id);
		Actor UpdateActor(Actor a);
		Actor SaveActor(Actor a);
		void Delete(int id);
		public File insertfile(File f);
}
