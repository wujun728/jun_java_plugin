
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.company.project.model.UserInfo;
/**
 * @author badqiu
 */
public class Ibatis3DemoMain {
	
	public static void main(String[] args) throws Exception {
		
		Reader reader = Resources.getResourceAsReader("Configuration.xml");
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		SqlSession session = sessionFactory.openSession();
		
		testCrud(session);
	}

	private static void testCrud(SqlSession session) {
		
		UserInfo user = new UserInfo();
		user.setPassword("pwd_1!aSw29");
		user.setAge(100);
		
		//test insert
		session.insert("UserInfo.insert",user);
		UserInfo fromDb = (UserInfo) session.selectOne("UserInfo.getById", user.getUserId());
		session.commit();
		System.out.println("user:"+user);
		System.out.println("fromDb:"+fromDb);
		assertTrue(user.equals(fromDb));
		
		//test update
		user.setUsername("badqiu");
		session.update("UserInfo.update",user);
		fromDb = (UserInfo) session.selectOne("UserInfo.getById", user.getUserId());
		assertTrue(user.equals(fromDb));
		
		// test select
		Long count = (Long)session.selectOne("UserInfo.count",user);
		assertTrue(1 == count);
		List list = session.selectList("UserInfo.pageSelect",user,0,100);
		fromDb = (UserInfo)list.get(0);
		assertTrue(fromDb.getUsername().equals(user.getUsername()));
		assertTrue(fromDb.equals(user));
		 
		//test delete
		session.delete("UserInfo.delete",user.getUserId());
		fromDb = (UserInfo) session.selectOne("UserInfo.getById", user.getUserId());
		assertTrue(fromDb == null);
	}
	
	public static void assertTrue(boolean v) {
		if(! v) {
			throw new IllegalStateException("test expression must be true");
		}
	}
}
