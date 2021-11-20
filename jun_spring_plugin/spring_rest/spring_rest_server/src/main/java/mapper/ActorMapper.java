package mapper;

import java.util.List;
import po.Actor;

//mybatis的实体类和hibernate实体类的不同是mybatis的实体类不需要加载到spring的beanFactory中，
//而是通过操作数据库的mapper来持久化数据,相当于DAO层。
public interface ActorMapper {
	public List<Actor> getAllactors();
	public void updateActor(Actor a);
	public Actor getactorbyid(int id);
	public void insertActor(Actor a);
	public void delete(int id);
}
