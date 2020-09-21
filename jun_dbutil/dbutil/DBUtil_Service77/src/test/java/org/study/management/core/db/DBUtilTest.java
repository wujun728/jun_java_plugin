package org.study.management.core.db;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;
import org.study.management.core.db.entity.Score;
import org.study.management.core.db.util.DBUtil;

public class DBUtilTest {

	@Test
	public void test() {
		// Assert.assertTrue(DBUtil.update("INSERT INTO score VALUES( NULL ,? ,?)",
		// new Object[]{ 95,"李四"}));

		// Score score = (Score)DBUtil.get(Score.class, 1);
		// System.out.println(score.getName()+ "|" +score.getScore());
		
		// List<?> list = DBUtil.query("SELECT * FROM score where score > ?",
		// new Object[] { 55 }, new BeanListHandler<Score>(Score.class));
		// for (Object object : list) {
		// Score score = (Score) object;
		// System.out.println(score.getName() + "|" + score.getScore());
		// }
	}

}
