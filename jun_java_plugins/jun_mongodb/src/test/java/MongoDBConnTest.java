
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * @author Wujun
 * 创建时间 ： 2014年11月4日 下午3:01:27
 * 
 * MongoDB的简单CRUD操作
 * MongoDB环境的安装：http://www.cnblogs.com/mecity/archive/2011/06/11/2078527.html
 */
public class MongoDBConnTest {
	public static void main(String[] args) throws UnknownHostException {
		//创建了一个MongoDB的数据库连接对象，它默认连接到当前机器的localhost地址，端口是27017
		Mongo mongo = new Mongo(); 
		
        //查询所有的Database
        for (String name : mongo.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }
        
		//获得了一个test的数据库，如果mongoDB中没有创建这个数据库也是可以正常运行的。
		//mongoDB可以在没有创建这个数据库的情况下，完成数据的添加操作。当添加的时候，没有这个库，mongoDB会自动创建当前数据库。
		DB db = mongo.getDB("test");
        //查询所有的聚集集合,也就是test数据库下面的表
        for (String name : db.getCollectionNames()) {
            System.out.println("collectionName: " + name);
        }
		
		//获取一个“聚集集合DBCollection”,获取foo表
		DBCollection users = db.getCollection("foo"); 
		
		DBCursor cur = users.find();
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
        System.out.println(cur.count());
        System.out.println(cur.getCursorId());
        System.out.println(JSON.serialize(cur));
	}
}
