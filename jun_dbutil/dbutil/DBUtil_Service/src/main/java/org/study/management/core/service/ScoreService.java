package org.study.management.core.service;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.study.management.core.db.entity.Score;
import org.study.management.core.db.util.DBUtil;

/**
 * 成绩服务类（使用了单例模式）
 * 
 * @author Administrator
 * 
 */
public class ScoreService {
	private static ScoreService scoreservice = null;

	private ScoreService() {
	}

	public static ScoreService getInstance() {
		if (scoreservice == null) {
			synchronized (ScoreService.class) {
				if (scoreservice == null) {
					scoreservice = new ScoreService();
				}
			}
		}
		return scoreservice;
	}

	/**
	 * 根据成绩查询
	 * 
	 * @param start
	 *            最低分
	 * @param end
	 *            最高分
	 * @return
	 */
	@SuppressWarnings({"unchecked"})
	public List<Score> findByScoreScope(int start, int end) {
		String sql = "SELECT s.id,s.score,s.name FROM score s WHERE s.score > ? && s.score < ?";
		return (List<Score>) DBUtil.query(sql, new Object[] { start, end },
				new BeanListHandler<Score>(Score.class));
	}
	
	/**
	 * 添加成绩
	 * 
	 * @param score
	 * @return
	 */
	public boolean add(Score score){
		return DBUtil.update("INSERT INTO score VALUES( NULL ,? ,?)", new Object[]{ score.getScore(), score.getName()});
	}
	
	/**
	 * 修改成绩
	 * 
	 * @param score
	 * @return
	 */
	public boolean update(Score score){
		return DBUtil.update("UPDATE score SET score = ?,name = ? WHERE id = ?", new Object[]{ score.getScore(), score.getName() ,score.getId()});
	}
}
