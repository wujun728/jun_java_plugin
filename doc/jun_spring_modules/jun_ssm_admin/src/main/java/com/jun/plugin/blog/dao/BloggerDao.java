package com.jun.plugin.blog.dao;

import com.jun.plugin.blog.entity.Blogger;

/**
 * ����Dao�ӿ�
 * @author Wujun
 *
 */
public interface BloggerDao {

	/**
	 * ��ѯ������Ϣ
	 * @return
	 */
	public Blogger find();
	
	/**
	 * ͨ���û�����ѯ�û�
	 * @param userName
	 * @return
	 */
	public Blogger getByUserName(String userName);
	
	/**
	 * ���²�����Ϣ
	 * @param blogger
	 * @return
	 */
	public Integer update(Blogger blogger);
}
