package boot.spring.mapper;

import java.util.List;

import boot.spring.po.Actor;

//mybatis的实体类和hibernate实体类的不同是mybatis的实体类不需要加载到spring的beanFactory中，
//而是通过操作数据库的mapper来持久化数据,相当于DAO层。
public interface ActorMapper {
	public List<Actor> getAllactors();
	public void updateActorbyid(Actor a);
	public Actor getactorbyid(short id);
	public void insertActor(Actor a);
	public void delete(short id);
	public List<Actor> selectActorByName(Actor a);
}
