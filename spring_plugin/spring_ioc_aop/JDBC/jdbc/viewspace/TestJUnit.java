package jdbc.viewspace;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJUnit {

	@Test 
	public void testCreat(){
		String file="spring-jdbc.xml"; 
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
	    ViewSpaceDaoImpl dao=(ViewSpaceDaoImpl) context.getBean("viewSpaceDao");
	    dao.createViewSpaceTable();context.close();
	}
	//@Test
	public void testAddViewSpace(){
	    ViewSpace vs=new ViewSpace(12,"point1","this id p1","sd");	
	    String file="spring-jdbc.xml"; 
	    ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);	
	    ViewSpaceDaoImpl dao=(ViewSpaceDaoImpl) context.getBean("viewSpaceDao");
		dao.addViewSpace(vs);context.close();
	}
	//@Test
	public void testSelectOne(){
		int id=12;
		String file="spring-jdbc.xml";
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
		ViewSpaceDao dao=(ViewSpaceDao) context.getBean("viewSpaceDao");
		ViewSpace vs=dao.getViewSpaceOne(id);
		System.out.println(vs.getSpaceName());context.close();
	}
	//@Test
	public void testSelectAll(){
		String file="spring-jdbc.xml";
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
		ViewSpaceDao dao=(ViewSpaceDao) context.getBean("viewSpaceDao");
		List<ViewSpace> list=dao.getViewSpaceList(5,8);
		Iterator<ViewSpace> iter=list.iterator();
		while(iter.hasNext()){
			ViewSpace vs=iter.next();
			System.out.println(vs.getSpaceName());
		}	
		context.close();
	}
	//@Test
	public void testAddList(){
		String file="spring-jdbc.xml";
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
		ViewSpaceDao dao=(ViewSpaceDao) context.getBean("viewSpaceDao");
		ViewSpace vs1=new ViewSpace(22,"point22","thisispoint22","sd");
		ViewSpace vs2=new ViewSpace(23,"point23","thisispoint22","sd");
		ViewSpace vs3=new ViewSpace(24,"point24","thisispoint22","sd");
		List<ViewSpace> list=new LinkedList<ViewSpace>();
		list.add(vs1);list.add(vs2);list.add(vs3);
		dao.addViewSpaceList(list);context.close();
	}
	//@Test
	public void testVS(){
		String file="spring-jdbc.xml";
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
		ViewSpaceDao dao=(ViewSpaceDao) context.getBean("viewSpaceDao");
		List<ViewSpace> list=dao.getViewSpaces(5,8);
		Iterator<ViewSpace> iter=list.iterator();
		while(iter.hasNext()){
			ViewSpace vs=iter.next();
			System.out.println(vs.getSpaceName());
		}	
		context.close();
	}
	//@Test
	public void createP(){
		String file="spring-jdbc.xml";
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
		ViewSpaceDao dao=(ViewSpaceDao) context.getBean("viewSpaceDao");
		dao.procedure();context.close();
	}
//	@Test
	public void testPP(){
		String file="spring-jdbc.xml";
		ConfigurableApplicationContext context=new ClassPathXmlApplicationContext(file);
		ViewSpaceDao dao=(ViewSpaceDao) context.getBean("viewSpaceDao");
		int a=dao.procedureExe(2);System.out.println(a);context.close();
	}
}
