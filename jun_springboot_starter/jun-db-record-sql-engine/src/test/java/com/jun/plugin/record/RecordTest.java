package com.jun.plugin.record;

import java.sql.SQLException;
import java.util.List;

import cn.hutool.json.JSONUtil;
import com.jun.plugin.db.record.Db;
import com.jun.plugin.db.record.IAtom;
import com.jun.plugin.db.record.Page;
import com.jun.plugin.db.record.Record;

/**
 * Unit test for simple App.
 */
public class RecordTest {

//	@Test
	public void test() {
		//初始化数据连接
		Db.init("jdbc:mysql://localhost:3306/db_qixing_bk?useUnicode=true&useSSL=false&characterEncoding=utf8" +
				"&serverTimezone=GMT%2b8&zeroDateTimeBehavior=convertToNull&useInformationSchema=true",
				"root", "");
//		Db.init("jdbc:mysql://localhost:3306/db_qixing_bk?characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&serverTimezone=GMT%2B8","root", "xxx");
		//打印sql日志
		Db.use().setShowSql(true);

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
