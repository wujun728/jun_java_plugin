package CoffeeAndIce.springboot_guice.server.persitence;

import org.springframework.stereotype.Component;

/**
 * 模拟Dao，但是不做真操作
 * @author lg
 *
 */
@Component //componentScan会注册他
public class SimpleDao {
	public void save(String data){
		System.out.println("save:"+data);
	};
	
	public String getPersonData(String name){
		System.out.println("get person data for:"+name);
		return name;
	}
}
