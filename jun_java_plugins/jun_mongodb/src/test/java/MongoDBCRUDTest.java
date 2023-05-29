
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;

/**
 * @author Wujun
 * 创建时间 ： 2014年11月4日 下午3:15:17
 * 
 * MongoDB的简单CRUD操作
 * MongoDB环境的安装：http://www.cnblogs.com/mecity/archive/2011/06/11/2078527.html
 * 
 * mongoDB对Java支持的驱动包下载地址：https://github.com/mongodb/mongo-java-driver/downloads
 * mongoDB对Java的相关支持、技术：http://www.mongodb.org/display/DOCS/Java+Language+Center
 * 在线查看源码：https://github.com/mongodb/mongo-java-driver 
 */
public class MongoDBCRUDTest {
	private Mongo mg = null;
	private DB db;//数据库
	private DBCollection users;//表

	@Before
	public void init() {
		try {
			mg = new Mongo();
			//mg = new Mongo("localhost", 27017);//默认是本地,端口为27017.
		} catch (MongoException e) {
			e.printStackTrace();
		}
		// 获取temp DB；如果没有创建，mongodb会自动创建.temp就是平常所说的Database
		db = mg.getDB("temp");
		// 获取users DBCollection；如果没有创建，mongodb会自动创建,users就是平常所说的table
		users = db.getCollection("users");
	}

	@After
	public void destory() {
		if (mg != null)
			mg.close();
		mg = null;
		db = null;
		users = null;
		System.gc();
	}

	private void print(Object o) {
		System.out.println(o);
	}
	
	/**
	 * 查询所有数据
	 * 
	 * @author Wujun
	 * 创建时间 ： 2014年11月4日 下午3:17:14
	 */
	@Test
	public void queryAll() {
	    print("查询users的所有数据：");
	    //db游标
	    DBCursor cur = users.find();
	    while (cur.hasNext()) {
	        print(cur.next());
	    }
	}
	
	/**
	 * 添加数据
	 * 
	 * @author Wujun
	 * 创建时间 ： 2014年11月4日 下午3:38:44
	 */
	@Test
	public void add() {
	    //先查询所有数据
	    queryAll();
	    DBObject user = new BasicDBObject();
	    user.put("name", "小周");
	    user.put("age", 121);
	    //users.save(user)保存，getN()获取影响行数
	    //print(users.save(user).getN());
	    
	    //扩展字段，随意添加字段，不影响现有数据
	    user.put("sex", "男");

	    //添加多条数据，传递Array对象
	    users.insert(user, new BasicDBObject("name3", "tom3"),new BasicDBObject("name4", "lucy4"));
	    
	    //添加List集合
	    List<DBObject> list = new ArrayList<DBObject>();
	    list.add(user);
	    
	    //查询下数据，看看是否添加成功
	    print("总数: " + users.count());
	    queryAll();
	}
	
	
	/**
	 * 删除数据
	 * 
	 * @author Wujun
	 * 创建时间 ： 2014年11月4日 下午3:38:57
	 */
	@Test
	public void remove() {
	    queryAll();
	    print("删除id = 545882728cd3ffeb216e171b：" + users.remove(new BasicDBObject("_id", new ObjectId("545882728cd3ffeb216e171b"))).getN());
	    print("remove age >= 24: " + users.remove(new BasicDBObject("age", new BasicDBObject("$gte", 24))).getN());
	}
	
	/**
	 * 修改数据
	 * 
	 * @author Wujun
	 * 创建时间 ： 2014年11月4日 下午3:41:31
	 */
	@Test
	public void modify() {
		print("修改：" + users.update(new BasicDBObject("_id", 
				new ObjectId("545883808cd31df938313342")),
				new BasicDBObject("age", 99)).getN());
		print("修改：" + users.update(
						new BasicDBObject("_id", 
								new ObjectId("545883808cd31df938313340")),
								new BasicDBObject("age", 121), true,// 如果数据库不存在，是否添加
						false// 多条修改
				).getN());
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	public void query() {
	    //查询所有
	    //queryAll();
	    //查询id = 4de73f7acd812d61b4626a77
	    print("find id = 4de73f7acd812d61b4626a77: " + users.find(new BasicDBObject("_id", new ObjectId("545883808cd31df938313342"))).toArray());
	    //查询age = 121
	    print("find age = 121: " + users.find(new BasicDBObject("age", 121)).toArray());
	    //查询age >= 99
	    print("find age >= 99: " + users.find(new BasicDBObject("age", new BasicDBObject("$gte", 99))).toArray());
	    print("find age <= 121: " + users.find(new BasicDBObject("age", new BasicDBObject("$lte", 121))).toArray());
	    print("查询age!=99：" + users.find(new BasicDBObject("age", new BasicDBObject("$ne", 99))).toArray());
	    print("查询age in 99/121：" + users.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.IN, new int[] { 99, 121}))).toArray());
	    print("查询age not in 121：" + users.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.NIN, new int[] {121}))).toArray());
	    print("查询age exists 排序：" + users.find(new BasicDBObject("age", new BasicDBObject(QueryOperators.EXISTS, true))).toArray());
	    print("只查询age属性：" + users.find(null, new BasicDBObject("age", true)).toArray());
	    print("只查属性：" + users.find(null, new BasicDBObject("age", true), 0, 2).toArray());
	    print("只查属性：" + users.find(null, new BasicDBObject("age", true), 0, 2, Bytes.QUERYOPTION_NOTIMEOUT).toArray());
	    //只查询一条数据，多条去第一条
	    print("findOne: " + users.findOne());
	    print("findOne: " + users.findOne(new BasicDBObject("age", 99)));
	    print("findOne: " + users.findOne(new BasicDBObject("age", 121), new BasicDBObject("name", true)));
	    //查询修改、删除
	    print("findAndRemove 查询age=99的数据，并且删除: " + users.findAndRemove(new BasicDBObject("name", "小周Abc")));
	    queryAll();
	}
	
	
	/**
	 * 其他操作
	 * 
	 * @author Wujun
	 * 创建时间 ： 2014年11月4日 下午4:06:33
	 */
	@Test
	public void testOthers() {
	    DBObject user = new BasicDBObject();
	    user.put("name", "hoojo");
	    user.put("age", 24);
	    //JSON 对象转换        
	    print("serialize: " + JSON.serialize(user));
	    //反序列化
	    print("parse: " + JSON.parse("{ \"name\" : \"hoojo\" , \"age\" : 24}"));
	    print("判断temp Collection是否存在: " + db.collectionExists("temp"));
	    //如果不存在就创建
	    if (!db.collectionExists("temp")) {
	        DBObject options = new BasicDBObject();
	        options.put("size", 20);
	        options.put("capped", 20);
	        options.put("max", 20);
	        print(db.createCollection("account5",null).save(options));
	    }
	    //设置db为只读
	    //db.setReadOnly(true);
	    
	    //尝试向只读的数据库写入数据。运行时候出现异常，说明不能向只读数据库写入数据.
	    db.getCollection("test").save(user);
	}
}
