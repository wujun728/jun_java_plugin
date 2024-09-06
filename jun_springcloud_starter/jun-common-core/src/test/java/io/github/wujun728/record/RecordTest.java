package io.github.wujun728.record;

import java.sql.SQLException;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.jfinal.plugin.activerecord.*;
import com.jfinal.plugin.druid.DruidPlugin;
import io.github.wujun728.record.model.Blog;
import io.github.wujun728.record.model._MappingKit;
//import io.github.wujun728.db.record.Db;
//import io.github.wujun728.db.record.IAtom;
//import io.github.wujun728.db.record.Page;
//import io.github.wujun728.db.record.Record;

/**
 * Unit test for simple App.
 */
public class RecordTest {
	static String jdbcUrl = "jdbc:mysql://localhost/jfinal_demo?useSSL=false&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
	static String user = "root";
	static String password = "yourpassword";

	public static DruidPlugin createDruidPlugin() {
		DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
		return druidPlugin;
	}

	public static void initActiveRecordPlugin() {
		DruidPlugin druidPlugin = createDruidPlugin();

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setDevMode(true);
		arp.setShowSql(true);

		// 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
		arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");

		// 所有映射在生成的 _MappingKit.java 中自动化搞定
		_MappingKit.mapping(arp);

		// 先启动 druidPlugin，后启动 arp
		druidPlugin.start();
		arp.start();
	}

	public static void main(String[] args) {
		initActiveRecordPlugin();

		/*// 使用 Model
		Blog dao = new Blog().dao();
		Blog blog = dao.template("findBlog", 1).findFirst();
		System.out.println(blog.getTitle());

		// 使用 Db + Record 模式
		Record record = Db.template("findBlog", 1).findFirst();
		System.out.println(record.getStr("title"));*/
		test();
	}

//	@Test
	public static void test() {
		//初始化数据连接
//		Db.init("jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&useSSL=false&characterEncoding=utf8" +
//				"&serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&useInformationSchema=true",
//				"root", "");
//		Db.init("jdbc:mysql://localhost:3306/db_qixing_bk?characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&serverTimezone=GMT%2B8","root", "xxx");
		//打印sql日志
//		Db.use().setShowSql(true);

		List<Record> baskets = Db.find("select * from biz_test");
		for (Record record : baskets) {
			System.out.println(record.getLong("id"));
			System.out.println(JSONUtil.toJsonStr(record));
		}
//		Db.tx(new TransactionWrap() {
//			@Override
//			public boolean run() throws SQLException {
//				return Db.deleteById("biz_test", 666666);
//			}
//		});
		Db.deleteById("biz_test", 666666);

		Record r = new Record();
//		r.set("id", 666666);
		r.set("cusname", "ddddd11");
		r.set("cusdesc", "ddddd11");
		r.set("create_date", "2011-01-01");
		Db.save("biz_test", r);

		r.set("cusname", "ddddd1122");
		r.set("cusdesc", "ddddd1122");
		Db.update("biz_test", "id", r);

		Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				try {
					Record r1 = new Record();
					r1.set("id", "ddddd");
					Db.save("biz_test", r1);

					Record r12 = new Record();
					r12.set("id", "ddddd");
					r12.set("remarks", "remarks");
					Db.update("biz_test", "id", r12);

				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		});
		//count参数决定是否统计总行数
		Page<Record> p = Db.paginate(1,2, "select * ","  from biz_test where id>?",1000);
		p.getList();
		p.getPageNumber();
		p.getPageSize();
		p.getTotalPage();
		p.getTotalRow();

		System.out.println(p.getTotalRow());
	}
}
